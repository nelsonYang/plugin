package com.framework.webgenerator;

import com.framework.annotation.ServiceConfig;
import com.framework.entity.annotation.EntityConfig;
import com.framework.entity.annotation.FieldConfig;
import com.framework.entity.pojo.Entity;
import com.framework.service.api.Service;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;

/**
 *
 *
 * @author nelson
 */
public class WebPanel extends JFrame {

    private final DataObject context;
    private String servicePackageName;
    private String entityPackageName;
    private static final Map<String, Class<? extends Service>> serviceMap = new HashMap<String, Class<? extends Service>>();
    private static final Map<String, Class<? extends Entity>> entityMap = new HashMap<String, Class<? extends Entity>>();
    private static final Map<String, Map<String, FieldConfig>> entityFieldMap = new HashMap<String, Map<String, FieldConfig>>();
    
     private final JLabel javaHomeLable = new JLabel("java_home:");
    private JTextField javaHomeField = new JTextField("/home/nelson/sofeware/jdk1.7.0_21");
    private final JLabel packageNamesLable = new JLabel("实体包名:");
    private JTextField packageNames = new JTextField("com.daxia.entity");
    private final JLabel servicePackageNameLable = new JLabel("服务包名:");
    private final JTextField servicePackageNameField = new JTextField("com.daxia.service");
    private final JLabel webContextLable = new JLabel("项目上下文:");
    private final JTextField webContextField = new JTextField("Sales");
    private final JLabel servicePathLabel = new JLabel("服务器地址:");
    private final JTextField servicePathField = new JTextField("http://192.168.253.149/Sales/");
    private final JLabel servletNameLable = new JLabel("servlet名称:");
    private final JTextField servletNameField = new JTextField("ServiceServlet");
    private final JLabel actNameLabel = new JLabel("act的名称:");
    private final JTextField actNameField = new JTextField("act");
    private final JButton finishButton = new JButton("finish");
    private static String jsTemplate;
    private static String addTemplate;
    private static String inquireByIdTemplate;
    private static String inquireListTemplate;
    private static String inquirePageListTemplate;
    private static String updateTemplate;
    private static String indexTemplate;

    static {
        try {
            StringBuilder sb = new StringBuilder(200);
            InputStream is1 = WebPanel.class.getResourceAsStream("template.js");
            //  InputStream is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("template.js");
            InputStreamReader isr = new InputStreamReader(is1);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            jsTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = WebPanel.class.getResourceAsStream("add.html");
            // is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("add.html");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            addTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = WebPanel.class.getResourceAsStream("inquireById.html");
            // is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("inquireById.html");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            inquireByIdTemplate = sb.toString();

            sb = new StringBuilder(200);
            // is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("inquireList.html");
            is1 = WebPanel.class.getResourceAsStream("inquireList.html");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            inquireListTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = WebPanel.class.getResourceAsStream("inquirePageList.html");
            // is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("inquirePageList.html");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            inquirePageListTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = WebPanel.class.getResourceAsStream("update.html");
            // is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("update.html");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            updateTemplate = sb.toString();

            sb = new StringBuilder(200);
            is1 = WebPanel.class.getResourceAsStream("index.html");
            //is1 = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
            isr = new InputStreamReader(is1);
            br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            indexTemplate = sb.toString();

        } catch (IOException ex) {
            System.err.print(ex);
        } finally {
        }
    }

