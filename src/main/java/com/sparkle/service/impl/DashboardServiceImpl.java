package com.sparkle.service.impl;


import com.sparkle.annotation.ExecutionTime;
import com.sparkle.apiconsume.currencyApi.CurrencyApiClient;
import com.sparkle.apiconsume.currencyApi.ExchangeRateDTO;
import com.sparkle.service.DashboardService;
import com.sparkle.service.ReportingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final ReportingService reportingService;

    private final CurrencyApiClient currencyApiClient;

    public DashboardServiceImpl(ReportingService reportingService, CurrencyApiClient currencyApiClient) {
        this.reportingService = reportingService;
        this.currencyApiClient = currencyApiClient;
    }

    @ExecutionTime
    @Override
    public ExchangeRateDTO getExchangeRates() {

        var apiResponse = currencyApiClient.getExchangeRates();
        var currencyApiResponseForUsd = apiResponse.getUsd();
        var date = apiResponse.getDate();

        ExchangeRateDTO exchangeRateDTO = ExchangeRateDTO.builder()
                .date(date)
                .euro(currencyApiResponseForUsd.getEur())
                .britishPound(currencyApiResponseForUsd.getGbp())
                .canadianDollar(currencyApiResponseForUsd.getCad())
                .japaneseYen(currencyApiResponseForUsd.getJpy())
                .indianRupee(currencyApiResponseForUsd.getInr()).build();
        log.info("Currencies are Retrieved for date : " + date);
        return exchangeRateDTO;
    }

    @Override
    public BigDecimal totalProfitLoss(){

        Map<String,BigDecimal> bigDecimalList = reportingService.profitLossDataMap();
        List<BigDecimal> bigDecimals = new ArrayList<>();
        for (BigDecimal eachValue : bigDecimalList.values()){
            bigDecimals.add(eachValue);

        }
           return bigDecimals.stream().reduce(BigDecimal::add).get();


    }

}
