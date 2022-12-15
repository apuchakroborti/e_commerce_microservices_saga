package com.apu.payment.services.impls;


import com.apu.payment.dto.*;
import com.apu.payment.dto.request.PaymentSearchCriteria;
import com.apu.payment.entity.Payment;
import com.apu.payment.exceptions.GenericException;
import com.apu.payment.repository.PaymentRepository;
import com.apu.payment.services.PaymentService;
import com.apu.payment.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final RestTemplate template;

    @Autowired
    PaymentServiceImpl(PaymentRepository paymentRepository,
                       RestTemplate template){
        this.paymentRepository = paymentRepository;
        this.template = template;
    }


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

}