    public WebPanel(final DataObject context) {
        this.context = context;
        this.setSize(400, 800);
        this.setFocusable(true);
        Container container = this.getContentPane();
        GridLayout gridLayout = new GridLayout(8, 2);
        gridLayout.setHgap(10);
        gridLayout.setVgap(10);
        container.setLayout(gridLayout);
         container.add(javaHomeLable);
        container.add(javaHomeField);
        container.add(packageNamesLable);
        container.add(packageNames);
        container.add(servicePackageNameLable);
        container.add(servicePackageNameField);
        container.add(webContextLable);
        container.add(webContextField);
        container.add(servicePathLabel);
        container.add(servicePathField);
        container.add(servletNameLable);
        container.add(servletNameField);
        container.add(actNameLabel);
        container.add(actNameField);
        container.add(finishButton);

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(javaHomeField.getText().trim().isEmpty() && packageNames.getText().trim().isEmpty() && servicePackageNameLable.getText().trim().isEmpty() && webContextField.getText().trim().isEmpty() && servicePathField.getText().trim().isEmpty() && servletNameField.getText().trim().isEmpty() && actNameField.getText().trim().isEmpty())) {
                    try {
                        String javaHome = javaHomeField.getText().trim();
                        String packageName = packageNames.getText().trim();
                        String servicePackageName = servicePackageNameField.getText().trim();
                        String serverPath = servicePathField.getText().trim();
                        String webContext = webContextField.getText().trim();
                        String actName = actNameField.getText().trim();
                        String servletName = servletNameField.getText().trim();
                        String[] packageNames;
                        String[] servicePackageNames;
                        if (packageName.contains(",")) {
                            packageNames = packageName.split(",");
                        } else {
                            packageNames = new String[]{packageName};
                        }
                        if (servicePackageName.contains(",")) {
                            servicePackageNames = servicePackageName.split(",");
                        } else {
                            servicePackageNames = new String[]{servicePackageName};
                        }
                        String path;
                        path = context.getPrimaryFile().getPath();
                        System.out.println("entity path =" + path);
                        //开始加载entity 和 service

                        getRegisterServiceMap(servicePackageNames);
                        getAllEntityMap(packageNames);
                        getAllEntityFieldMap(entityMap);
                        System.out.println("serviceMap.size=" + serviceMap.size());
                        System.out.println("entityMap.size=" + entityMap.size());
                        //拷贝js等数据
                        URL url = WebPanel.class.getResource("");
                        System.out.println("url =" + url);
                        System.out.println(url.getFile());
                        System.out.println(url.getPath());
                        FileEntity fileEntity = getFileEntity(url.getPath());
                        copyRequiredFiles(javaHome,fileEntity, path, new String[]{"css", "js", "images", "resource", "html","WEB-INF"});
                        //replate sean-config.js config.js
                        updateConfig(path + "/js/common/config.js", new String[]{"#{ServerPath}", "#{ServletName}", "#{ActName}", "#{WebContext}"}, new String[]{serverPath, servletName, actName, webContext});
                        updateConfig(path + "/js/seajs-config.js", new String[]{"#{WebContext}"}, new String[]{webContext});
                        //生成每一个文件对应的html 
                        generateAllHtml(path + "/html/", entityMap);
                        //生成所有的连接
                        generateAllLink(path + "/html/");
                        setVisible(false);
                    } catch (Exception ex) {
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

    private static FileEntity getFileEntity(String urlPath) {
        int maohaoIndex = urlPath.lastIndexOf(":");
        int gantanghaoIndex = urlPath.indexOf("!");
        String path = urlPath.substring(maohaoIndex + 1, gantanghaoIndex);
        int xieganIndex = path.lastIndexOf("/");
        String filePath = path.substring(0, xieganIndex);
        String fileName = path.substring(xieganIndex + 1);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(filePath);
        fileEntity.setAbsolutePath(path);
        System.out.println("fileName=" + fileName + ", filePath=" + filePath + ",path =" + path);
        return fileEntity;
    }


    //生成shell
    private static boolean copyRequiredFiles(String javahome,FileEntity fileEntity, String desPath, String[] directories) throws IOException, InterruptedException {
        Process process;
        InputStream is;
        StringBuilder sb = new StringBuilder(50);
        String srcPath = fileEntity.getFilePath() + "/webconfig/";
        sb.append("#! /bin/sh").append("\n");
        sb.append("cd ").append(fileEntity.getFilePath()).append("\n");
        sb.append(javahome).append("/bin/jar -xf ./").append(fileEntity.getFileName()).append("\n");
        sb.append("sleep 1").append("\n");
        for (String directory : directories) {
            sb.append("rm -rf ").append(desPath).append("/").append(directory).append("\n");
            sb.append("mkdir -p ").append(desPath).append("/").append(directory).append("\n");
            sb.append("cp -r ").append(srcPath).append(directory).append("/*  ").append(desPath).append("/").append(directory).append("\n");
            System.out.println(sb.toString());
        }
        FileWriter fw = new FileWriter(fileEntity.getFilePath() + "/copy.sh");
        fw.write(sb.toString());
        fw.flush();
        fw.close();
        String path = fileEntity.getFilePath() + "/copy.sh";
        String privileges = "chmod 777 " + path;
        process = Runtime.getRuntime().exec(privileges);
        int result = process.waitFor();
        System.out.println("privileges =" + result);
        int num = 0;
        do {
            System.out.println("command =" + path);
            process = Runtime.getRuntime().exec(path);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            result = process.waitFor();
            String line = "";
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
            input = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            num++;
            System.out.println("result=" + result);
            Thread.sleep(5 * 1000l);
        } while (result != 0 && num <= 3);

        return true;
    }

    private static boolean updateConfig(String filePath, String[] replaceHolders, String[] replaceValues) throws FileNotFoundException, IOException {
        StringBuilder contentBuilder = new StringBuilder(500);
        FileReader reader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line).append("\n");
        }
        System.out.println();
        String content = contentBuilder.toString();
        System.out.println("before :" + content);
        for (int index = 0; index < replaceHolders.length; index++) {
            content = content.replace(replaceHolders[index], replaceValues[index]);
        }
        br.close();
        System.out.println("after :" + content);
        FileWriter fw = new FileWriter(filePath);
        fw.write(content);
        fw.flush();
        fw.close();
        return true;
    }

