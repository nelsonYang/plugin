package com.framework.generator;

import com.framework.entity.annotation.FieldConfig;
import java.awt.Container;
import java.awt.Dimension;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;

/**
 *
 * @author nelson
 */
public class ServiceDAOInfoPanel extends JFrame {

    private DataObject context;
    private JLabel packageNamesLable = new JLabel("实体包名:");
    private JTextField packageNames = new JTextField("com.sales.entity");
    private JLabel interfaceTypeLable = new JLabel("接口类型:");
    private JTextField interfaceType = new JTextField("MOBILE");
    private JButton finishButton = new JButton("finish");
    private static String inquireListTemplate;
    private static String inquirePageTemplate;
    private static String inquireTemplateByKey;
    private static String insertTemplate;
    private static String updateTemplate;
    private static String deleteTempate;

    static {
        try {
            StringBuilder sb = new StringBuilder(200);
            InputStream is1 = ServiceDAOInfoPanel.class.getResourceAsStream("inquireListTemplate");
            InputStreamReader isr = new InputStreamReader(is1);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            inquireListTemplate = sb.toString();


            sb = new StringBuilder(200);
            is1 = ServiceDAOInfoPanel.class.getResourceAsStream("inquirePageTemplate");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            inquirePageTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = ServiceDAOInfoPanel.class.getResourceAsStream("inquireTemplateByKey");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            inquireTemplateByKey = sb.toString();

            sb = new StringBuilder(200);
            is1 = ServiceDAOInfoPanel.class.getResourceAsStream("insertTemplate");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            insertTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = ServiceDAOInfoPanel.class.getResourceAsStream("updateTemplate");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            updateTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = ServiceDAOInfoPanel.class.getResourceAsStream("deleteTemplate");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            deleteTempate = sb.toString();

        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
//

    public ServiceDAOInfoPanel(final DataObject context) {
        this.context = context;
        this.setSize(400, 800);
        this.setFocusable(true);
        Container container = this.getContentPane();
        GridLayout gridLayout = new GridLayout(3, 2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        container.setLayout(gridLayout);
        container.add(packageNamesLable);
        container.add(packageNames);
        container.add(interfaceTypeLable);
        container.add(interfaceType);
        container.add(finishButton);

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(packageNames.getText().trim().isEmpty() && interfaceType.getText().trim().isEmpty())) {
                    try {
                        String packageName = packageNames.getText().trim();
                        String interfaceTypeName = interfaceType.getText().trim();
                        String[] packageNames;
                        if (packageName.contains(",")) {
                            packageNames = packageName.split(",");
                        } else {
                            packageNames = new String[]{packageName};
                        }

                        String path;
                        path = context.getPrimaryFile().getPath();
                        System.out.println("path =" + path);
                        int index = path.indexOf("/src/main/java/");
                        String packages = path.substring(index + 15);
                        packages = packages.replace("/", ".");
                        ClassParser classParser = new ClassParser();
                        List<Class> classList = classParser.parse(packageNames);
                        EntityConfigParser entityConfigParser = new EntityConfigParser();
                        EntityContext entityContext = new EntityContext();
                        entityConfigParser.parse(classList, entityContext);

                        Map<String, EntityInfo> entityMap = entityContext.getEntityMap();
                        StringBuilder actionNames = new StringBuilder(500);
                        String inquireListTemplateStr;
                        String inquirePageTemplateStr;
                        String inquireTemplateByKeyStr;
                        String insertTemplateStr;
                        String updateTemplateStr;
                        String deleteTempateStr;
                        String insertActionName = "";
                        String updateActionName = "";
                        String inquireByKeyActionName = "";
                        String inquirePageActionName = "";
                        String inquireListActionName = "";
                        String deleteActionName = "";
                        String lowCaseEntityName = "";
                        StringBuilder keyFields;
                        StringBuilder returnFields;
                        StringBuilder minorFields;
                        StringBuilder validateFields;
                        StringBuilder primarykey;
                        String entityName;
                        EntityInfo entityInfo;
                        actionNames.append("package ").append(packages).append(";\r\n").append("public class ActionNames { \r\n");
                        for (Entry<String, EntityInfo> entry : entityMap.entrySet()) {
                            lowCaseEntityName = entry.getKey();
                            entityName = lowCaseEntityName.substring(0, 1).toUpperCase() + lowCaseEntityName.substring(1);
                            entityInfo = entry.getValue();
                            String[] keyFieldNames = entityInfo.getKeyFields();
                            List<String> keyFieldList = Arrays.asList(keyFieldNames);
                            keyFields = new StringBuilder(50);
                            primarykey = new StringBuilder(100);
                            primarykey.append(" PrimaryKey primaryKey = new PrimaryKey(); \r\n");
                            for (String keyFieldName : keyFieldNames) {
                                keyFields.append("\"").append(keyFieldName).append("\",");
                                primarykey.append("\tString ").append(keyFieldName).append(" = ").append("parameters.get(\"").append(keyFieldName).append("\");\r\n");
                                primarykey.append("\tprimaryKey.putKeyField(\"").append(keyFieldName).append("\",String.valueOf(").append(keyFieldName).append("));\r\n");
                            }
                            keyFields.setLength(keyFields.length() - 1);
                            List<FieldConfig> fieldConfigList = entityInfo.getFieldInfo();
                            returnFields = new StringBuilder(200);
                            minorFields = new StringBuilder(100);
                            validateFields = new StringBuilder(300);
                            for (FieldConfig fieldConfig : fieldConfigList) {
                                returnFields.append("\"").append(fieldConfig.fieldName()).append("\",");
                                if (!keyFieldList.contains(fieldConfig.fieldName())) {
                                    minorFields.append("\"").append(fieldConfig.fieldName()).append("\",");
                                }
                                validateFields.append("\t@Field(fieldName =\"").append(fieldConfig.fieldName()).append("\",").append(" fieldType = FieldTypeEnum.").append(fieldConfig.fieldType()).append(", description =\"").append(fieldConfig.description()).append("\"),\r\n");
                            }
                            returnFields.setLength(returnFields.length() - 1);
                            minorFields.setLength(minorFields.length() - 1);
                            insertActionName = "insert" + entityName;
                            updateActionName = "update" + entityName;
                            inquireByKeyActionName = "inquire" + entityName + "ById";
                            inquirePageActionName = "inquirePage" + entityName + "List";
                            inquireListActionName = "inquire" + entityName + "List";
                            deleteActionName = "delete" + entityName;
                            inquireListTemplateStr = inquireListTemplate.replace("${actionName}", inquireListActionName)
                                    .replace("${servicePackage}", packages)
                                    .replace("${interfaceType}", interfaceTypeName)
                                    .replace("${entityPackage}", packageName)
                                    .replace("${entityName}", entityName)
                                    .replace("${lowcaseEnityName}", lowCaseEntityName)
                                    .replace("${returnFields}", returnFields)
                                    .replace("${keyFields}", keyFields)
                                    .replace("${minorFields}", minorFields)
                                    .replace(" ${primaryKey}", primarykey)
                                    .replace("${validateFields}", validateFields);
                            FileWriter entityNameWriter = new FileWriter(path + File.separator + "Inquire" + entityName + "ListService.java");
                            entityNameWriter.write(inquireListTemplateStr.toString());
                            entityNameWriter.flush();
                            entityNameWriter.close();

                            inquirePageTemplateStr = inquirePageTemplate.replace("${actionName}", inquirePageActionName)
                                    .replace("${servicePackage}", packages)
                                    .replace("${interfaceType}", interfaceTypeName)
                                    .replace("${entityPackage}", packageName)
                                    .replace("${entityName}", entityName)
                                    .replace("${lowcaseEnityName}", lowCaseEntityName)
                                    .replace("${returnFields}", returnFields)
                                    .replace("${keyFields}", keyFields)
                                    .replace("${minorFields}", minorFields)
                                    .replace(" ${primaryKey}", primarykey)
                                    .replace("${validateFields}", validateFields);
                            entityNameWriter = new FileWriter(path + File.separator + "InquirePage" + entityName + "ListService.java");
                            entityNameWriter.write(inquirePageTemplateStr.toString());
                            entityNameWriter.flush();
                            entityNameWriter.close();

                            inquireTemplateByKeyStr = inquireTemplateByKey.replace("${actionName}", inquireByKeyActionName)
                                    .replace("${servicePackage}", packages)
                                    .replace("${interfaceType}", interfaceTypeName)
                                    .replace("${entityPackage}", packageName)
                                    .replace("${entityName}", entityName)
                                    .replace("${lowcaseEnityName}", lowCaseEntityName)
                                    .replace("${returnFields}", returnFields)
                                    .replace("${keyFields}", keyFields)
                                    .replace("${minorFields}", minorFields)
                                    .replace(" ${primaryKey}", primarykey)
                                    .replace("${validateFields}", validateFields);
                            entityNameWriter = new FileWriter(path + File.separator + "Inquire" + entityName + "ByIdService.java");
                            entityNameWriter.write(inquireTemplateByKeyStr.toString());
                            entityNameWriter.flush();
                            entityNameWriter.close();

                            deleteTempateStr = deleteTempate.replace("${actionName}", deleteActionName)
                                    .replace("${servicePackage}", packages)
                                    .replace("${interfaceType}", interfaceTypeName)
                                    .replace("${entityPackage}", packageName)
                                    .replace("${entityName}", entityName)
                                    .replace("${lowcaseEnityName}", lowCaseEntityName)
                                    .replace("${returnFields}", returnFields)
                                    .replace("${keyFields}", keyFields)
                                    .replace("${minorFields}", minorFields)
                                    .replace(" ${primaryKey}", primarykey)
                                    .replace("${validateFields}", validateFields);
                            entityNameWriter = new FileWriter(path + File.separator + "Delete" + entityName + "Service.java");
                            entityNameWriter.write(deleteTempateStr.toString());
                            entityNameWriter.flush();
                            entityNameWriter.close();

                            insertTemplateStr = insertTemplate.replace("${actionName}", insertActionName)
                                    .replace("${servicePackage}", packages)
                                    .replace("${interfaceType}", interfaceTypeName)
                                    .replace("${entityPackage}", packageName)
                                    .replace("${entityName}", entityName)
                                    .replace("${lowcaseEnityName}", lowCaseEntityName)
                                    .replace("${returnFields}", returnFields)
                                    .replace("${keyFields}", keyFields)
                                    .replace("${minorFields}", minorFields)
                                    .replace(" ${primaryKey}", primarykey)
                                    .replace("${validateFields}", validateFields);
                            entityNameWriter = new FileWriter(path + File.separator + "Insert" + entityName + "Service.java");
                            entityNameWriter.write(insertTemplateStr.toString());
                            entityNameWriter.flush();
                            entityNameWriter.close();

                            updateTemplateStr = updateTemplate.replace("${actionName}", updateActionName)
                                    .replace("${servicePackage}", packages)
                                    .replace("${interfaceType}", interfaceTypeName)
                                    .replace("${entityPackage}", packageName)
                                    .replace("${entityName}", entityName)
                                    .replace("${lowcaseEnityName}", lowCaseEntityName)
                                    .replace("${returnFields}", returnFields)
                                    .replace("${keyFields}", keyFields)
                                    .replace("${minorFields}", minorFields)
                                    .replace(" ${primaryKey}", primarykey)
                                    .replace("${validateFields}", validateFields);
                            entityNameWriter = new FileWriter(path + File.separator + "Update" + entityName + "Service.java");
                            entityNameWriter.write(updateTemplateStr.toString());
                            entityNameWriter.flush();
                            entityNameWriter.close();
                            actionNames.append("\tpublic static final String ").append(insertActionName).append("= \"").append(insertActionName).append("\";").append("\r\n");
                            actionNames.append("\tpublic static final String ").append(updateActionName).append("= \"").append(updateActionName).append("\";").append("\r\n");
                            actionNames.append("\tpublic static final String ").append(inquireByKeyActionName).append("= \"").append(inquireByKeyActionName).append("\";").append("\r\n");
                            actionNames.append("\tpublic static final String ").append(inquirePageActionName).append("= \"").append(inquirePageActionName).append("\";").append("\r\n");
                            actionNames.append("\tpublic static final String ").append(inquireListActionName).append("= \"").append(inquireListActionName).append("\";").append("\r\n");
                            actionNames.append("\tpublic static final String ").append(deleteActionName).append("= \"").append(deleteActionName).append("\";").append("\r\n");
                        }
                        actionNames.append("}");
                        FileWriter entityNameWriter = null;
                        entityNameWriter = new FileWriter(path + File.separator + "ActionNames.java");
                        entityNameWriter.write(actionNames.toString());
                        entityNameWriter.flush();
                        JOptionPane.showMessageDialog(null, "执行成功", "成功提示", JOptionPane.OK_OPTION);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "包名或者接口类型不能为空！", "信息不完整的提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
