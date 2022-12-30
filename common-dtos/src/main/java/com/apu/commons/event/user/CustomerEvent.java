package com.apu.commons.event.user;

import com.apu.commons.dto.payment.WalletRequestDto;
import com.apu.commons.event.Event;
import com.apu.commons.event.payment.WalletStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class CustomerEvent implements Event {
    private UUID eventId= UUID.randomUUID();
    private Date eventDate=new Date();
    private WalletRequestDto walletRequestDto;
    private CustomerStatus customerStatus;
    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public CustomerEvent(WalletRequestDto walletRequestDto, CustomerStatus customerStatus) {
        this.walletRequestDto = walletRequestDto;
        this.customerStatus = customerStatus;
    }
}
