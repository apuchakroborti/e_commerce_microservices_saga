package com.apu.user.services.publisher;

import com.apu.commons.dto.payment.WalletRequestDto;
import com.apu.commons.event.user.CustomerEvent;
import com.apu.commons.event.user.CustomerStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;


@Service
@Slf4j
public class WalletCreatePublisher {
    @Autowired
    private Sinks.Many<CustomerEvent> walletSinks;


    public void publishWalletCreateEvent(WalletRequestDto walletRequestDto, CustomerStatus customerStatus){
        log.info("Publishing event from user service: customer id: {}, customer status: {}",
                walletRequestDto.getCustomerId(),
                customerStatus);
        CustomerEvent customerEvent = new CustomerEvent(walletRequestDto, customerStatus);
        walletSinks.tryEmitNext(customerEvent);
    }
}
