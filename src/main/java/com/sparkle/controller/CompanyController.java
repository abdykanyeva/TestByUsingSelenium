package com.sparkle.controller;


import com.sparkle.dto.CompanyDTO;
import com.sparkle.service.AddressService;
import com.sparkle.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;
    private final AddressService addressService;

    public CompanyController(CompanyService companyService, AddressService addressService) {
        this.companyService = companyService;
        this.addressService = addressService;
    }


    @GetMapping("/list")
    public String listCompanies(Model model) {

        //should listAllCompanies  (sort by status and title (repository), id=1 will not be listed)
        model.addAttribute("companies", companyService.listAllCompaniesOrderByStatusAndTitle());

        return "company/company-list";
    }


    @GetMapping("/update/{id}")
    public String editCompany(@PathVariable("id") Long id, Model model) {

        model.addAttribute("company", companyService.findById(id));
        model.addAttribute("countries", addressService.getListOfCountries());
        return "company/company-update";
    }

    @PostMapping("/update/{id}")
    public String editCompany(@PathVariable("id") Long id, @ModelAttribute("company") @Valid CompanyDTO companyDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "company/company-update";
        }

        companyService.update(companyDTO, id);

        return "redirect:/companies/list";

    }

    @GetMapping("/activate/{id}")
    public String activate(@PathVariable("id") Long id) {

        companyService.activateDeactivateCompanyStatus(id);

        return "redirect:/companies/list";
    }

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable("id") Long id) {


        companyService.activateDeactivateCompanyStatus(id);


        return "redirect:/companies/list";
    }

    @GetMapping("/create")
    public String createCompany(Model model) {


        model.addAttribute("newCompany", new CompanyDTO());
        model.addAttribute("countries", addressService.getListOfCountries() );

        return "company/company-create";
    }

    @PostMapping("/create")
    public String insertCompany(@ModelAttribute("newCompany") @Valid CompanyDTO companyDTO, BindingResult bindingResult, String title) {

        if (companyService.titleAlreadyExists(title)) {
            bindingResult.rejectValue("title", " ", "Title already exists.");
            return "company/company-create";
        }

        if (bindingResult.hasErrors()) {
            return "company/company-create";
        }

        companyService.save(companyDTO);
        return "redirect:/companies/list";
    }

}
