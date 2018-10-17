package com.yingqing.mq.springrabbit;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SpringAMQPConfig {

//    @Resource
//    MQMessageHandler mqMessageHandler;

    @Autowired
    Environment env;


    @Bean
    public ConnectionFactory connectionFactory() {
        com.rabbitmq.client.ConnectionFactory connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setHost(env.getProperty("spring.rabbitmq.host"));
        connectionFactory.setUsername(env.getProperty("spring.rabbitmq.username"));
        connectionFactory.setPassword(env.getProperty("spring.rabbitmq.password"));
        connectionFactory.setPort(Integer.parseInt(env.getProperty("spring.rabbitmq.port")));

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(10000);

//        Map<String, Object> connectionFactoryPropertiesMap = new HashMap();
//        connectionFactoryPropertiesMap.put("principal", "");
//        connectionFactoryPropertiesMap.put("description", "");
//        connectionFactoryPropertiesMap.put("emailAddress", "");
//        connectionFactory.setClientProperties(connectionFactoryPropertiesMap);

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
        cachingConnectionFactory.setPublisherConfirms(true);
        cachingConnectionFactory.setPublisherReturns(true);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        FanoutExchange exchange = new FanoutExchange("transferChannel", true, false);
//        rabbitAdmin.declareExchange(exchange);
//        String queueNamesStr = env.getProperty("rabbit.queue.names");
//        String[] queueNames = queueNamesStr.split(",");
//        for(String queueName : queueNames){
//            rabbitAdmin.declareQueue(new Queue(queueName, true));
//            Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchange.getName(), "", new HashMap<>());
//            rabbitAdmin.declareBinding(binding);
//        }
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(60000);
        return rabbitTemplate;
    }

    /*
    * user with @RabbitListener
    * */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);

        // 设置消费者线程数
        simpleRabbitListenerContainerFactory.setConcurrentConsumers(1);

        // 设置最大消费者线程数
        simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(1);

        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        // 设置消费者标签
        simpleRabbitListenerContainerFactory.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return "transfer mq consumer";
            }
        });

        return simpleRabbitListenerContainerFactory;
    }

//    @Bean
//    public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory)
//    {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
////        if (ServerConfig.getInstance().isHModeEnable()){
////            if(ServerConfig.getInstance().isFrontend()) {
////                container.setQueues(new Queue("transfer_channel_1"));
////            }else {
////                container.setQueues(new Queue("transfer_channel_2"));
////            }
////        }
//        container.setQueues(new Queue("transfer_channel_1", true));
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(mqMessageHandler);
//        return container;
//    }


}
