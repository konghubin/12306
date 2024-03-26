package com.hubindeveloper.train.generator.server;

import com.hubindeveloper.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description：
 * @author：Kong
 * @date：2024/3/25
 */
public class ServerGenerator {
    static String servicePath = "train/[module]/src/main/java/com/hubindeveloper/train/[module]/service/";
    static String pomPath = "train/generator/pom.xml";
    static {
        new File(servicePath).mkdirs();
    }

    public static void main(String[] args) throws IOException, TemplateException, DocumentException {
        String generatorPath = getGenerator();
        String module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        servicePath = servicePath.replace("[module]", module);

        // 读取table节点
        Document document = new SAXReader().read("train/generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        System.out.println(table);
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        System.out.println(tableName.getText() + "/" + domainObjectName.getText());

        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = tableName.getText().replaceAll("_", "-");

        // 组装参数
        Map<String, Object> param = new HashMap<>();
        param.put("module", module);
        param.put("Domain", Domain);
        param.put("domain", domain);
        param.put("do_main", do_main);
        System.out.println(param);

        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(servicePath + Domain + "Service.java", param);
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
}
