package org.serviceexcel.file_service.config;

import org.serviceexcel.file_service.util.AppConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    @Bean
    public Queue fileQueue() {
        return new Queue(AppConstants.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(AppConstants.EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(AppConstants.LOCALHOST);
        connectionFactory.setPort(5672);
        connectionFactory.setUsername(AppConstants.USERNAME_RABBIT);
        connectionFactory.setPassword(AppConstants.PASSWORD_RABBIT);
        return connectionFactory;
    }

    @Bean
    public Binding binding(Queue fileQueue, TopicExchange exchange) {
        return BindingBuilder.bind(fileQueue).to(exchange).with(AppConstants.ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(messageConverter());
        return template;
    }
}