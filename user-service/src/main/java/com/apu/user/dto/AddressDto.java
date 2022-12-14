package com.apu.user.dto;


import com.apu.user.utils.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    private Long id;
    @NotNull(message = "Address type must not be null!")
    private AddressType addressType;
    private String addressDetails;

    @NotNull(message = "district must not be null!")
    private DistrictDto district;
}
