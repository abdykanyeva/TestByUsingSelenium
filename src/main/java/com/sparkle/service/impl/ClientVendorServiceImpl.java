package com.sparkle.service.impl;

import com.sparkle.dto.ClientVendorDTO;
import com.sparkle.entity.Address;
import com.sparkle.entity.ClientVendor;
import com.sparkle.entity.Company;
import com.sparkle.entity.Invoice;
import com.sparkle.enums.InvoiceStatus;
import com.sparkle.mapper.MapperUtil;
import com.sparkle.repository.ClientVendorRepository;
import com.sparkle.repository.CompanyRepository;
import com.sparkle.repository.InvoiceRepository;
import com.sparkle.security.SecurityService;
import com.sparkle.service.ClientVendorService;
import com.sparkle.service.CompanyService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    private final InvoiceRepository invoiceRepository;

    private final CompanyService companyService;

    private final CompanyRepository companyRepository;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, SecurityService securityService, MapperUtil mapperUtil, InvoiceRepository invoiceRepository, CompanyService companyService, CompanyRepository companyRepository) {
        this.clientVendorRepository = clientVendorRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
        this.invoiceRepository = invoiceRepository;
        this.companyService = companyService;
        this.companyRepository = companyRepository;
    }

    @Override
    public ClientVendorDTO findById(Long id) {

        ClientVendor obj = clientVendorRepository.findById(id).orElseThrow(() -> new NoSuchElementException("clientVendor does not exist"));
        return mapperUtil.convert(obj, new ClientVendorDTO());
    }

    @Override
    public ClientVendorDTO save(ClientVendorDTO clientVendorDTO) {
        clientVendorDTO.setCompany(companyService.getCompanyByLoggedInUser());
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor());

        ClientVendor save = clientVendorRepository.save(clientVendor);
        return mapperUtil.convert(save, new ClientVendorDTO());
    }

    @Override
    public void delete(Long id) {
        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(id);

        //working delete method below
        if (clientVendor.isPresent()) {

            clientVendor.get().setIsDeleted(true);
            clientVendorRepository.save(clientVendor.get());
        }

    }


    @Override
    public ClientVendorDTO update(ClientVendorDTO clientVendorDTO) {
        Optional<ClientVendor> clientVendor = clientVendorRepository.findById(clientVendorDTO.getId());
        ClientVendor updatedClientVendor = mapperUtil.convert(clientVendorDTO, new ClientVendor()); //convert what we got into new client vendor

        if (clientVendor.isPresent()) { //build whole new clientvendor
            updatedClientVendor.setId(clientVendor.get().getId());
            updatedClientVendor.setCompany(mapperUtil.convert(securityService.getLoggedInUser().getCompany(), new Company()));
            updatedClientVendor.setWebsite(clientVendorDTO.getWebsite());
            updatedClientVendor.setClientVendorType(clientVendorDTO.getClientVendorType());
            updatedClientVendor.setAddress(mapperUtil.convert(clientVendorDTO.getAddress(), new Address()));

            clientVendorRepository.save(updatedClientVendor);
//            clientVendorRepository.delete(clientVendor.get());//added this no ida how to get rid of old clientvendor
        }
        return mapperUtil.convert(updatedClientVendor, new ClientVendorDTO());
    }


    @Override
    public List<ClientVendorDTO> listAllClientVendors() {
        return clientVendorRepository.findAll(Sort.by("clientVendorType")).stream().filter(clientVendor ->
                        clientVendor.getCompany().getId().equals(securityService.getLoggedInUser().getCompany().getId())).
                filter(clientVendor -> clientVendor.getIsDeleted().equals(false)).map(
                        clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());

    }

    @Override
    public List<ClientVendor> findAllByClientVendorName(String name) {
        return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorName().equals(name)).collect(Collectors.toList());

    }

    @Override
    public List<ClientVendorDTO> findAllClients() {
        return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorType()
                .getValue().equals("Client")).filter(clientVendor ->
                clientVendor.getCompany().getId().equals(securityService.getLoggedInUser().getCompany().getId())).map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());
    }

    @Override
    public List<ClientVendorDTO> findAllVendors() {
        return clientVendorRepository.findAll().stream().filter(clientVendor -> clientVendor.getClientVendorType()
                .getValue().equals("Vendor")).filter(clientVendor ->
                clientVendor.getCompany().getId().equals(securityService.getLoggedInUser().getCompany().getId())).map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDTO())).collect(Collectors.toList());

    }

    @Override
    public boolean findIfCompanyHasOpenInvoices(Long id) {
        Long companyid = securityService.getLoggedInUser().getCompany().getId();

        List<Invoice> list = invoiceRepository.findAll().stream().filter(invoice -> invoice.getClientVendor().getId().equals(id))
                //so have list of invoices by client vendor WordTune

                .filter(invoice -> invoice.getInvoiceStatus().equals(InvoiceStatus.AWAITING_APPROVAL)).
        //list of all awaiting approval invoices by wordTune
                filter(invoice -> invoice.getCompany().getId().equals(companyid)).collect(Collectors.toList());
        //hopefully all awaiting approval invoices by wordTune that GreenTech has


        //have a list of all open invoices for the company by the client vendor id
        //need logged in users company and need to check if the client vendor id company has open invoices


        if (list.size() > 1) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<ClientVendor> findClientVendorById(Long id) {
        return clientVendorRepository.findById(id);
    }


}