    private static Map<String, Class<? extends Service>> getRegisterServiceMap(String[] packageNames) {
        //获取action name
        String act;
        List<Class> classList = new ClassParser().parse(packageNames);
        for (Class clazz : classList) {
            if (clazz.isAnnotationPresent(ServiceConfig.class)) {
                ServiceConfig serviceConfig = (ServiceConfig) clazz.getAnnotation(ServiceConfig.class);
                act = serviceConfig.act();
                serviceMap.put(act, clazz);
            }
        }
        return serviceMap;
    }

    private static Map<String, Class<? extends Entity>> getAllEntityMap(String[] packageNames) {
        String entityName;
        List<Class> classList = new ClassParser().parse(packageNames);
        for (Class clazz : classList) {
            if (clazz.isAnnotationPresent(EntityConfig.class)) {
                EntityConfig serviceConfig = (EntityConfig) clazz.getAnnotation(EntityConfig.class);
                entityName = serviceConfig.entityName();
                entityMap.put(entityName, clazz);
            }
        }
        return entityMap;
    }

    public static EntityConfig getEntityConfig(String entityName) {

        return (EntityConfig) entityMap.get(entityName).getAnnotation(EntityConfig.class);

    }

    private static Map<String, Map<String, FieldConfig>> getAllEntityFieldMap(Map<String, Class<? extends Entity>> entityMap) {
        Class<? extends Entity> entityClass;
        Map<String, FieldConfig> fieldMap;
        for (Entry<String, Class<? extends Entity>> entry : entityMap.entrySet()) {
            fieldMap = new HashMap<String, FieldConfig>(15, 1);
            entityClass = entry.getValue();
            // EntityConfig entityConfig = entityClass.getAnnotation(EntityConfig.class);
            //String[] keyField = entityConfig.keyFields();
            Field[] fields = entityClass.getDeclaredFields();
            FieldConfig fieldConfig;
            for (Field field : fields) {
                if (field.isAnnotationPresent(FieldConfig.class)) {
                    fieldConfig = field.getAnnotation(FieldConfig.class);
                    fieldMap.put(fieldConfig.fieldName(), fieldConfig);
                }
            }
            entityFieldMap.put(entry.getKey(), fieldMap);
        }

        return entityFieldMap;
    }

    private static Map<String, FieldConfig> getEntityFieldMap(String entityName) {
        return entityFieldMap.get(entityName);

    }

