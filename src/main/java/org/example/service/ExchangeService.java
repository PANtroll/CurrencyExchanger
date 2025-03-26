package org.example.service;

import org.example.to.ExchangeForCurrency;
import org.example.to.NBPMidRate;

import java.util.Date;
import java.util.List;

public interface ExchangeService {

    ExchangeForCurrency getSellExchangeForCurrency(String currency, Date date);

    List<NBPMidRate> buyPlnExchangeByCurrencies(List<String> currencies, Date date);
}
