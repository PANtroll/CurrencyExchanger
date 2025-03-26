package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.dao.ExchangeForCurrencyDAO;
import org.example.model.ExchangeForCurrencyEntity;
import org.example.to.ExchangeForCurrency;
import org.example.to.NBPMidRate;
import org.example.to.NBPSellRate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        NBPSellRate nbpSellRate = nbpService.getSellExchangeForCurrency(currency, date);

        result = mapAndSaveInCache(currency, nbpSellRate, date);

        return result;
    }

    @Override
    public List<NBPMidRate> buyPlnExchangeByCurrencies(List<String> currencies, Date date) {

        currencies = currencies.stream().map(currency -> currency.toUpperCase().trim()).toList();
        List<NBPMidRate> nbpmidRateList = nbpService.getBuyExchangeForCurrencies(currencies, date);//todo change data structure
        nbpmidRateList.forEach(rate -> rate.setMid(BigDecimal.ONE.setScale(4, RoundingMode.HALF_UP).divide(rate.getMid(), RoundingMode.HALF_UP)));
        return nbpmidRateList;
    }

    private ExchangeForCurrency mapAndSaveInCache(String currency, NBPSellRate nbpSellRate, Date date) {
        ExchangeForCurrency result;
        result = new ExchangeForCurrency();
        result.setCurrency(currency);
        result.setRate(nbpSellRate.getBid());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        result.setDate(formatter.format(date));
        log.debug("Save to cache {} for {}", currency, date);
        exchangeForCurrencyDAO.save(modelMapper.map(result, ExchangeForCurrencyEntity.class));

        return result;
    }
}