    /**
     *
     * @return
     */
    private static String getActionName(String actionName) {
        String entityName = null;
        if (actionName.startsWith("insert")) {
            entityName = actionName.substring("insert".length());
        } else if (actionName.startsWith("update")) {
            entityName = actionName.substring("update".length());
        } else if (actionName.startsWith("delete")) {
            entityName = actionName.substring("delete".length());
        } else if (actionName.startsWith("inquire") && actionName.endsWith("ById")) {
            int length = actionName.length();
            int pre = "inquire".length();
            int suf = "ById".length();
            int entityLength = length - pre - suf;
            entityName = actionName.substring(pre, pre + entityLength);
        } else if (actionName.startsWith("inquire") && actionName.endsWith("List") && !actionName.startsWith("inquirePage")) {
            int length = actionName.length();
            int pre = "inquire".length();
            int suf = "List".length();
            int entityLength = length - pre - suf;
            entityName = actionName.substring(pre, pre + entityLength);
        } else if (actionName.startsWith("inquirePage")) {
            int length = actionName.length();
            int pre = "inquirePage".length();
            int suf = "List".length();
            int entityLength = length - pre - suf;
            entityName = actionName.substring(pre, pre + entityLength);
        }
        entityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        return entityName;
    }

    private static boolean generateAllHtml(String filePath, Map<String, Class<? extends Entity>> entityMap) throws IOException {
        Set<String> entityNameSet = entityMap.keySet();
        String firstUpperCaseEntityName;
        ServiceConfig serviceConfig = null;
        Class<? extends Service> serviceClass;
        for (String key : entityNameSet) {
            System.out.println("key=" + key);

            firstUpperCaseEntityName = key.substring(0, 1).toUpperCase() + key.substring(1);
            //insert
            serviceClass = serviceMap.get("insert" + firstUpperCaseEntityName);
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                generateHtml(filePath, serviceConfig);
            }

            //update
            serviceClass = serviceMap.get("update" + firstUpperCaseEntityName);
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                generateHtml(filePath, serviceConfig);
            }
            //delete

