package com.apu.user.config;

import com.apu.commons.event.payment.WalletEvent;
import com.apu.commons.event.payment.WalletStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class WalletEventConsumerConfig {

    @Autowired
    private WalletStatusUpdateHandler handler;

    @Bean
    public Consumer<WalletEvent> walletEventConsumer(){

        return (walletEvent) -> handler.updateCustomer(
                walletEvent.getWalletRequestDto().getCustomerId(), customer -> {
                    log.info("Consuming events from payment service: event id: {}", walletEvent.getEventId());
                    log.info("Consuming events from payment service: customer id: {} wallet status: {}",
                            walletEvent.getWalletRequestDto().getCustomerId(),
                            walletEvent.getWalletStatus());
                    Boolean status = WalletStatus.WALLET_CREATE.equals(walletEvent.getWalletStatus())?true:false;

                    customer.setWalletStatus(status);
                });
    }
}
