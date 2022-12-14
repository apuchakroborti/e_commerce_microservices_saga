package com.apu.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserSearchCriteria {

    private Long id;
    private String email;
    private String phone;
    private String userId;
    private String firstName;
    private String lastName;
}