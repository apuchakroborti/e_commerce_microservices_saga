package com.apu.payment.config;

import com.apu.commons.event.payment.WalletEvent;
import com.apu.commons.event.payment.WalletStatus;
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
    public Function<Flux<WalletEvent>, Flux<WalletEvent>> walletCreateProcessor() {
        return walletEventFlux -> walletEventFlux.flatMap(this::processWalletCreation);
    }

    //TODO need to work for the wallet event consume and publish event to the user event
    private Mono<WalletEvent> processWalletCreation(WalletEvent walletEvent) {
        // get the customer id
        // check the balance availability
        // if balance sufficient -> Payment completed and deduct amount from DB
        // if payment not sufficient -> cancel order event and update the amount in DB
        log.info("WalletConsumerConfig::processWalletCreation --> customer id: {} wallet status: {}",
                walletEvent.getWalletRequestDto().getCustomerId(), walletEvent.getWalletStatus());

        if(WalletStatus.WALLET_CREATE.equals(walletEvent.getWalletStatus())){
            log.info("WalletConsumerConfig::processWalletCreation --> new Order Event-->customer id: {} wallet status: {}",
                    walletEvent.getWalletRequestDto().getCustomerId(), walletEvent.getWalletStatus());

            return  Mono.fromSupplier(()->this.walletService.newWalletEvent(walletEvent));
        }else{
            log.info("WalletConsumerConfig::processWalletCreation --> cancelWalletEvent--> customer id: {} wallet status: {}",
                    walletEvent.getWalletRequestDto().getCustomerId(), walletEvent.getWalletStatus());
            return Mono.fromRunnable(()->{
                log.info("Wallet is not created!");
            });
        }
    }
}
