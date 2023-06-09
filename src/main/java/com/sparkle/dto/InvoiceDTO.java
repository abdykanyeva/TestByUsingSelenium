package com.sparkle.dto;


import com.sparkle.enums.InvoiceStatus;
import com.sparkle.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDTO {

    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    private LocalDate date;
    private CompanyDTO companyDTO;
    private ClientVendorDTO clientVendor;
    private BigDecimal price;
    private Integer tax;
    private BigDecimal total;
    private List<InvoiceProductDTO> invoiceProducts;

}
