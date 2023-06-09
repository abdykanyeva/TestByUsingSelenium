package com.sparkle.apiconsume.countryapi;

import com.sparkle.dto.AccessTokenDTO;
import com.sparkle.dto.CountryListDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(url = "${address.api.url}", name = "Countries-List")
public interface ConsumeCountriesAPI {

    //need to send api token i got when i signed up and my email and receive auth token
    @GetMapping("/getaccesstoken")
    AccessTokenDTO getAccessToken(@RequestHeader(name = "api-token") String token, @RequestHeader(name = "user-email") String email);

    //with auth token retrieve list of countries
    @GetMapping("/countries")
    List<CountryListDTO> getListOfCountries(@RequestHeader(name = "Authorization") String auth);

}
