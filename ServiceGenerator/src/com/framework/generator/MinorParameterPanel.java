package com.framework.generator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author nelson
 */
public class MinorParameterPanel extends JFrame {

    private List<JTextField> parameterNamesList = new ArrayList<JTextField>();
    private List<JComboBox> parameterTypeList = new ArrayList<JComboBox>();
    private List<JTextField> descriptionList = new ArrayList<JTextField>();
    private JButton button = new JButton("MinorAdd");
    private JButton ok = new JButton("ok");

    public MinorParameterPanel(final List<JTextField> parameterNamesList, final List<JComboBox> parameterTypeList, final List<JTextField> descriptionList) {
        this.parameterNamesList = parameterNamesList;
        this.parameterTypeList = parameterTypeList;
        this.descriptionList = descriptionList;

        this.setSize(400, 800);
        Container contentContainer = this.getContentPane();
        JPanel container = new JPanel();
        container.setSize(400, 800);
        container.setLayout(new BorderLayout());
        final JPanel parameterPanel = new JPanel();
        parameterPanel.setLayout(new FlowLayout());
        JScrollPane contentScrollPannel = new JScrollPane(parameterPanel);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel importParametersPanel = new JPanel();
                importParametersPanel.setLayout(new GridLayout(3, 1));
                importParametersPanel.setSize(200, 50);
                JTextField parametername = new JTextField("次要参数名");
                parametername.setSize(50, 50);
                DefaultComboBoxModel model = new DefaultComboBoxModel(new String[]{"FieldTypeEnum.BIG_INT", "FieldTypeEnum.INT"});
                JComboBox parametersTypeComboBox = new JComboBox(model);
                parametersTypeComboBox.setSelectedIndex(0);
                JTextField parameterDesc = new JTextField("次要参数名描述");
                parameterDesc.setSize(50, 50);
                parameterNamesList.add(parametername);
                parameterTypeList.add(parametersTypeComboBox);
                descriptionList.add(parameterDesc);
                importParametersPanel.add(parametername);
                importParametersPanel.add(parametersTypeComboBox);
                importParametersPanel.add(parameterDesc);
                parameterPanel.add(importParametersPanel);
                revalidate();
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        container.add(button, BorderLayout.NORTH);
        container.add(contentScrollPannel, BorderLayout.CENTER);
        container.add(ok, BorderLayout.SOUTH);
        contentContainer.add(container);
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
