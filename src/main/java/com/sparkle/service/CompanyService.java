package com.sparkle.service;

import com.sparkle.dto.CompanyDTO;


import java.util.List;

public interface CompanyService {

    CompanyDTO findById(Long Id);

    List<CompanyDTO> listAllCompanies();

    List<CompanyDTO> listAllCompaniesOrderByStatusAndTitle();

    CompanyDTO save(CompanyDTO companyDTO);

    void activateDeactivateCompanyStatus(Long id);

    CompanyDTO update(CompanyDTO companyDTO, Long id);

    List<CompanyDTO> getCompaniesByLoggedInUserForRoot();

    CompanyDTO getCompanyByLoggedInUser();

    boolean titleAlreadyExists(String title);


}
