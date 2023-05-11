package com.sparkle.converter;

import com.sparkle.dto.InvoiceDTO;
import com.sparkle.service.InvoiceService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component

public class InvoiceDtoConverter implements Converter<String, InvoiceDTO> {


    InvoiceService invoiceService;

    public InvoiceDtoConverter( InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

       return invoiceService.findById(Long.parseLong(source));

    }

}
