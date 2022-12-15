package com.apu.payment.repository;

import com.apu.payment.entity.Payment;
import com.apu.payment.utils.PaymentMedium;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query(value="  select payment " +
            "   from Payment payment" +
            "   where ( :id is null or payment.id= :id ) " +
            "   and ( :orderId is null or payment.orderId = :orderId ) "+
            "   and ( :customerId is null or payment.customerId = :customerId ) "+
            "   and ( :paymentMedium is null or payment.paymentMedium = :paymentMedium ) "+
            "   and ( :customerId is null or payment.customerId = :customerId ) "+
            "   and ( :transactionId is null or payment.transactionId = :transactionId ) "+
            "   and ( :fromDate is null or payment.paymentDate >= :fromDate ) " +
            "   and ( :toDate is null or payment.paymentDate <= :toDate ) ")
    Page<Payment> getCustomersPaymentInfoByIdAndDAteRange(
            @Param("id") Long id,
            @Param("orderId") Long orderId,
            @Param("customerId") Long customerId,
            @Param("paymentMedium") PaymentMedium paymentMedium,
            @Param("transactionId") String transactionId,
            @Param("status") Boolean status,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable);
}
