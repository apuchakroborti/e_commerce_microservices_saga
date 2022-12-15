package com.apu.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDto extends CommonDto {
    private Long id;

    @Column(name = "CUSTOM_USER_ID", nullable = false)
    private Long customUserId;

    @Column(name = "ORDER_PLACING_DATE", nullable = false)
    private LocalDate orderPlacingDate;

    @Column(name = "ORDER_DETAILS")
    private String orderDetails;

    @Column(name = "EXPECTED_DELIVERY_DATE", nullable = false)
    private LocalDate expectedDeliveryDate;


    private List<Long> productIdList = new ArrayList<>();

    private Integer totalAmount;
    private Integer paymentAmount;
}
