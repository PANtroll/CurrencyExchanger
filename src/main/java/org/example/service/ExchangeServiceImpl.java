package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dao.ExchangeForCurrencyDAO;
import org.example.model.ExchangeForCurrencyEntity;
import org.example.to.ExchangeForCurrency;
import org.example.to.NBPRate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private final NBPService nbpService;
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private final ExchangeForCurrencyDAO exchangeForCurrencyDAO;

    @Override
    public ExchangeForCurrency getSellExchangeForCurrency(String currency, Date date) {
        log.debug("getSellExchangeForCurrency for {}, {}", currency, date);

        if (exchangeForCurrencyDAO.existsByCurrencyAndDate(currency, date)) {
            log.info("Found rate for {} and {} in cache", currency, date);
            ExchangeForCurrencyEntity rateForCache = exchangeForCurrencyDAO.findByCurrencyAndDate(currency, date);
            return modelMapper.map(rateForCache, ExchangeForCurrency.class);
        }

        currency = currency.toUpperCase();
        ExchangeForCurrency result = null;
        NBPRate nbpRate = nbpService.getSellExchangeForCurrency(currency, date);

        result = mapAndSaveInCache(currency, nbpRate);

        return result;
    }

    private ExchangeForCurrency mapAndSaveInCache(String currency, NBPRate nbpRate) {
        ExchangeForCurrency result;
        result = new ExchangeForCurrency();
        result.setCurrency(currency);
        result.setRate(nbpRate.getMid());
        result.setDate(nbpRate.getEffectiveDate());
        log.debug("Save to cache {} for {}", currency, nbpRate.getEffectiveDate());
        exchangeForCurrencyDAO.save(modelMapper.map(result, ExchangeForCurrencyEntity.class));

        return result;
    }
}
