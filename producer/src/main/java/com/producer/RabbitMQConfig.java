package com.producer;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Declarables fanoutBindings() {
        Queue usQueue = new Queue("fanout-inventory-us");
        Queue ukQueue = new Queue("fanout-inventory-uk");
        Queue vnQueue = new Queue("fanout-inventory-vn");
        FanoutExchange fanoutExchange = new FanoutExchange("fanout-exchange-inventory");
        return new Declarables(
                usQueue,
                ukQueue,
                vnQueue,
                fanoutExchange,
                BindingBuilder.bind(usQueue).to(fanoutExchange),
                BindingBuilder.bind(ukQueue).to(fanoutExchange),
                BindingBuilder.bind(vnQueue).to(fanoutExchange)
        );
    }

    @Bean
    public Declarables directInventoryBinding() {
        Queue inventoryMasterQueue = new Queue("inventory-master", false);
        DirectExchange directExchange = new DirectExchange("direct-inventory-master");
        return new Declarables(
                inventoryMasterQueue,
                directExchange,
                BindingBuilder.bind(inventoryMasterQueue)
                        .to(directExchange)
                        .with("duy.master.test")
        );
    }

    @Bean
    public Declarables topicInventoryBinding() {
        Queue inventoryMasterQueue = new Queue("inventory-master", false);
        Queue inventorySlaveQueue = new Queue("inventory-slave", false);
        TopicExchange topicExchange = new TopicExchange("topic-inventory-master");
        return new Declarables(
                inventoryMasterQueue,
                inventorySlaveQueue,
                topicExchange,
                BindingBuilder.bind(inventoryMasterQueue)
                        .to(topicExchange)
                        .with("*.inventory.*"),
                BindingBuilder.bind(inventorySlaveQueue)
                        .to(topicExchange)
                        .with("*.inventory.*")
        );
    }

//    @Bean
//    public Declarables headerInventoryBinding() {
//        Queue inventoryMasterQueue = new Queue("inventory-master", false);
//        HeadersExchange headersExchange = new HeadersExchange("topic-inventory-master");
//        return new Declarables(
//                inventoryMasterQueue,
//                headersExchange,
//                BindingBuilder.bind(inventoryMasterQueue)
//                        .to(headersExchange)
//                        .where("action")
//                        .matches("inventory")
//        );
//    }

}
