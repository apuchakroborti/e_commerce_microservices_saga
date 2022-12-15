package com.apu.user.entity;

import com.apu.user.security_oauth2.models.security.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//using @Getter and @Setter instead of @Data to remove recursive toString method
@Entity
@Table(name = "CUSTOMERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends EntityCommon {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", nullable = false, length = 120, unique = true)
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "NID")
    private String nid;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "customUser", fetch = FetchType.LAZY)
    private List<Address> addressList = new ArrayList<>();

    @Column(name = "STATUS", nullable = false)
    private Boolean status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OAUTH_USER_ID")
    private User oauthUser;
}
