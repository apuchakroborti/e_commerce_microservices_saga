package com.apu.order.config;

import com.apu.commons.event.payment.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class EventConsumerConfig {

    @Autowired
    private OrderStatusUpdateHandler handler;


    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){

        //listen payment-event-topic
        //will check payment status
        //if payment status completed -> complete the order
        //if payment status failed -> cancel the order
        return (payment)-> handler.updateOrder(payment.getPaymentRequestDto().getOrderId(), po->{
            log.info("Consuming events from payment service: event id: {}", payment.getEventId());
            log.info("Consuming events from payment service: customer id: {} order id: {}, payment status: {}",
                    payment.getPaymentRequestDto().getCustomerId(),
                    payment.getPaymentRequestDto().getOrderId(),
                    payment.getPaymentStatus());
            po.setPaymentStatus(payment.getPaymentStatus());
        });
    }
}
