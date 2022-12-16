package com.apu.payment.repository;

import com.apu.payment.entity.CustomerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long> {
}
