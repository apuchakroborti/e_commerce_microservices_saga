package com.apu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto implements Serializable {
    private Integer id;
    private String name;
    private String name_utf;
    private String code2;
    private String code3;
}
