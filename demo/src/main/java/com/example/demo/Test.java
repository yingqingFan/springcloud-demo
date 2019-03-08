package com.example.demo;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;

public class Test {

    public static Logger logger = LoggerFactory.getLogger("Test");

    public static final String targetEndpoint = "http://localhost:8888/sop/services/HelloService?wsdl";
    public static final String targetNamespace = "http://sop.webservice.HelloService";
    public static final String method = "HelloServiceStart";
    public static String send(String paramData) {
        Logger logger1 = LoggerFactory.getLogger(Test.class);
        logger1.info("WebService发送请求......");
        try {
            RPCServiceClient client = new RPCServiceClient();
            Options options = client.getOptions();
            options.setTo(new EndpointReference(targetEndpoint));
            options.setTimeOutInMilliSeconds(1000 * 60 * 5);// 毫秒单位
            options.setAction(method);
            Object[] response = client.invokeBlocking(new QName(targetNamespace, method), new Object[]{paramData}, new Class[]{String.class});
            String results = (String) response[0];
            return results;
        } catch (Exception e) {
            logger.error("WebService请求异常, Cause:{}, Message:{}", e.getCause(), e.getMessage());
            e.printStackTrace(); return null; }
    }

    public static void main(String[] args) {
        System.out.println(send("hello"));
    }
}
