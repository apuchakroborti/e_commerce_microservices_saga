package com.apu.commons.event.payment;

import com.apu.commons.dto.payment.WalletRequestDto;
import com.apu.commons.event.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class WalletEvent implements Event {

    private UUID eventId= UUID.randomUUID();
    private Date eventDate=new Date();
    private WalletRequestDto walletRequestDto;
    private WalletStatus walletStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public WalletEvent(WalletRequestDto walletRequestDto, WalletStatus walletStatus) {
        this.walletRequestDto = walletRequestDto;
        this.walletStatus = walletStatus;
    }
}
