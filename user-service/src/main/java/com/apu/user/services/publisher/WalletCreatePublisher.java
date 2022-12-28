package com.apu.user.services.publisher;

import com.apu.commons.dto.payment.WalletRequestDto;
import com.apu.commons.event.payment.WalletEvent;
import com.apu.commons.event.payment.WalletStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;


@Service
@Slf4j
public class WalletCreatePublisher {
    @Autowired
    private Sinks.Many<WalletEvent> walletSinks;


    public void publishWalletCreateEvent(WalletRequestDto walletRequestDto, WalletStatus walletStatus){
        log.info("Publishing event from user service: customer id: {}, wallet status: {}",
                walletRequestDto.getCustomerId(),
                walletStatus);
        WalletEvent walletEvent = new WalletEvent(walletRequestDto, walletStatus);
        walletSinks.tryEmitNext(walletEvent);
    }
}
