package com.apu.payment.config;

import com.apu.commons.event.payment.WalletEvent;
import com.apu.commons.event.user.CustomerEvent;
import com.apu.commons.event.user.CustomerStatus;
import com.apu.payment.services.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@Slf4j
public class WalletConsumerConfig {

    @Autowired
    private WalletService walletService;

    @Bean
    public Function<Flux<CustomerEvent>, Flux<WalletEvent>> walletProcessor() {
        log.info("WalletConsumerConfig::walletProcessor --> ");
        return walletEventFlux -> walletEventFlux.flatMap(this::processWallet);
    }

    //TODO need to work for the wallet event consume and publish event to the user event
    private Mono<WalletEvent> processWallet(CustomerEvent customerEvent) {
        // get the customer id
        // check the balance availability
        // if balance sufficient -> Payment completed and deduct amount from DB
        // if payment not sufficient -> cancel order event and update the amount in DB
        log.info("WalletConsumerConfig::processWallet --> customer id: {} customer status: {}",
                customerEvent.getWalletRequestDto().getCustomerId(), customerEvent.getCustomerStatus());

        if(CustomerStatus.NEW_CUSTOMER.equals(customerEvent.getCustomerStatus())){
            log.info("WalletConsumerConfig::processWallet --> new Customer Event-->customer id: {} customer status: {}",
                    customerEvent.getWalletRequestDto().getCustomerId(), customerEvent.getCustomerStatus());

            return  Mono.fromSupplier(()->this.walletService.newWalletEvent(customerEvent));
        }else{
            //TODO need to block the wallet for the users who are blocked
            log.info("WalletConsumerConfig::processWalletCreation --> cancel Customer Wallet Event--> customer id: {} customer status: {}",
                    customerEvent.getWalletRequestDto().getCustomerId(), customerEvent.getCustomerStatus());

            return Mono.fromRunnable(()->{
                log.info("Wallet is not created!");
            });
        }
    }
}
