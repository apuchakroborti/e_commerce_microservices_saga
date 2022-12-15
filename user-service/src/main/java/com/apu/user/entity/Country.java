package com.apu.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COUNTRY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    private Integer id;
    private String name;
    private String name_utf;

    @Column(name = "code_2")
    private String code2;

    @Column(name = "code_3")
    private String code3;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private List<District> districtList = new ArrayList<>();
}
