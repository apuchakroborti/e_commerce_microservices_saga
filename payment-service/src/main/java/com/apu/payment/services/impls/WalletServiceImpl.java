package com.apu.payment.services.impls;


import com.apu.commons.dto.payment.WalletRequestDto;
import com.apu.commons.event.payment.WalletEvent;
import com.apu.commons.event.payment.WalletStatus;
import com.apu.payment.dto.WalletDto;
import com.apu.payment.entity.Wallet;
import com.apu.payment.exceptions.GenericException;
import com.apu.payment.repository.WalletRepository;
import com.apu.payment.services.WalletService;
import com.apu.payment.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class WalletServiceImpl implements WalletService {


    private final WalletRepository walletRepository;
    private final RestTemplate template;

    @Autowired
    WalletServiceImpl(WalletRepository walletRepository,
                      RestTemplate template){
        this.walletRepository = walletRepository;
        this.template = template;
    }


    @Override
    public WalletDto addWallet(WalletDto walletDto) throws GenericException {
        try {
            Wallet wallet = new Wallet();
            Utils.copyProperty(walletDto, wallet);

            wallet.setBalance(100.00);
            wallet.setCreatedBy(1L);
            wallet.setStatus(true);
            wallet = walletRepository.save(wallet);

            Utils.copyProperty(wallet, walletDto);
            return walletDto;

        }catch (Exception e){
            log.error("Exception occurred while creating, message: {}", e.getMessage());
            throw new GenericException(e.getMessage(), e);
        }
    }

    @Override
    public WalletDto getWalletById(Long id) throws GenericException {
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if(!optionalWallet.isPresent()) throw new GenericException("Wallet Not found!");

        WalletDto walletDto = new WalletDto();
        Utils.copyProperty(optionalWallet.get(), walletDto);

        return walletDto;
    }

    @Override
    public WalletDto getWalletByCustomerId(Long customerId) throws GenericException {
        Optional<Wallet> optionalWallet = walletRepository.findByCustomerIdAndStatus(customerId, true);
        if(!optionalWallet.isPresent()) throw new GenericException("Wallet Not found!");

        WalletDto walletDto = new WalletDto();
        Utils.copyProperty(optionalWallet.get(), walletDto);
        return walletDto;
    }

    @Override
    public WalletDto updateBalance(Long customerId, Double amount) throws GenericException {
        Optional<Wallet> optionalWallet = walletRepository.findByCustomerIdAndStatus(customerId, true);
        if(!optionalWallet.isPresent()) throw new GenericException("Wallet Not found!");
        Wallet wallet = optionalWallet.get();

        if(amount>wallet.getBalance())throw new GenericException("Insufficient Balance!");
        wallet.setBalance(wallet.getBalance()-amount);
        wallet = walletRepository.save(wallet);

        WalletDto walletDto = new WalletDto();
        Utils.copyProperty(wallet, walletDto);
        return walletDto;
    }

    /**
     * // get the user id
     * // check the balance availability
     * // if balance sufficient -> Payment completed and deduct amount from DB
     * // if payment not sufficient -> cancel order event and update the amount in DB
     **/
    @Transactional
    @Override
    public WalletEvent newWalletEvent(WalletEvent walletEvent){
        try {
            log.info("PaymentConsumerConfig::processPayment::newOrderEvent--> customer id: {} order status: {}",
                    walletEvent.getWalletRequestDto().getCustomerId(), walletEvent.getWalletStatus());

            WalletRequestDto walletRequestDto = walletEvent.getWalletRequestDto();
            Optional<Wallet> walletOptional = walletRepository.findByCustomerIdAndStatus(walletRequestDto.getCustomerId(), true);
            if (walletOptional.isPresent()) {
                return new WalletEvent(walletRequestDto, WalletStatus.WALLET_ALREADY_PRESENT);
            }
            Wallet wallet = new Wallet(walletRequestDto.getCustomerId(), 100.0, true);
            wallet = walletRepository.save(wallet);
            Utils.copyProperty(wallet, walletRequestDto);

            return new WalletEvent(walletRequestDto, WalletStatus.WALLET_CREATE);
        }catch (Exception e){
            log.error("Exception occurred while creating wallet for the new customer!, message: {}", e.getMessage());
            return new WalletEvent(null, WalletStatus.WALLET_CREATION_FAILED);
        }
    }
}
