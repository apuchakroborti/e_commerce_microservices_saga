package com.apu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto implements Serializable {
    private Long id;
    private Long customerId;
    private Double balance;
    private Boolean status;
}
