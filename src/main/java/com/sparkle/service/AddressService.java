package com.sparkle.service;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressService {

    List<String> getListOfCountries();



}
