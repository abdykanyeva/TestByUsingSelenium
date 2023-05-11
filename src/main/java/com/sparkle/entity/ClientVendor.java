package com.sparkle.entity;


import com.sparkle.entity.common.BaseEntity;
import com.sparkle.enums.ClientVendorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity {

    private String clientVendorName;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    @OneToOne( cascade = CascadeType.ALL)
    private Address address;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

}
