package com.apu.order.repository;

import com.apu.order.entity.ProductSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSummaryRepository extends JpaRepository<ProductSummary, Long> {
}
