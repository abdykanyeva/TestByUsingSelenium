package com.sparkle.service;

import com.sparkle.dto.InvoiceProductDTO;
import com.sparkle.entity.Company;
import com.sparkle.entity.InvoiceProduct;
import com.sparkle.enums.InvoiceStatus;
import com.sparkle.enums.InvoiceType;


import java.util.List;

public interface InvoiceProductService {


    InvoiceProductDTO findById(Long id);
    List<InvoiceProductDTO> findAllInvoiceProductByInvoiceId(Long id);


    void removeInvoiceProduct( Long id);
    InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) throws Exception;

    void delete(Long id);
    void deleteProductByInvoiceId(Long id);
    List<InvoiceProductDTO> printInvoice(Long id);

    public List<InvoiceProductDTO> listAllBasedOnStatusOrderByDateDesc();

    public List<InvoiceProduct> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company, InvoiceType invoiceType, InvoiceStatus invoiceStatus);


}
