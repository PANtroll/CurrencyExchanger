package org.example.service;

import org.example.to.NBPRate;

import java.util.Date;
import java.util.List;

public interface NBPService {

    NBPRate getSellExchangeForCurrency(String currency, Date date);

    List<NBPRate> getBuyExchangeForCurrencies(List<String> currencies, Date date);//todo object change

}
