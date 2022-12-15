package com.apu.order.repository;

import com.apu.order.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<CustomerOrder, Long>, JpaSpecificationExecutor<CustomerOrder> {
}
