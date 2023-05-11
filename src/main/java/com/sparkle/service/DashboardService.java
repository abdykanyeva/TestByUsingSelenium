package com.sparkle.service;


import com.sparkle.apiconsume.currencyApi.ExchangeRateDTO;

import java.math.BigDecimal;

public interface DashboardService {

    public ExchangeRateDTO getExchangeRates();
    BigDecimal totalProfitLoss();
}
