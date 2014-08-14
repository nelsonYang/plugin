package com.framwork.dao.generator;

import com.netbeans.jdbc.JDBCConnection;
import com.netbeans.dao.DAOGenerate;
import com.netbeans.entity.ColumnEntity;
import com.netbeans.entity.TableEntity;
import com.netbeans.type.TypeMapping;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
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
public class DAOInfoPanel extends JFrame {

    private DataObject context;
    private JLabel urlLable = new JLabel("jdbcURL:");
    private JTextField jdbcUrl = new JTextField("jdbc:mysql://192.168.1.80:3306/yinpiao?zeroDateTimeBehavior=convertToNull");
    private JLabel userNameLable = new JLabel("userName:");
    private JTextField userName = new JTextField("DaXia");
    private JLabel passwordLable = new JLabel("password:");
    private JTextField passwordField = new JTextField("123456");
    private JLabel schemaLable = new JLabel("schema:");
    private JTextField schema = new JTextField("");
    private JButton testButton = new JButton("test connection");
    private JButton finishButton = new JButton("finish");
    private static String template;

    //  @FieldConfig(fieldName=${fieldName},fieldType = FieldTypeEnum.${fieldType}, description = ${comment})
    //private ${javaType} ${fieldName};
    static {
        try {
            StringBuilder sb = new StringBuilder(200);
            InputStream is1 = DAOInfoPanel.class.getResourceAsStream("template");
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
//

    public DAOInfoPanel(final DataObject context) {
        this.context = context;
        this.setSize(400, 800);
        this.setFocusable(true);
        Container container = this.getContentPane();
        GridLayout gridLayout = new GridLayout(8, 1);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        container.setLayout(gridLayout);
        container.add(urlLable);
        container.add(jdbcUrl);
        container.add(userNameLable);
        container.add(userName);
        container.add(passwordLable);
        container.add(passwordField);
        container.add(schemaLable);
        container.add(schema);
        container.add(testButton);
        container.add(finishButton);
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!jdbcUrl.getText().trim().isEmpty()) {
                    String url = jdbcUrl.getText().trim();
                    String user = userName.getText().trim();
                    String password = passwordField.getText().trim();
                    String schemaInfo = schema.getText().trim();
                    Connection conn = JDBCConnection.getConnection(url, user, password);
                    PreparedStatement ps;
                    try {
                        ps = conn.prepareStatement("select 1");
                        ps.execute();
                        JOptionPane.showMessageDialog(null, "测试链接码结果", "测试成功", JOptionPane.OK_OPTION);
                    } catch (SQLException ex) {
                        Exceptions.printStackTrace(ex);
                        JOptionPane.showMessageDialog(null, "测试链接码结果", "测试失败", JOptionPane.OK_OPTION);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "连接字符串不能为空！", "信息不完整的提示", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jdbcUrl.getText().trim().isEmpty()) {
                    try {
                        String url = jdbcUrl.getText().trim();
                        String user = userName.getText().trim();
                        String password = passwordField.getText().trim();
                        String path;
                        path = context.getPrimaryFile().getPath();
                        System.out.println("path =" + path);
                        int index = path.indexOf("/src/main/java/");
                        String packages = path.substring(index + 15);
                        packages = packages.replace("/", ".");

                        List<TableEntity> tableList = DAOGenerate.generateDAO(url, user, password);
                        String tableName;
                        String className;
                        List<ColumnEntity> primaryKeyColumnList;
                        List<ColumnEntity> columnList;
                        String useCache = "true";
                        StringBuilder keyFields;
                        String timeToIdleSeconds = "3000";
                        String timeToLiveSeconds = "6000";
                        StringBuilder fieldInfo;
                        StringBuilder columnInfo;
                        StringBuilder toMap;
                        StringBuilder parseMap;
                        StringBuilder keyField;
                        String javaType;
                        String methodFieldName;
                        String fieldType;
                        String columnInfoStr;
                        StringBuilder entityNames = new StringBuilder(500);
                        //entityName
                        entityNames.append("package ").append(packages).append(";\r\n").append("public class EntityNames { \r\n");
                        for (TableEntity tableEntity : tableList) {
                            try {

                                tableName = tableEntity.getTableName();
                                entityNames.append("\tpublic static final String ").append(tableName).append("= \"").append(tableName).append("\";").append("\r\n");
                                //primary key
                                primaryKeyColumnList = tableEntity.getPrimaryKeyColumnList();
                                keyFields = new StringBuilder();
                                keyField = new StringBuilder();
                                keyFields.append("{");
                                keyField.append("\tPrimaryKey primaryKey = new PrimaryKey();\r\n");
                                for (ColumnEntity column : primaryKeyColumnList) {
                                    keyFields.append("\"").append(column.getColumnName()).append("\"").append(",");
                                    keyField.append("\t primaryKey.putKeyField(\"").append(column.getColumnName()).append("\", String.valueOf(this.").append(column.getColumnName()).append("));\r\n");
                                }
                                keyField.append("\t return primaryKey;");
                                if (keyFields.length() > 2) {
                                    keyFields.setLength(keyFields.length() - 1);
                                }
                                keyFields.append("}");
                                fieldInfo = new StringBuilder();
                                columnList = tableEntity.getColumnList();
                                toMap = new StringBuilder();
                                parseMap = new StringBuilder();
                                toMap.append("\tMap<String, String> entityMap = new HashMap<String, String>(16, 1);\r\n");
                                for (ColumnEntity column : columnList) {
                                    javaType = TypeMapping.getJavaType(column.getColumnType());
                                    fieldType = TypeMapping.getFieldType(column.getColumnType(), column.getDataSize());
                                    methodFieldName = StringUtils.toFirstUpcase(column.getColumnName());
                                    columnInfo = new StringBuilder("\t@FieldConfig(fieldName = \"${fieldName}\", fieldType =FieldTypeEnum.${fieldType} , description = \"${comment}\")\r\n");
                                    columnInfo.append("\tprivate ${javaType} ${fieldName};\r\n");
                                    columnInfo.append("\tpublic ${javaType} get${methodFieldName}() {\r\n");
                                    columnInfo.append("\treturn ${fieldName};\r\n");
                                    columnInfo.append("\t}\r\n");
                                    columnInfo.append(" \t public void set${methodFieldName}(${javaType} ${fieldName}){\r\n");
                                    columnInfo.append("\t this.${fieldName} = ${fieldName};\r\n");
                                    columnInfo.append("\t}\r\n");
                                    columnInfoStr = columnInfo.toString().replace("${fieldName}", column.getColumnName());
                                    System.out.println("fieldName =" + column.getColumnName());
                                    System.out.println("fieldType =" + fieldType);
                                    columnInfoStr = columnInfoStr.replace("${fieldType}", fieldType);
                                    columnInfoStr = columnInfoStr.replace("${methodFieldName}", methodFieldName);
                                    columnInfoStr = columnInfoStr.replace("${javaType}", javaType);
                                    columnInfoStr = columnInfoStr.replace("${comment}", column.getComment());
                                    if ("VARCHAR".equals(column.getColumnType()) || "CHAR".equals(column.getColumnType())) {
                                        toMap.append("\t entityMap.put(\"").append(column.getColumnName()).append("\", this.").append(column.getColumnName()).append("== null ? \"\":").append("this.").append(column.getColumnName()).append(");\r\n");
                                    } else if ("DATETIME".equals(column.getColumnType())) {
                                        toMap.append("\ttry{\r\n");
                                        toMap.append("\t entityMap.put(\"").append(column.getColumnName()).append("\",").append("new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").format(").append(column.getColumnName()).append("));\r\n");
                                        toMap.append("\t}catch(Exception ex){ \r\n\tSystem.err.print(ex);\r\n").append("\t entityMap.put(\"").append(column.getColumnName()).append("\",\"\");\r\n").append("\t}\r\n");
                                    } else if ("DATE".equals(column.getColumnType())) {
                                        toMap.append("\r\ttry{\r\n");
                                        toMap.append("\t entityMap.put(\"").append(column.getColumnName()).append("\",").append("new SimpleDateFormat(\"yyyy-MM-dd\").format(").append(column.getColumnName()).append("));\r\n");
                                        toMap.append("\t}catch(Exception ex){ \r\n\tSystem.err.print(ex);\r\n").append("\t entityMap.put(\"").append(column.getColumnName()).append("\",\"\");\r\n").append("\t}\r\n");
                                    } else {
                                        toMap.append("\t entityMap.put(\"").append(column.getColumnName()).append("\", String.valueOf(this.").append(column.getColumnName()).append("));\r\n");
                                    }
                                    if ("INT".equals(column.getColumnType()) || "MEDIUMINT".equals(column.getColumnType()) || "SMALLINT".equals(column.getColumnType())) {
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("Integer.parseInt(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                    } else if ("TINYINT".equals(column.getColumnType()) || "BIT".equals(column.getColumnType()) || "TINYINT UNSIGNED".equals(column.getColumnType())) {
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("Byte.parseByte(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                    } else if ("DOUBLE".equals(column.getColumnType())) {
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("Double.parseDouble(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                    } else if ("FLOAT".equals(column.getColumnType())) {
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("Float.parseFloat(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                    } else if ("DATE".equals(column.getColumnType())) {
                                        parseMap.append("\ttry{\r\n");
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("new SimpleDateFormat(\"yyyy-MM-dd\").parse(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                        parseMap.append("\t}catch(Exception ex){ \r\n\tSystem.err.print(ex);\r\n").append("\t entityMap.put(\"").append(column.getColumnName()).append("\",\"\");\r\n").append("\t}\r\n");
                                    } else if ("DATETIME".equals(column.getColumnType())) {
                                        parseMap.append("\r\ttry{\r\n");
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                        parseMap.append("\t}catch(Exception ex){ \r\n\tSystem.err.print(ex);\r\n").append("\t entityMap.put(\"").append(column.getColumnName()).append("\",\"\");\r\n").append("\t}\r\n");
                                    } else if ("BIGINT".equals(column.getColumnType()) || "BIGINT UNSIGNED".equals(column.getColumnType())) {
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("Long.parseLong(entityMap.get(\"").append(column.getColumnName()).append("\"));\r\n");
                                    } else {
                                        parseMap.append("\t this.").append(column.getColumnName()).append("=").append("(entityMap.get(\"").append(column.getColumnName()).append("\")== null ? \"\" :").append("entityMap.get(\"").append(column.getColumnName()).append("\"));").append("\r\n");
                                    }
                                    fieldInfo.append(columnInfoStr);
                                }
                                toMap.append("\t return entityMap;\r\n");
                                className = StringUtils.toFirstUpcase(tableName);
                                path = context.getPrimaryFile().getPath();
                                System.out.println("path =" + path);
                                FileWriter fw = null;

                                fw = new FileWriter(path + File.separator + className + ".java");
                                System.out.println("tablename=" + tableName);
                                StringBuilder sb = new StringBuilder(template);
                                String sbTemplate = sb.toString();
                                sbTemplate = sbTemplate.replace("${tableName}", tableName);
                                sbTemplate = sbTemplate.replace("${package}", packages);
                                sbTemplate = sbTemplate.replace("${className}", className);
                                sbTemplate = sbTemplate.replace("${useCache}", useCache);
                                sbTemplate = sbTemplate.replace("${keyFields}", keyFields.toString());
                                sbTemplate = sbTemplate.replace("${timeToIdleSeconds}", timeToIdleSeconds);
                                sbTemplate = sbTemplate.replace("${timeToLiveSeconds}", timeToLiveSeconds);
                                sbTemplate = sbTemplate.replace("${KeyField}", keyField);
                                sbTemplate = sbTemplate.replace("${FieldInfo}", fieldInfo);
                                sbTemplate = sbTemplate.replace("${parseMap}", parseMap);
                                sbTemplate = sbTemplate.replace("${toMap}", toMap);
                                fw.write(sbTemplate);
                                fw.flush();
                            } catch (IOException ex) {
                                Exceptions.printStackTrace(ex);
                            }
                        }
                        entityNames.append("}");
                        FileWriter entityNameWriter = null;
                        entityNameWriter = new FileWriter(path + File.separator + "EntityNames.java");
                        entityNameWriter.write(entityNames.toString());
                        entityNameWriter.flush();
                        JOptionPane.showMessageDialog(null, "执行成功", "成功提示", JOptionPane.OK_OPTION);
                    } catch (IOException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "连接字符串不能为空！", "信息不完整的提示", JOptionPane.ERROR_MESSAGE);
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
