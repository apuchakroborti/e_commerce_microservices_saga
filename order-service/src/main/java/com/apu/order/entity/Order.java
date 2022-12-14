package com.apu.order.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//using @Getter and @Setter instead of @Data to remove remove recursive toString and EqualsAndHashCode method
@Entity
@Getter
@Setter
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = "employee")
//@EqualsAndHashCode(exclude = "employee")
public class Order extends EntityCommon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
