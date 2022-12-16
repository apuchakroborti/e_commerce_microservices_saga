package com.apu.payment.repository;

import com.apu.payment.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByCustomerIdAndStatus(Long customerId, Boolean status);
}
