package com.apu.payment.services.impls;


import com.apu.commons.dto.order.CustomerOrderDto;
import com.apu.commons.dto.payment.PaymentRequestDto;
import com.apu.commons.event.order.OrderEvent;
import com.apu.commons.event.payment.PaymentEvent;
import com.apu.commons.event.payment.PaymentStatus;
import com.apu.payment.dto.*;
import com.apu.payment.dto.request.PaymentSearchCriteria;
import com.apu.payment.entity.CustomerTransaction;
import com.apu.payment.entity.Payment;
import com.apu.payment.entity.Wallet;
import com.apu.payment.exceptions.GenericException;
import com.apu.payment.repository.CustomerTransactionRepository;
import com.apu.payment.repository.PaymentRepository;
import com.apu.payment.repository.WalletRepository;
import com.apu.payment.services.PaymentService;
import com.apu.payment.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final RestTemplate template;
    private final WalletRepository userBalanceRepository;
    private final CustomerTransactionRepository customerTransactionRepository;

    @Autowired
    PaymentServiceImpl(PaymentRepository paymentRepository,
                       RestTemplate template,
                       WalletRepository userBalanceRepository,
                       CustomerTransactionRepository customerTransactionRepository){
        this.paymentRepository = paymentRepository;
        this.template = template;
        this.userBalanceRepository = userBalanceRepository;
        this.customerTransactionRepository = customerTransactionRepository;
    }

    //TODO need to create wallets while creating a customer
    //TODO need to provide add money to the wallets
    /*@PostConstruct
    public void initCustomerBalanceInDB() {
        userBalanceRepository.saveAll(Stream.of(new Wallet(5L, 50000.0, true),
                new Wallet(6L, 60000.0, true),
                new Wallet(7L, 30000.0, true),
                new Wallet(8L, 50000.0, true),
                new Wallet(9L, 10000.0, true)).collect(Collectors.toList()));
    }*/


    @Override
    public PaymentDto doPayment(PaymentDto paymentDto) throws GenericException {
        try {
            Payment payment = new Payment();
            Utils.copyProperty(paymentDto, payment);
            paymentRepository.save(payment);
            return paymentDto;

        }catch (Exception e){
            log.error("Exception occurred while completing payment, message: {}", e.getMessage());
            throw new GenericException(e.getMessage(), e);
        }
    }
    @Override
    public Page<Payment> getPaymentInfoBySearchCriteria(PaymentSearchCriteria criteria, Pageable pageable) throws GenericException{
        try {
            Page<Payment> employeeMonthlyPaySlipPage = paymentRepository.getCustomersPaymentInfoByIdAndDAteRange(
                    criteria.getId()
                    , criteria.getOrderId()
                    , criteria.getCustomerId()
                    , criteria.getPaymentMedium()
                    , criteria.getTransactionId()
                    ,true
                    , criteria.getFromDate()
                    , criteria.getToDate()
                    , pageable);
            return employeeMonthlyPaySlipPage;
        }catch (Exception e){
            log.error("Error occurred while fetching payment info!");
            throw new GenericException(e.getMessage(),e);
        }
    }

    /**
     * // get the user id
     * // check the balance availability
     * // if balance sufficient -> Payment completed and deduct amount from DB
     * // if payment not sufficient -> cancel order event and update the amount in DB
     **/
    @Transactional
    @Override
    public PaymentEvent newOrderEvent(OrderEvent orderEvent){
        log.info("PaymentConsumerConfig::processPayment::newOrderEvent--> customer id: {} order status: {}",
                orderEvent.getOrderRequestDto().getCustomerId(), orderEvent.getOrderStatus());

        CustomerOrderDto orderRequestDto = orderEvent.getOrderRequestDto();
//        if(orderRequestDto.getId()==null)throw new GenericException("Order id should not be null");
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getId(),
                orderRequestDto.getCustomerId(), orderRequestDto.getTotalAmount());

        /*Optional<Wallet> walletOptional = userBalanceRepository.findByCustomerIdAndStatus(orderRequestDto.getCustomerId(), true);
        if(!walletOptional.isPresent()){
            log.info("Wallet not found for the customer id: {}", orderRequestDto.getCustomerId());
            return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED);
        }
        Wallet wallet = walletOptional.get();
        if(wallet.getBalance()<orderRequestDto.getTotalAmount()){
            log.info("Insufficient balance for the customer id: {}", orderRequestDto.getCustomerId());
            return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED);
        }
        wallet.setBalance(wallet.getBalance() - orderRequestDto.getTotalAmount());
        userBalanceRepository.save(wallet);
        customerTransactionRepository.save(new CustomerTransaction(orderRequestDto.getId(),
                orderRequestDto.getCustomerId(),
                orderRequestDto.getTotalAmount()));

        log.info("Payment completed for the customer id: {} amount: {}", orderRequestDto.getCustomerId(), orderRequestDto.getTotalAmount());
        return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);*/

        return userBalanceRepository.findByCustomerIdAndStatus(orderRequestDto.getCustomerId(), true)
                .filter(ub -> ub.getBalance() > orderRequestDto.getTotalAmount())
                .map(ub -> {
                    ub.setBalance(ub.getBalance() - orderRequestDto.getTotalAmount());
                    customerTransactionRepository.save(new CustomerTransaction(orderRequestDto.getId(),
                            orderRequestDto.getCustomerId(),
                            orderRequestDto.getTotalAmount()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));

    }

    @Transactional
    @Override
    public void cancelOrderEvent(OrderEvent orderEvent) {
        log.info("PaymentConsumerConfig::processPayment::cancelOrderEvent --> customer id: {} order status: {}",
                orderEvent.getOrderRequestDto().getCustomerId(), orderEvent.getOrderStatus());

        customerTransactionRepository.findById(orderEvent.getOrderRequestDto().getId())
                .ifPresent(ut->{
                    customerTransactionRepository.delete(ut);

                    userBalanceRepository.findById(ut.getCustomerId())
                            .ifPresent(ub->ub.setBalance(ub.getBalance() + ut.getAmount()));
                });
    }

}
