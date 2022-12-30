package com.apu.user.config;

import com.apu.commons.event.user.CustomerEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class WalletPublisherConfig {

    @Bean
    public Sinks.Many<CustomerEvent> walletSinks(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<CustomerEvent>> walletSupplier(Sinks.Many<CustomerEvent> sinks){
       return sinks::asFlux;
    }
}
