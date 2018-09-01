package com.yingqing.mq.noanotation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransferRetryMqProducer {
    private final static String BSA_QUEUE_NAME = "bsa_transfer_retry_all";
    private final static String CR_QUEUE_NAME = "cr_transfer_retry_all";
    public final static String EXCHANGE_NAME="transfer_retry_all";
    public static void produce() throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);

        factory.setAutomaticRecoveryEnabled(true); //设置网络异常重连
        factory.setNetworkRecoveryInterval(10000);//设置 每10s ，重试一次
        factory.setTopologyRecoveryEnabled(false);//设置不重新声明交换器，队列等信息。

        //Map<String, Object> connectionFactoryPropertiesMap = new HashMap();
        //connectionFactoryPropertiesMap.put("principal", "RobertoHuang");
        //connectionFactoryPropertiesMap.put("description", "RGP订单系统V2.0");
        //connectionFactoryPropertiesMap.put("emailAddress", "RobertoHuang@foxmail.com");
        //connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        Connection connection = factory.newConnection();

        //创建一个通道
        Channel channel = connection.createChannel();

        String exchangeName = "tte3";

        String queName = "ttq3";

        String bindKey = "ttk3";

        String exchangeType = "topic";

        channel.exchangeDeclare(exchangeName, exchangeType, true);
        channel.queueDeclare(queName, true, false, false, null);

        channel.queueBind(queName, exchangeName, bindKey);


        String message = "retry all";

        /*
         * 向server发布一条消息
         * 参数1：exchange名字，若为空则使用默认的exchange
         * 参数2：routing key
         * 参数3：其他的属性
         * 参数4：消息体
         * RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，
         * 任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃
         */

        //Default exchange
        //The default exchange is implicitly bound to every queue, with a routing key equal to the queue name. It is not possible to explicitly bind to, 	//or unbind from the default exchange. It also cannot be deleted.
        channel.basicPublish(exchangeName, bindKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws Exception{
        TransferRetryMqProducer.produce();
    }
}
