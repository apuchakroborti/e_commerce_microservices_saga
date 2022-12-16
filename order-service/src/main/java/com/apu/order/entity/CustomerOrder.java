package com.apu.order.entity;

import com.apu.commons.event.order.OrderStatus;
import com.apu.commons.event.payment.PaymentStatus;
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
public class CustomerOrder extends EntityCommon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "ORDER_PLACING_DATE", nullable = false)
    private LocalDate orderPlacingDate;

    @Column(name = "EXPECTED_DELIVERY_DATE", nullable = false)
    private LocalDate expectedDeliveryDate;

    @Column(name = "ORDER_DETAILS")
    private String orderDetails;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Double totalAmount;

    @Column(name = "PAYMENT_AMOUNT")
    private Double paymentAmount;

    private String comments;

    private Boolean status;

    @OneToMany(mappedBy = "customerOrder", fetch = FetchType.LAZY)
    private List<ProductSummary> productSummaryList = new ArrayList<>();

    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;
}
