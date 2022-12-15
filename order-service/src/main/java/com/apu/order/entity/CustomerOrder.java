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

    @Column(name = "TOTAL_AMOUNT")
    private Integer totalAmount;

    @Column(name = "PAYMENT_AMOUNT")
    private Integer paymentAmount;

    private String comments;

    private Boolean status;

    @OneToMany(mappedBy = "customerOrder", fetch = FetchType.LAZY)
    private List<ProductSummary> productSummaryList = new ArrayList<>();
}
