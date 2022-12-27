package com.apu.commons.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletRequestDto {

    private Long customerId;
    private Double balance;

}
