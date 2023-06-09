package com.sparkle.entity;


import com.sparkle.entity.common.BaseEntity;
import com.sparkle.enums.InvoiceStatus;
import com.sparkle.enums.InvoiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "is_deleted = false")
public class Invoice extends BaseEntity {


    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    private ClientVendor clientVendor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;







}
