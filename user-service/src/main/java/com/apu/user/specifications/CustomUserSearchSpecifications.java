package com.apu.user.specifications;

import com.apu.user.entity.Customer;
import com.apu.user.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

public class CustomUserSearchSpecifications {
    public static Specification<Customer> withId(Long id){
        return (root, query, cb) -> id != null ? cb.equal(root.get("id"), id) : cb.conjunction();
    }
    public static Specification<Customer> withFirstName(String firstName){
        return (root, query, cb) -> !Utils.isNullOrEmpty(firstName) ?
                cb.like(root.get("firstName"), "%" + firstName + "%") :
                cb.conjunction();
    }
    public static Specification<Customer> withLastName(String lastName){
        return (root, query, cb) -> !Utils.isNullOrEmpty(lastName) ?
                cb.like(root.get("lastName"), "%" + lastName + "%") :
                cb.conjunction();
    }
    public static Specification<Customer> withPhone(String phone){
        return (root, query, cb) -> !Utils.isNullOrEmpty(phone) ?
                cb.equal(root.get("phone"), phone) :
                cb.conjunction();
    }
    public static Specification<Customer> withEmail(String email){
        return (root, query, cb) -> !Utils.isNullOrEmpty(email) ?
                cb.equal(root.get("email"), email) :
                cb.conjunction();
    }
    public static Specification<Customer> withStatus(Boolean status){
        return (root, query, cb) -> status!=null ?
                cb.equal(root.get("status"), status) :
                cb.conjunction();
    }
}
