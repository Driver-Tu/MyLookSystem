package com.zh.look.exceptionResultsLog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取RabbitTemplate
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        //设置ReturnCallback
        rabbitTemplate.setReturnsCallback(returnCallback -> {
            log.error("消息发送失败:交换机:{},路由键:{},应答码:{},原因:{},消息:{}",
                    returnCallback.getExchange(),
                    returnCallback.getRoutingKey(),
                    returnCallback.getReplyCode(),
                    returnCallback.getReplyText(),
                    returnCallback.getMessage().toString());
        });
    }
}
