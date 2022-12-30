package com.apu.payment.services;


import com.apu.commons.event.payment.WalletEvent;
import com.apu.commons.event.user.CustomerEvent;
import com.apu.payment.dto.WalletDto;
import com.apu.payment.exceptions.GenericException;

public interface WalletService {
   WalletDto addWallet(WalletDto walletDto) throws GenericException;
   WalletDto getWalletById(Long id) throws GenericException;
   WalletDto getWalletByCustomerId(Long customerId) throws GenericException;
   WalletDto updateBalance(Long customerId, Double amount) throws GenericException;
   WalletEvent newWalletEvent(CustomerEvent customerEvent);
}
