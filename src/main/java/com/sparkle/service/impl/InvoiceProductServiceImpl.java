package com.sparkle.service.impl;

import com.sparkle.dto.InvoiceDTO;
import com.sparkle.dto.InvoiceProductDTO;
import com.sparkle.dto.UserDTO;
import com.sparkle.exception.InvoiceProductNotFoundException;
import com.sparkle.repository.InvoiceProductRepository;
import com.sparkle.security.SecurityService;
import com.sparkle.entity.Company;
import com.sparkle.entity.InvoiceProduct;
import com.sparkle.enums.InvoiceStatus;
import com.sparkle.enums.InvoiceType;
import com.sparkle.mapper.MapperUtil;
import com.sparkle.service.CompanyService;
import com.sparkle.service.InvoiceProductService;
import com.sparkle.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {


    private final MapperUtil mapperUtil;

    private final InvoiceService invoiceService;

    private final InvoiceProductRepository invoiceProductRepository;

    private final SecurityService securityService;

    private final CompanyService companyService;

    public InvoiceProductServiceImpl(MapperUtil mapperUtil, @Lazy InvoiceService invoiceService, InvoiceProductRepository invoiceProductRepository, SecurityService securityService, CompanyService companyService) {
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
        this.companyService = companyService;
    }
    //todo @Adelia check whether any field needs @Lazy in constructor


    @Override
    public InvoiceProductDTO findById(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id)
                .orElseThrow(() -> new InvoiceProductNotFoundException("Invoice product not found"));
        return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());
    }

    @Override
    public List<InvoiceProductDTO> findAllInvoiceProductByInvoiceId(Long id) {
        return invoiceProductRepository.findByInvoiceId(id).stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());


    }


    @Override
    public InvoiceProductDTO addInvoiceProduct(Long id, InvoiceProductDTO invoiceProductDTO) {

        InvoiceDTO invoiceDTO = invoiceService.findById(id);


        if(invoiceProductDTO.getTotal()!=null ){
            invoiceProductDTO.setInvoice(invoiceDTO);
            InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductDTO.getId()).orElseThrow();
            invoiceProduct.setProfitLoss(invoiceProductDTO.getProfitLoss());
            invoiceProduct.setRemainingQuantity(invoiceProductDTO.getRemainingQuantity());

            invoiceProductRepository.save(invoiceProduct);

            return mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());


        }else{
            InvoiceProductDTO invoiceProduct = new InvoiceProductDTO();
            invoiceProduct.setInvoice(invoiceDTO);
            invoiceProduct.setPrice(invoiceProductDTO.getPrice());
            invoiceProduct.setQuantity(invoiceProductDTO.getQuantity());
            invoiceProduct.setTax(invoiceProductDTO.getTax());
            invoiceProduct.setProfitLoss(invoiceProductDTO.getProfitLoss());
            invoiceProduct.setProduct(invoiceProductDTO.getProduct());
            invoiceProduct.setTotal(invoiceProductDTO.getPrice()
                    .multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()).multiply(BigDecimal.valueOf(invoiceProductDTO.getTax()).divide(BigDecimal.valueOf(100))))
                    .add(invoiceProductDTO.getPrice().multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity()))));

            InvoiceProduct invoiceProduct_res = mapperUtil.convert(invoiceProduct, new InvoiceProduct());

            InvoiceProduct invoiceProduct_con = invoiceProductRepository.save(invoiceProduct_res);

            return mapperUtil.convert(invoiceProduct_con,new InvoiceProductDTO());

        }

    }







    @Override
        public void removeInvoiceProduct (Long id){
           InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id)
                   .orElseThrow(() -> new InvoiceProductNotFoundException("Invoice product not found"));

            invoiceProduct.setIsDeleted(true);
            invoiceProductRepository.save(invoiceProduct);

        }


    @Override
    public void delete(Long id) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id)
                .orElseThrow(()-> new InvoiceProductNotFoundException("Invoice Product not found"));
        invoiceProduct.setIsDeleted(true);
        invoiceProductRepository.save(invoiceProduct);
    }

    public void deleteProductByInvoiceId(Long id){
        UserDTO loggedInUser = securityService.getLoggedInUser();
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findByInvoiceId(id);

        invoiceProductList.stream()
                .filter(invoiceProduct -> invoiceProduct.getInvoice().getCompany().getId()
                        .equals(loggedInUser.getCompany().getId())).forEach(invoiceProduct -> delete(invoiceProduct.getId()));
    }

    public List<InvoiceProductDTO> printInvoice(Long id){

        return invoiceProductRepository.findByInvoiceId(id)
                .stream().map(invoiceProduct -> {
                    InvoiceProductDTO invoiceProductDTO = mapperUtil.convert(invoiceProduct, new InvoiceProductDTO());
                    BigDecimal totalPrice = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity()));
                    Integer taxRate = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getTax()).divide(BigDecimal.valueOf(100))).intValue();
                    BigDecimal totalPriceWithTax = totalPrice.add(BigDecimal.valueOf(taxRate).multiply(BigDecimal.valueOf(invoiceProductDTO.getQuantity())));
                    invoiceProductDTO.setTotal(totalPriceWithTax);
                    return invoiceProductDTO;

                }).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProductDTO> listAllBasedOnStatusOrderByDateDesc() {
        List<InvoiceProduct> list = invoiceProductRepository.retrieveAllBasedOnStatusOrderByDateDesc();
        return list.stream()
                .filter(c->c.getInvoice().getCompany().getTitle().equals(companyService.getCompanyByLoggedInUser().getTitle()))
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceProduct> findAllByCompanyAndInvoiceTypeAndInvoiceStatus(Company company, InvoiceType invoiceType, InvoiceStatus invoiceStatus) {
        return invoiceProductRepository.findAllByInvoice_CompanyAndInvoice_InvoiceStatusAndInvoice_InvoiceType(company,invoiceStatus,invoiceType);
    }




}
