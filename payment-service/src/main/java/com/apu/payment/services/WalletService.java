package com.apu.payment.services;


import com.apu.commons.event.order.OrderEvent;
import com.apu.commons.event.payment.PaymentEvent;
import com.apu.commons.event.payment.WalletEvent;
import com.apu.payment.dto.PaymentDto;
import com.apu.payment.dto.WalletDto;
import com.apu.payment.dto.request.PaymentSearchCriteria;
import com.apu.payment.dto.request.WalletSearchCriteria;
import com.apu.payment.entity.Payment;
import com.apu.payment.exceptions.GenericException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletService {
   WalletDto addWallet(WalletDto walletDto) throws GenericException;
   WalletDto getWalletById(Long id) throws GenericException;
   WalletDto getWalletByCustomerId(Long customerId) throws GenericException;
   WalletDto updateBalance(Long customerId, Double amount) throws GenericException;
   WalletEvent newWalletEvent(WalletEvent walletEvent);
}
