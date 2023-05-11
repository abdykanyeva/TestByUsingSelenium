package com.sparkle.repository;

import com.sparkle.entity.Company;
import com.sparkle.entity.InvoiceProduct;
import com.sparkle.enums.InvoiceStatus;
import com.sparkle.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findByInvoiceId(Long id);
    void deleteInvoiceProductsById(Long id);
    InvoiceProduct findInvoiceProductById(Long id);

    List<InvoiceProduct> findAllByInvoice_CompanyAndInvoice_InvoiceStatusAndInvoice_InvoiceType(Company company, InvoiceStatus invoiceStatus, InvoiceType invoiceType);
    @Query(value = "SELECT * FROM invoice_products ip JOIN invoices i ON i.id =ip.invoice_id WHERE i.invoice_status ='APPROVED' ORDER BY i.date DESC ", nativeQuery = true)
    List<InvoiceProduct> retrieveAllBasedOnStatusOrderByDateDesc();


}
