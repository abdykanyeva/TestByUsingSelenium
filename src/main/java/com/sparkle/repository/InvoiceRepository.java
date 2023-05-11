package com.sparkle.repository;


import com.sparkle.entity.Company;
import com.sparkle.entity.Invoice;
import com.sparkle.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {


    List<Invoice> findAllByCompanyAndInvoiceType(Company company, InvoiceType invoiceType);

}
