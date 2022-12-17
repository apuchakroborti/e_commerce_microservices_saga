package com.apu.payment.config;

import com.apu.commons.event.order.OrderEvent;
import com.apu.commons.event.order.OrderStatus;
import com.apu.commons.event.payment.PaymentEvent;
import com.apu.payment.services.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@Slf4j
public class PaymentConsumerConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        // get the customer id
        // check the balance availability
        // if balance sufficient -> Payment completed and deduct amount from DB
        // if payment not sufficient -> cancel order event and update the amount in DB
        log.info("PaymentConsumerConfig::processPayment--> customer id: {} order status: {}",
                orderEvent.getOrderRequestDto().getCustomerId(), orderEvent.getOrderStatus());

        if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
            log.info("PaymentConsumerConfig::processPayment--> new Order Event-->customer id: {} order status: {}",
                    orderEvent.getOrderRequestDto().getCustomerId(), orderEvent.getOrderStatus());

            return  Mono.fromSupplier(()->this.paymentService.newOrderEvent(orderEvent));
        }else{
            log.info("PaymentConsumerConfig::processPayment--> cancelOrderEvent--> customer id: {} order status: {}",
                    orderEvent.getOrderRequestDto().getCustomerId(), orderEvent.getOrderStatus());
            return Mono.fromRunnable(()->this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
