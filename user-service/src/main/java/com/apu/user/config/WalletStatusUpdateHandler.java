package com.apu.user.config;

import com.apu.user.entity.Customer;
import com.apu.user.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class WalletStatusUpdateHandler {


    /*@Autowired
    private OrderStatusPublisher publisher;*/

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void updateCustomer(Long id, Consumer<Customer> consumer) {
        customerRepository.findById(id).ifPresent(consumer.andThen(this::updateCustomer));
    }

    private void updateCustomer(Customer customer) {
        customerRepository.save(customer);
        /*if (!isWalletCreateComplete) {
            publisher.publishOrderEvent(convertEntityToDto(customer), orderStatus);
        }*/
    }

    /*public CustomerOrderDto convertEntityToDto(CustomerOrder customerOrder) {
        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        Utils.copyProperty(customerOrder, customerOrderDto);

        return customerOrderDto;

    }*/
}
