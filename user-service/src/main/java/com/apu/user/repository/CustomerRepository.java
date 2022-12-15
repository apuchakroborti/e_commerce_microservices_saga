package com.apu.user.repository;

import com.apu.user.entity.Customer;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByUserId(String userId);

    @Query("select  cu from Customer cu where cu.oauthUser.id = ?#{principal.id}")
    Optional<Customer> getLoggedInEmployee();

}
