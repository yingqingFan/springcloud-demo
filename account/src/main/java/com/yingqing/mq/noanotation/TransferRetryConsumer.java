package com.yingqing.mq.noanotation;
import com.rabbitmq.client.*;

import java.io.IOException;

public class TransferRetryConsumer {


    public void consume() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);

        factory.setAutomaticRecoveryEnabled(true); //设置网络异常重连
        factory.setNetworkRecoveryInterval(10000);//设置 每10s ，重试一次
        factory.setTopologyRecoveryEnabled(false);//设置不重新声明交换器，队列等信息。

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "tte3";

        String queName = "ttq3";

        String bindKey = "ttk3";

        String exchangeType = "topic";

        /*exchange：
         *exchangeName:交换器名称
         *type：交换器类型：direct、fanout、topic
         *	a) 如果是Direct类型，则会将消息中的RoutingKey与该Exchange关联的所有Binding中的BindingKey进行比较，如果相等，则发送到该Binding对应的		         *Queue中；
         *	b) 如果是  Fanout  类型，则会将消息发送给所有与该  Exchange  定义过  Binding  的所有  Queues  中去，其实是一种广播行为；
         *	c)如果是Topic类型，则会按照正则表达式，对RoutingKey与BindingKey进行匹配，如果匹配成功，则发送到对应的Queue中；
         *durable：true：服务器重启会保留下来交换器。注意：仅设置此选项，不代表消息持久化。既不保证重启后消息还在。
         *autoDelete： true：当已经没有消费者时，服务器是否可以删除该交换器。
         */
        channel.exchangeDeclare(exchangeName, exchangeType, true);

        /*
         * 声明（创建）队列
         * 参数1：队列名称
         * 参数2：durable: 为true时server重启队列不会消失
         * 参数3：exclusive: 队列是否是独占的，如果为true只能被一个connection使用，其他连接建立时会抛出异常
         * 参数4：autoDelete: 队列不再使用时是否自动删除（没有连接，并且没有未处理的消息)
         * 参数5：建立队列时的其他参数
         */
        channel.queueDeclare(queName, true, false, false, null);

        channel.queueBind(queName, exchangeName, bindKey);

        //prefetchSize：0
        //prefetchCount：会告诉RabbitMQ不要同时给一个消费者推送多于N个消息，即一旦有N个消息还没有ack，则该consumer将block掉，直到有消息ack
        //global：true\false 是否将上面设置应用于channel，简单点说，就是上面限制是channel级别的还是consumer级别
        //备注：据说prefetchSize 和global这两项，rabbitmq没有实现，暂且不研究
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                if(message.equals("retry all")){
                    try {
                        System.out.println(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //deliveryTag:该消息的index
                //multiple：是否批量.true:将一次性ack所有小于deliveryTag的消息。
                this.getChannel().basicAck(envelope.getDeliveryTag(),false);

            }
        };

        //autoAck：是否自动ack
        channel.basicConsume(queName, false, consumer);
    }

    public static void main(String[] args) {
        try {
            new TransferRetryConsumer().consume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