            serviceClass = serviceMap.get("delete" + firstUpperCaseEntityName);
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                generateHtml(filePath, serviceConfig);
            }
            //inquirePage

            serviceClass = serviceMap.get("inquirePage" + firstUpperCaseEntityName + "List");
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                generateHtml(filePath, serviceConfig);
            }
            //inquireList

            serviceClass = serviceMap.get("inquire" + firstUpperCaseEntityName + "List");
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                generateHtml(filePath, serviceConfig);
            }

            serviceClass = serviceMap.get("inquire" + firstUpperCaseEntityName + "ById");
            //inquireById
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                generateHtml(filePath, serviceConfig);
            }
            System.out.println("serviceClass=" + serviceClass);
            System.out.println("serviceConfig=" + serviceConfig);

        }
        return true;
    }

    private static boolean generateHtml(String filePath, ServiceConfig serviceConfig) throws IOException {
        ContextEntity contextEntity;
        String resultHtml = "";
        String fileName = "";
        String fieldHtml;
        String rules;
        String messages;
        String act = serviceConfig.act();
        String entityName = getActionName(act);
        String firstUpCaseEntityName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
        System.out.println("act=" + act);
        System.out.println("firstUpCaseEntityName=" + firstUpCaseEntityName);
        if (act.startsWith("insert")) {
            String toUrl = "inquirePage" + firstUpCaseEntityName + "List.html";
            contextEntity = getFieldHtmlAndRulesAndMessage(serviceConfig);
            fieldHtml = contextEntity.getResult();
            rules = contextEntity.getRules();
            messages = contextEntity.getMessage();

            resultHtml = addTemplate.replace("#{toUrl}", toUrl).replace("#{insertAction}", act).replace("#{rules}", rules).replace("#{messages}", messages).replace("#{fields}", fieldHtml);
            fileName = "add" + firstUpCaseEntityName + ".html";
        } else if (act.startsWith("update")) {
            String toUrl = "inquirePage" + firstUpCaseEntityName + "List.html";
            contextEntity = getFieldHtmlAndRulesAndMessage(serviceConfig);
            fieldHtml = contextEntity.getResult();
            rules = contextEntity.getRules();
            messages = contextEntity.getMessage();
            EntityConfig entityConfig = getEntityConfig(entityName);
            String primaryKey = entityConfig.keyFields()[0];
            String queryAction = "inquire" + firstUpCaseEntityName + "ById";
            resultHtml = updateTemplate.replace("#{queryAction}", queryAction).replace("#{primaryKey}", primaryKey).replace("#{toUrl}", toUrl).replace("#{updateAction}", act).replace("#{rules}", rules).replace("#{messages}", messages).replace("#{fields}", fieldHtml);
            fileName = "update" + firstUpCaseEntityName + ".html";

        } else if (act.startsWith("inquire") && act.endsWith("ById")) {
            fieldHtml = getLabelField(serviceConfig);
            EntityConfig entityConfig = getEntityConfig(entityName);
            String primaryKey = entityConfig.keyFields()[0];
            resultHtml = inquireByIdTemplate.replace("#{primaryKey}", primaryKey).replace("#{inquireAction}", act).replace("#{fields}", fieldHtml);
            fileName = "inquire" + firstUpCaseEntityName + "ById.html";

        } else if (act.startsWith("inquire") && act.endsWith("List") && !act.startsWith("inquirePage")) {
            contextEntity = getFieldHtmlAndRulesAndMessage(serviceConfig);
            contextEntity = getTable(serviceConfig);
            String head = contextEntity.getResult();
            String template = contextEntity.getMappingResult();
            EntityConfig entityConfig = getEntityConfig(entityName);
            String entityNameDesc = entityConfig.entityName();
            String primaryKey = entityConfig.keyFields()[0];
            String deleteAction = "delete" + firstUpCaseEntityName;
            resultHtml = inquireListTemplate.replace("#{entiyNameDesc}", entityNameDesc).replace("#{primaryKey}", primaryKey).replace("#{inquireListAction}", act).replace("#{deleteAction}", deleteAction).replace("#{head}", head).replace("#{template}", template).replace("#{entityName}", firstUpCaseEntityName);
            fileName = "inquire" + firstUpCaseEntityName + "List.html";
        } else if (act.startsWith("inquirePage")) {
            contextEntity = getFieldHtmlAndRulesAndMessage(serviceConfig);
            contextEntity = getTable(serviceConfig);
            String head = contextEntity.getResult();
            String template = contextEntity.getMappingResult();
            EntityConfig entityConfig = getEntityConfig(entityName);
            String entityNameDesc = entityConfig.entityName();
            String primaryKey = entityConfig.keyFields()[0];
            String deleteAction = "delete" + firstUpCaseEntityName;
            resultHtml = inquirePageListTemplate.replace("#{entiyNameDesc}", entityNameDesc).replace("#{primaryKey}", primaryKey).replace("#{inquirePageAction}", act).replace("#{deleteAction}", deleteAction).replace("#{head}", head).replace("#{template}", template).replace("#{entityName}", firstUpCaseEntityName);
            fileName = "inquirePage" + firstUpCaseEntityName + "List.html";
        }
        if (!act.startsWith("delete")) {
            System.out.println("fileName=" + fileName);
            System.out.println("filePath=" + filePath);
            FileWriter fw = new FileWriter(filePath + fileName);
            fw.write(resultHtml);
            fw.flush();
            fw.close();
        }
        return true;
    }

    private static boolean generateJs() {
        try {
            StringBuilder sb = new StringBuilder(200);
            InputStream is1 = WebPanel.class.getResourceAsStream("template.js");
            InputStreamReader isr = new InputStreamReader(is1);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            jsTemplate = sb.toString();
            return true;
        } catch (IOException ex) {
            System.err.println(ex);
            Logger.getLogger(WebPanel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean generateAllLink(String filePath) throws IOException {
        String entityName;
        String firstUpcaseEntityName;
        EntityConfig entityConfig;
        Class<? extends Service> serviceClass;
        ServiceConfig serviceConfig;
        String act;
        String desc;
        String fileName = "index.html";
        StringBuilder linkBuilder = new StringBuilder(200);
        for (Entry<String, Class<? extends Entity>> entry : entityMap.entrySet()) {
            entityConfig = getEntityConfig(entry.getKey());
            entityName = entityConfig.entityName();
            firstUpcaseEntityName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);
            System.out.println("firstUpcaseEntityName = " + firstUpcaseEntityName);
            serviceClass = serviceMap.get("inquirePage" + firstUpcaseEntityName + "List");
            System.out.println("serviceClass = " + serviceClass);
            if (serviceClass != null) {
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                act = serviceConfig.act();
                desc = serviceConfig.description();
                linkBuilder.append("<div><a href=").append(act).append(".html>").append(desc).append("</a></div>").append("\r\n");
                serviceClass = serviceMap.get("inquire" + firstUpcaseEntityName + "List");
                serviceConfig = serviceClass.getAnnotation(ServiceConfig.class);
                act = serviceConfig.act();
                desc = serviceConfig.description();
                linkBuilder.append("<div><a href=").append(act).append(".html>").append(desc).append("</a></div>").append("\r\n");
            }
        }
        String resultHtml = indexTemplate.replace("${allLinkList}", linkBuilder.toString());
        FileWriter fw = new FileWriter(filePath + fileName);
        fw.write(resultHtml);
        fw.flush();
        fw.close();
        return true;
    }

    private static ContextEntity getTable(ServiceConfig serviceConfig) {
        ContextEntity resultEntity = new ContextEntity();
        StringBuilder tableHeadBuilder = new StringBuilder(100);
        StringBuilder templateBuilder = new StringBuilder(200);
        // Map<String, FieldConfig> fieldMap = new HashMap<String, FieldConfig>();
        String act = serviceConfig.act();
        System.out.println("act=" + act);
        String entityName = getActionName(act);
        System.out.println("entityName=" + entityName);
        String firstUpperCaseEntityName = entityName.substring(0,1).toUpperCase() + entityName.substring(1);
        Class<? extends Entity> entityClass = entityMap.get(entityName);
        System.out.println("entityClassName=" + entityClass.getName());
        EntityConfig entityConfig = entityClass.getAnnotation(EntityConfig.class);
        String[] keyField = entityConfig.keyFields();
        Map<String, FieldConfig> fieldMap = getEntityFieldMap(entityName);
        templateBuilder.append("<% var entity, entityId; %>").append("\r\n");
        templateBuilder.append("<% for ( i = 0, len = dataList.length; i < len; i++ ) { entity = dataList[i]; entityId = entity.").append(keyField[0]).append("; %>").append("\r\n");
        templateBuilder.append("<tr>").append("\r\n");
        templateBuilder.append("<td><%=entityId%></td>").append("\r\n");

        tableHeadBuilder.append("<th class=\"w150\">").append(fieldMap.get(keyField[0]).fieldName()).append("</th>").append("\r\n");
        String[] returnParameters = serviceConfig.returnParameters();
        for (String returnParameter : returnParameters) {
            if (!returnParameter.equals(keyField[0])) {
                tableHeadBuilder.append("<th class=\"w100\">").append(fieldMap.get(returnParameter).description()).append("</th>").append("\r\n");
                templateBuilder.append("<td><%=entity.").append(returnParameter).append("%></td>").append("\r\n");
            }
        }
        tableHeadBuilder.append("<th class=\"w150\">操作</th>").append("\r\n");
        templateBuilder.append("<td>").append("\r\n");
        templateBuilder.append("<a href=\"update").append(firstUpperCaseEntityName).append(".html?").append(keyField[0]).append("=<%=entityId%>\">编辑</a>").append("\r\n");
        templateBuilder.append("<a href=\"javascript:;\" data-id=\"<%=entityId%>\" class=\"opt\">删除</a>").append("\r\n");
        templateBuilder.append("</td>").append("\r\n");
        templateBuilder.append("</tr>").append("\r\n");
        templateBuilder.append("<% }%>").append("\r\n");
        resultEntity.setResult(tableHeadBuilder.toString());
        resultEntity.setMappingResult(templateBuilder.toString());
        return resultEntity;
    }

    /**
     *
     * @param serviceConfig
     * @return
     */
    public static ContextEntity getFieldHtmlAndRulesAndMessage(ServiceConfig serviceConfig) {
        ContextEntity resultEntity = new ContextEntity();
        StringBuilder fieldBuilder = new StringBuilder(200);
        StringBuilder ruleBuilder = new StringBuilder(50);
        StringBuilder messageBuilder = new StringBuilder(50);
        String[] importantParameters = serviceConfig.importantParameters();
        String act = serviceConfig.act();
        String entityName = getActionName(act);
        Map<String, FieldConfig> fieldMap = getEntityFieldMap(entityName);
        String parameterDesc;
        FieldConfig fieldConfig;
        ruleBuilder.append("{").append("\r\n");
        messageBuilder.append("{").append("\r\n");
        for (String parameter : importantParameters) {

            if (!("session".equals(parameter) || "encryptType".equals(parameter) || "pageCount".equals(parameter) || "pageNo".equals(parameter))) {
                System.out.println("parameter =" + parameter);
                fieldConfig = fieldMap.get(parameter);
                if (fieldConfig != null) {
                    parameterDesc = fieldConfig.description();
                    fieldBuilder.append("<div class=\"form-group clearfix\">").append("\r\n");
                    fieldBuilder.append("<div class=\"label-item fl\">").append("\r\n");
                    fieldBuilder.append(" <label class=\"input-item\" for=\"").append(parameter).append("\"><b>*</b>").append(parameterDesc).append("：</label>").append("\r\n");
                    fieldBuilder.append("</div>").append("\r\n");
                    fieldBuilder.append("<div class=\"inner-group fl\">").append("\r\n");
                    fieldBuilder.append("<div class=\"inner-group clearfix\">").append("\r\n");
                    fieldBuilder.append("<div class=\"input-box fl\">").append("\r\n");
                    fieldBuilder.append("<input type=\"text\" name=\"").append(parameter).append("\" class=\"big-input\" id=\"").append(parameter).append("\" placeholder=\"").append(parameterDesc).append("\">").append("\r\n");
                    fieldBuilder.append("</div>").append("\r\n");
                    fieldBuilder.append("</div>").append("\r\n");
                    fieldBuilder.append("</div>").append("\r\n");
                    fieldBuilder.append("</div>").append("\r\n");
                    ruleBuilder.append(parameter).append(": { required: true}").append("\r\n");
                    messageBuilder.append(parameter).append(": {required: \"请输入").append(parameterDesc).append("\"}").append("\r\n");
                }
            }
        }
        ruleBuilder.append("};").append("\r\n");
        messageBuilder.append("};").append("\r\n");

        String[] minorParameters = serviceConfig.minorParameters();
        for (String parameter : minorParameters) {
            System.out.println("minor parameter =" + parameter);
            fieldConfig = fieldMap.get(parameter);
            if (fieldConfig != null) {
                parameterDesc = fieldConfig.description();
                fieldBuilder.append("<div class=\"form-group clearfix\">").append("\r\n");
                fieldBuilder.append("<div class=\"label-item fl\">").append("\r\n");
                fieldBuilder.append(" <label class=\"input-item\" for=\"").append(parameter).append("\">").append(parameterDesc).append("：</label>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("<div class=\"inner-group fl\">").append("\r\n");
                fieldBuilder.append("<div class=\"inner-group clearfix\">").append("\r\n");
                fieldBuilder.append("<div class=\"input-box fl\">").append("\r\n");
                fieldBuilder.append("<input type=\"text\" name=\"").append(parameter).append("\" class=\"big-input\" id=\"").append(parameter).append("\" placeholder=\"").append(parameterDesc).append("\">").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
            }
        }
        resultEntity.setRules(ruleBuilder.toString());
        resultEntity.setMessage(messageBuilder.toString());
        resultEntity.setResult(fieldBuilder.toString());
        return resultEntity;
    }

    private static String getLabelField(ServiceConfig serviceConfig) {
        String[] returnParameters = serviceConfig.returnParameters();
        String act = serviceConfig.act();
        String entityName = getActionName(act);
        Map<String, FieldConfig> fieldMap = getEntityFieldMap(entityName);
        StringBuilder fieldBuilder = new StringBuilder(200);
        String parameterDesc;
        FieldConfig fieldConfig;
        for (String parameter : returnParameters) {
            fieldConfig = fieldMap.get(parameter);
            if (fieldConfig != null) {
                parameterDesc = fieldConfig.description();
                fieldBuilder.append("<div class=\"form-group clearfix\">").append("\r\n");
                fieldBuilder.append("<div class=\"label-item fl\">").append("\r\n");
                fieldBuilder.append(" <label class=\"input-item\" for=\"").append(parameter).append("\">").append(parameterDesc).append("：</label>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("<div class=\"inner-group fl\">").append("\r\n");
                fieldBuilder.append("<div class=\"inner-group clearfix\">").append("\r\n");
                fieldBuilder.append("<div class=\"input-box fl\">").append("\r\n");
                fieldBuilder.append("<label ").append("class=\"big-input\" id=\"").append(parameter).append("\"").append("/>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
                fieldBuilder.append("</div>").append("\r\n");
            }
        }
        return fieldBuilder.toString();
    }
}
