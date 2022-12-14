package com.apu.payment.repository;

import com.apu.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query(value="  select payment " +
            "   from Payment payment" +
            "   where ( :customerId is null or payment.customerId = :customerId ) " +
            "   and ( :fromDate is null or payment.paymentDate >= :fromDate ) " +
            "   and ( :toDate is null or payment.paymentDate <= :toDate ) ")
    Page<Payment> getCustomersPaymentInfoByIdAndDAteRange(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            @Param("customerId") Long employeeId,
            Pageable pageable);
}
