package org.example.service;

import org.example.to.NBPMidRate;
import org.example.to.NBPSellRate;

import java.util.Date;
import java.util.List;

public interface NBPService {

    NBPSellRate getSellExchangeForCurrency(String currency, Date date);

    List<NBPMidRate> getBuyExchangeForCurrencies(List<String> currencies, Date date);

}
