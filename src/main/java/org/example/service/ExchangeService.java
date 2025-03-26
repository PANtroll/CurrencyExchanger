package org.example.service;

import org.example.to.ExchangeForCurrency;

import java.util.Date;

public interface ExchangeService {

    ExchangeForCurrency getSellExchangeForCurrency(String currency, Date date);

}
