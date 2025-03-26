package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.to.ExchangeForCurrency;
import org.example.to.NBPRate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private final NBPService nbpService;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public ExchangeForCurrency getSellExchangeForCurrency(String currency, Date date) {

        //todo go to cache if not go to NBP
        currency = currency.toUpperCase();
        ExchangeForCurrency result = null;
        NBPRate nbpRate = nbpService.getSellExchangeForCurrency(currency, date);

        result = new ExchangeForCurrency();
        result.setCurrency(currency);
        result.setRate(nbpRate.getMid());
        result.setDate(nbpRate.getEffectiveDate());

        return result;
    }
}
