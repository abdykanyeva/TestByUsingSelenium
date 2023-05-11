package com.sparkle.service.impl;

import com.sparkle.apiconsume.countryapi.ConsumeCountriesAPI;
import com.sparkle.dto.AccessTokenDTO;
import com.sparkle.dto.CountryListDTO;
import com.sparkle.repository.AddressRepository;
import com.sparkle.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ConsumeCountriesAPI consumeCountriesAPI;

    @Value("${user_email}")
    private String user_email;

    @Value("${api_token}")
    private String api_token;


    public AddressServiceImpl(AddressRepository addressRepository, ConsumeCountriesAPI consumeCountriesAPI) {
        this.addressRepository = addressRepository;
        this.consumeCountriesAPI = consumeCountriesAPI;
    }

    @Override
    public List<String> getListOfCountries() {
        List<CountryListDTO> countryListDTOS= consumeCountriesAPI.getListOfCountries(getAuthToken());
        log.info("retrieved country size is "+countryListDTOS.size() );
        return countryListDTOS.stream().map(CountryListDTO::getCountryName).collect(Collectors.toList());
    }


    private String getAuthToken(){
        AccessTokenDTO accessTokenDTO= consumeCountriesAPI.getAccessToken(api_token,user_email);
        log.info("token retrieved for country API");
        return "Bearer "+accessTokenDTO.getAuthToken();
    }

}
