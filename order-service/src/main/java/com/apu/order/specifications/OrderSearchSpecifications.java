package com.apu.order.specifications;

import com.apu.order.entity.CustomerOrder;
import com.apu.order.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class OrderSearchSpecifications {
    public static Specification<CustomerOrder> withId(Long id){
        return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : cb.conjunction();
    }
    public static Specification<CustomerOrder> withCustomerId(Long customerId){
        return (root, query, cb) -> customerId != null ? cb.equal(root.get("customerId"), customerId) : cb.conjunction();
    }
    public static Specification<CustomerOrder> withProductCode(String productCode){
        return (root, query, cb) -> !Utils.isNullOrEmpty(productCode) ?
                cb.like(root.get("productCode"), "%" + productCode + "%") :
                cb.conjunction();
    }
    public static Specification<CustomerOrder> withOrderPlacingDate(LocalDate orderPlacingDate){
        return (root, query, cb) -> orderPlacingDate!=null?
                cb.equal(root.get("orderPlacingDate"), orderPlacingDate) :
                cb.conjunction();
    }
    public static Specification<CustomerOrder> withExpectedDeliveryDate(LocalDate expectedDeliveryDate){
        return (root, query, cb) -> expectedDeliveryDate!=null?
                cb.equal(root.get("expectedDeliveryDate"), expectedDeliveryDate) :
                cb.conjunction();
    }

    public static Specification<CustomerOrder> withStatus(Boolean status){
        return (root, query, cb) -> status!=null ?
                cb.equal(root.get("status"), status) :
                cb.conjunction();
    }
    
}
