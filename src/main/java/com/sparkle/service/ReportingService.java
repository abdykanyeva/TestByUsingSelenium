package com.sparkle.service;

import com.sparkle.dto.InvoiceProductDTO;
import com.sparkle.entity.InvoiceProduct;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Map;

public interface ReportingService {

    List<InvoiceProductDTO> generateReport();
    //need user so i know which invoices to pull dont know which ones

    Map<String, BigDecimal> profitLossDataMap();

    BigDecimal InvoiceTotalPerTheMonth(Month month);

    Map<Integer, Month> MapOfDifferentMonths();

    Map<Integer,String> ReduceToMonth();

    List<InvoiceProductDTO> getStockReport();

    public BigDecimal profitLossPerInvoice(InvoiceProduct invoiceProduct);
}
