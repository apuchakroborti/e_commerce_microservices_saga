package com.apu.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletSearchCriteria {
    private Long id;
    private Long customerId;
}
