package com.cydeo.service.impl.integration;

import com.sparkle.dto.InvoiceDTO;
import com.sparkle.enums.InvoiceStatus;
import com.sparkle.enums.InvoiceType;
import com.cydeo.motherOftests.TestData;
import com.sparkle.service.impl.InvoiceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class InvoiceServiceImplTest extends TestData{

    @Autowired
    InvoiceServiceImpl invoiceService;


    @Test
    @Transactional
    void findById() {
        InvoiceDTO byId = invoiceService.findById(1L);
        log.info("Invoice founded : " + byId.getDate());

        assertThat(byId.getInvoiceNo()).isEqualTo("P-001");
        assertThat(byId.getInvoiceStatus()).isEqualTo(InvoiceStatus.APPROVED);
    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void listAllInvoicesByType() {

        List<InvoiceDTO> invoiceDTOS = invoiceService.listAllInvoicesByType(InvoiceType.PURCHASE);

        log.info("invoice size is :" + invoiceDTOS.size());


        assertThat(invoiceDTOS).hasSize(2);

    }

    @Test
    void totalTaxOfInvoice() {
    }

    @Test
    void totalPriceOfInvoice() {
    }

    @Test
    void calculateProfitLoss() {
    }

    @Test
    void listAllInvoice() {
    }

    @Test
    void approve() {
    }

    @Test
    void getNewPurchaseInvoice() {
    }

    @Test
    void getNewSalesInvoice() {
    }

    @Test
    @Transactional
    @WithMockUser(username = "manager@greentech.com", password = "Abc1", roles = "Manager")
    void save() {
        InvoiceDTO testInvoice = TestData.invoiceDTO;
        InvoiceDTO save = invoiceService.save(testInvoice, InvoiceType.SALES);

        Assertions.assertThat(save).isNotNull();
        assertThat(save.getInvoiceNo()).isEqualTo("T-001");


        Assertions.assertThat(save)
                .extracting("invoiceNo", "invoiceType")
                .contains("T-001", "SALES");


    }

    @Test
    void create() {


    }

    @Test
    void existsById() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteByInvoiceId() {
    }

    @Test
    void updateQuantityInStock() {
    }

    @Test
    void updateQuantityAfterApproval() {
    }

    @Test
    void checkIfStockIsEnough() {
    }

    @Test
    void findByInvoiceId() {
    }

    @Test
    void totalCostOfApprovedInvoices() {
    }

    @Test
    void totalSalesOfApprovedInvoices() {
    }

    @Test
    void listAllApprovedInvoices() {
    }
}