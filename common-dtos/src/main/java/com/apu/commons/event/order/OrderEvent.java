package com.apu.commons.event.order;

import com.apu.commons.dto.order.CustomerOrderDto;
import com.apu.commons.event.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class OrderEvent implements Event {

    private UUID eventId= UUID.randomUUID();
    private Date eventDate=new Date();
    private CustomerOrderDto orderRequestDto;
    private OrderStatus orderStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public OrderEvent(CustomerOrderDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }
}
