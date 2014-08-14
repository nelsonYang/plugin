package com.framework.generator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;

/**
*
 *
 * @author nelson
 */
public class ServiceInfoPanel extends JFrame {

    private static String template;

    static {
        try {
            StringBuilder sb = new StringBuilder(200);
            InputStream is1 = ServiceInfoPanel.class.getResourceAsStream("template");
            InputStreamReader isr = new InputStreamReader(is1);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            template = sb.toString();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    private DataObject context;
    private JLabel classNameLabel = new JLabel("ClassName:");
    private JLabel actionNamelabel = new JLabel("ActionName:");
    private JLabel importantParametersLabel = new JLabel("importantParameters:");
    private JLabel minorParameterslabel = new JLabel("minorParameters:");
    private JLabel parametersWrapperTypelabel = new JLabel("parametersWrapperType:");
    private JLabel requireLoginLabel = new JLabel("requireLogin:");
    private JLabel requestEncryptlabel = new JLabel("requestEncrypt:");
    private JLabel returnParametersLabel = new JLabel("returnParameters:");
    private JLabel responseEncryptlabel = new JLabel("responseEncrypt:");
    private JLabel responseTypeLabel = new JLabel("responseType:");
    private JLabel terminalTypeLabel = new JLabel("terminalType:");
    private JLabel uploadlabel = new JLabel("upload:");
    private JLabel descriptionLabel = new JLabel("description:");
    private JTextField classNameTextField;
    private JTextField actionNameTextField;
    private JComboBox parametersWrapperComboBox;
    private JComboBox requireLoginComboBox;
    private JComboBox requestEncryptComboBox;
    private JComboBox responseEncryptComboBox;
    private JComboBox terminalTypeComboBox;
    private JComboBox uploadComboBox;
    private JComboBox responseTypeComboBox;
    private JTextArea descriptionTextArea;
    private JButton finishButton;
    private JButton cancelButton;
    private JButton parameterAddButton;
    private JButton minorparameterAddButton;
    private JButton returnParameterAddButton;
    private List<JTextField> parameterNamesList = new ArrayList<JTextField>();
    private List<JComboBox> parameterTypeList = new ArrayList<JComboBox>();
    private List<JTextField> descriptionList = new ArrayList<JTextField>();
    private List<JTextField> minorparameterNamesList = new ArrayList<JTextField>();
    private List<JComboBox> minorparameterTypeList = new ArrayList<JComboBox>();
    private List<JTextField> minordescriptionList = new ArrayList<JTextField>();
    private List<JTextField> returnparameterNamesList = new ArrayList<JTextField>();
    private List<JComboBox> returnparameterTypeList = new ArrayList<JComboBox>();
    private List<JTextField> returndescriptionList = new ArrayList<JTextField>();

    public ServiceInfoPanel(final DataObject context) {
        this.context = context;
        Container containerPanel = getContentPane();
        final JPanel container = new JPanel();
        container.setSize(400, 200);
        JScrollPane contentScrollPannel = new JScrollPane(container);
        contentScrollPannel.setAutoscrolls(true);
        contentScrollPannel.setFocusable(true);


        contentScrollPannel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentScrollPannel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        this.setTitle("Service Info");
        this.setResizable(true);
        JPanel panel = new JPanel();
        GridLayout gridlayout = new GridLayout(14, 2);
        gridlayout.setHgap(10);
        gridlayout.setVgap(10);
        panel.setLayout(gridlayout);


        final JPanel minorparameterPanel = new JPanel();
        minorparameterPanel.setLayout(new FlowLayout());
        final JPanel returnparameterPanel = new JPanel();
        returnparameterPanel.setLayout(new FlowLayout());
        classNameTextField = new JTextField();
        actionNameTextField = new JTextField();
        finishButton = new JButton("完成");
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String className = classNameTextField.getText().trim();
                String actionName = actionNameTextField.getText().trim();
                String description = descriptionTextArea.getText().trim();
                String parametersWrapperValue = parametersWrapperComboBox.getSelectedItem().toString();
                String requireLoginValue = requireLoginComboBox.getSelectedItem().toString();
                String requestEncryptValue = requestEncryptComboBox.getSelectedItem().toString();
                String responseEncryptValue = responseEncryptComboBox.getSelectedItem().toString();
                String terminalTypeValue = terminalTypeComboBox.getSelectedItem().toString();
                String uploadValue = uploadComboBox.getSelectedItem().toString();
                String responseTypeValue = responseTypeComboBox.getSelectedItem().toString();
                if (className != null && !className.isEmpty() && actionName != null && !actionName.isEmpty() && description != null && !description.isEmpty()) {
                    FileWriter fw = null;
                    try {
                        String path = context.getPrimaryFile().getPath();
                        System.out.println("path =" + path);
                        fw = new FileWriter(path + File.separator + className + ".java");
                        int index = path.indexOf("/src/main/java/");
                        String packages = path.substring(index + 15);
                        packages = packages.replace("/", ".");
                        template = template.replace("${package}", packages);
                        template = template.replace("${className}", className);
                        template = template.replace("${act}", actionName);
                        template = template.replace("${parametersWrapperType}", parametersWrapperValue);
                        template = template.replace("${requestEncrypt}", requestEncryptValue);
                        template = template.replace("${requireLogin}", requireLoginValue);
                        template = template.replace("${responseEncrypt}", responseEncryptValue);
                        template = template.replace("${terminalType}", terminalTypeValue);
                        template = template.replace("${requestEncrypt}", requestEncryptValue);
                        template = template.replace("${upload}", uploadValue);
                        template = template.replace("${responseType}", responseTypeValue);
                        template = template.replace("${decription}", description);
                        //重要参数
                        JTextField parameterName;
                        JComboBox parameterType;
                        JTextField desc;
                        StringBuilder parameterBuilder = new StringBuilder(100);
                        parameterBuilder.append("{");
                        StringBuilder validateBuilder = new StringBuilder(200);
                        validateBuilder.append("{");
                        StringBuilder minorParameterBuilder = new StringBuilder(100);
                        minorParameterBuilder.append("{");
                        StringBuilder returnParameterBuilder = new StringBuilder(100);
                        returnParameterBuilder.append("{");
                        for (int count = 0; count < parameterNamesList.size(); count++) {
                            parameterName = parameterNamesList.get(count);
                            parameterBuilder.append("\"").append(parameterName.getText().trim()).append("\",");
                            parameterType = parameterTypeList.get(count);
                            desc = descriptionList.get(count);
                            validateBuilder.append("@Field(fieldName =\"").append(parameterName.getText().trim()).append("\",fieldType = ").append(parameterType.getSelectedItem().toString()).append("description = \"").append(desc.getText().trim()).append("\",").append("\r\n");;

                        }
                        if (parameterBuilder.length() > 1) {
                            parameterBuilder.setLength(parameterBuilder.length() - 1);
                        }
                        parameterBuilder.append("}");
                        //次要参数
                        for (int count = 0; count < minorparameterNamesList.size(); count++) {
                            parameterName = minorparameterNamesList.get(count);
                            minorParameterBuilder.append("\"").append(parameterName.getText().trim()).append("\",");
                            parameterType = minorparameterTypeList.get(count);
                            desc = minordescriptionList.get(count);
                            validateBuilder.append("@Field(fieldName =\"").append(parameterName.getText().trim()).append("\",fieldType = ").append(parameterType.getSelectedItem().toString()).append("，description = \"").append(desc.getText().trim()).append("\",").append("\r\n");
                        }
                        if (validateBuilder.length() > 1) {
                            validateBuilder.setLength(validateBuilder.toString().lastIndexOf(','));
                        }
                        validateBuilder.append("}");
                        if (minorParameterBuilder.length() > 1) {
                            minorParameterBuilder.setLength(minorParameterBuilder.length() - 1);
                        }
                        minorParameterBuilder.append("}");
                        //返回参数
                        for (int count = 0; count < returnparameterNamesList.size(); count++) {
                            parameterName = returnparameterNamesList.get(count);
                            returnParameterBuilder.append("\"").append(parameterName.getText().trim()).append("\",");

                        }
                        if (returnParameterBuilder.length() > 1) {
                            returnParameterBuilder.setLength(returnParameterBuilder.length() - 1);
                        }
                        returnParameterBuilder.append("}");
                        template = template.replace("${importantParameters}", parameterBuilder.toString());
                        template = template.replace("${minorParameters}", minorParameterBuilder.toString());
                        template = template.replace("${validateParameters}", validateBuilder.toString());
                        template = template.replace("${returnParameters}", returnParameterBuilder.toString());
                        fw.write(template);
                        fw.flush();
                        setVisible(false);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    } finally {
                        try {
                            fw.close();
                        } catch (IOException ex) {
                            Exceptions.printStackTrace(ex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "className,actionName,description,必填！", "信息不完整的提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        parameterAddButton = new JButton("添加重要参数");
        parameterAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ImportParameterPanel(parameterNamesList, parameterTypeList, descriptionList);
            }
        });
        minorparameterAddButton = new JButton("添加次要参数");
        minorparameterAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MinorParameterPanel(minorparameterNamesList, minorparameterTypeList, minordescriptionList);
            }
        });
        returnParameterAddButton = new JButton("添加返回参数");
        returnParameterAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReturnParameterPanel(returnparameterNamesList, returnparameterTypeList, returndescriptionList);
            }
        });

        DefaultComboBoxModel model = new DefaultComboBoxModel(new String[]{"ParameterWrapperTypeEnum.SIMPLE_MAP", "ParameterWrapperTypeEnum.BATCH_MAP"});
        parametersWrapperComboBox = new JComboBox(model);
        parametersWrapperComboBox.setSelectedIndex(0);
        model = new DefaultComboBoxModel(new String[]{"LoginEnum.REQUIRE", "LoginEnum.NOT_REQUIRE"});
        requireLoginComboBox = new JComboBox(model);
        requireLoginComboBox.setSelectedIndex(0);
        model = new DefaultComboBoxModel(new String[]{"CryptEnum.AES", "CryptEnum.PLAIN", "CryptEnum.SIGN"});
        requestEncryptComboBox = new JComboBox(model);
        requestEncryptComboBox.setSelectedIndex(0);
        model = new DefaultComboBoxModel(new String[]{"CryptEnum.AES", "CryptEnum.PLAIN"});
        responseEncryptComboBox = new JComboBox(model);
        responseEncryptComboBox.setSelectedIndex(0);
        model = new DefaultComboBoxModel(new String[]{"ResponseTypeEnum.JSON"});
        responseTypeComboBox = new JComboBox(model);
        responseTypeComboBox.setSelectedIndex(0);
        model = new DefaultComboBoxModel(new String[]{"TerminalTypeEnum.MOBILE", "TerminalTypeEnum.WEB", "TerminalTypeEnum.OPEN_FIRE"});
        terminalTypeComboBox = new JComboBox(model);
        terminalTypeComboBox.setSelectedIndex(0);
        model = new DefaultComboBoxModel(new String[]{"UploadEnum.NO", "UploadEnum.YES"});
        uploadComboBox = new JComboBox(model);
        uploadComboBox.setSelectedIndex(0);
        descriptionTextArea = new JTextArea();

        panel.add(classNameLabel);
        panel.add(classNameTextField);
        panel.add(actionNamelabel);
        panel.add(actionNameTextField);
        panel.add(parametersWrapperTypelabel);
        panel.add(parametersWrapperComboBox);
        panel.add(requireLoginLabel);
        panel.add(requireLoginComboBox);
        panel.add(requestEncryptlabel);
        panel.add(requestEncryptComboBox);
        panel.add(responseEncryptlabel);
        panel.add(responseEncryptComboBox);
        panel.add(responseTypeLabel);
        panel.add(responseTypeComboBox);
        panel.add(terminalTypeLabel);
        panel.add(terminalTypeComboBox);
        panel.add(uploadlabel);
        panel.add(uploadComboBox);
        panel.add(descriptionLabel);
        panel.add(descriptionTextArea);
        panel.add(importantParametersLabel);
        panel.add(parameterAddButton);
        panel.add(minorParameterslabel);
        panel.add(minorparameterAddButton);
        panel.add(returnParametersLabel);
        panel.add(returnParameterAddButton);
        panel.add(finishButton);
        panel.add(cancelButton);
        container.add(panel, c);
        containerPanel.add(contentScrollPannel);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width / 2;
        int screenHeight = screenSize.height / 2;
        int height = this.getHeight();
        int width = this.getWidth();
        setLocation(screenWidth - width / 2, screenHeight - height / 2);
        this.pack();
        this.setVisible(true);
    }
}
