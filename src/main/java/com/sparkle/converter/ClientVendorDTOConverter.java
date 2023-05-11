package com.sparkle.converter;

import com.sparkle.dto.ClientVendorDTO;
import com.sparkle.service.ClientVendorService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientVendorDTOConverter implements Converter<String, ClientVendorDTO> {

    private final ClientVendorService clientVendorService;

    public ClientVendorDTOConverter(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }

    @Override
    public ClientVendorDTO convert(String source) {
       if (source==null || source.equals("")){
           return null;
       }
       return clientVendorService.findById(Long.parseLong(source));
    }
}
