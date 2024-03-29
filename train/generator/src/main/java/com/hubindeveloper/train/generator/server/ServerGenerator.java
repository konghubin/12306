package com.hubindeveloper.train.generator.server;

import com.hubindeveloper.train.generator.util.DbUtil;
import com.hubindeveloper.train.generator.util.Field;
import com.hubindeveloper.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @description：
 * @author：Kong
 * @date：2024/3/25
 */
public class ServerGenerator {
    static boolean readOnly = false;
    static String vuePath = "web/src/views/main/";
    static String serverPath = "train/[module]/src/main/java/com/hubindeveloper/train/[module]/";
    static String pomPath = "train/generator/pom.xml";
    static String module = "";

    public static void main(String[] args) throws Exception {
        String generatorPath = getGenerator();
        module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        serverPath = serverPath.replace("[module]", module);
        new File(serverPath).mkdirs();

        // 读取table节点
        Document document = new SAXReader().read("train/generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
//        System.out.println(table);
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
//        System.out.println(tableName.getText() + "/" + domainObjectName.getText());

        // 为DbUtil设置数据源
        Node connectionURL = document.selectSingleNode("//@connectionURL");
        Node userId = document.selectSingleNode("//@userId");
        Node password = document.selectSingleNode("//@password");
        System.out.println("url: " + connectionURL.getText());
        System.out.println("user: " + userId.getText());
        System.out.println("password: " + password.getText());
        DbUtil.url = connectionURL.getText();
        DbUtil.user = userId.getText();
        DbUtil.password = password.getText();

        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = tableName.getText().replaceAll("_", "-");
        String tableNameCn = DbUtil.getTableComment(tableName.getText());
        List<Field> fieldList = DbUtil.getColumnByTableName(tableName.getText());
        Set<String> typeSet = getJavaTypes(fieldList);

        // 组装参数
        Map<String, Object> param = new HashMap<>();
        param.put("module", module);
        param.put("Domain", Domain);
        param.put("domain", domain);
        param.put("do_main", do_main);
        param.put("tableNameCn", tableNameCn);
        param.put("fieldList", fieldList);
        param.put("typeSet", typeSet);
        param.put("readOnly", readOnly);
        System.out.println(param);

//        gen(Domain, param, "service", "service");
//        gen(Domain, param, "controller", "controller");
//        gen(Domain, param, "req", "saveReq");
//        gen(Domain, param, "req", "queryReq");
//        gen(Domain, param, "resp", "queryResp");

        genVue(do_main, param);
    }

    private static void gen(String Domain, Map<String, Object> param, String packageName, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".ftl");
        String toPath = serverPath + packageName + "/";
        new File(toPath).mkdirs();
        String Target = target.substring(0, 1).toUpperCase() + target.substring(1);
        String fileName = toPath + Domain + Target + ".java";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
    }

    private static void genVue(String do_main, Map<String, Object> param) throws IOException, TemplateException {
        FreemarkerUtil.initConfig("vue.ftl");
        new File(vuePath + module).mkdirs();
        String fileName = vuePath + module + "/" + do_main + ".vue";
        System.out.println("开始生成：" + fileName);
        FreemarkerUtil.generator(fileName, param);
    }

    private static String getGenerator() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        return node.getText();
    }

    /**
     * 获取所有的Java类型，使用Set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
