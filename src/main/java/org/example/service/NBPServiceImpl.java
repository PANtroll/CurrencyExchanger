package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.to.NBPMidExchange;
import org.example.to.NBPMidRate;
import org.example.to.NBPSellExchangeForCurrency;
import org.example.to.NBPSellRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class NBPServiceImpl implements NBPService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${NBP.URI.sell}")
    private String sellURI;

    @Value("${NBP.URI.mid}")
    private String midURI;

    @Override
    public NBPSellRate getSellExchangeForCurrency(String currency, Date date) {
        NBPSellRate result = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String URL = sellURI + currency + '/' + formatter.format(date);
            ResponseEntity<NBPSellExchangeForCurrency> fetchedObject = restTemplate.getForEntity(URL, NBPSellExchangeForCurrency.class);
            if (!HttpStatus.OK.equals(fetchedObject.getStatusCode())) {
                log.warn("Status code is not 200 for getting data from: " + sellURI);
                return result;
            }
            NBPSellExchangeForCurrency body = fetchedObject.getBody();
            List<NBPSellRate> rates = body.getRates();
            if (rates == null || rates.isEmpty()) {
                log.warn("No rates found for currency: " + currency);
                return result;
            }
            if (rates.size() > 1) {
                log.warn("More than one rate found for currency: " + currency);
            }
            result = rates.get(0);

        } catch (Exception e) {
            log.error("Error when fetching data from API: " + sellURI);
        }

        return result;
    }

    @Override
    public List<NBPMidRate> getBuyExchangeForCurrencies(List<String> currencies, Date date) {//todo
        List<NBPMidRate> result = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String URL = midURI + formatter.format(date);
            ResponseEntity<NBPMidExchange[]> fetchedObject = restTemplate.getForEntity(URL, NBPMidExchange[].class);
            if (!HttpStatus.OK.equals(fetchedObject.getStatusCode())) {
                log.warn("Status code is not 200 for getting data from: " + sellURI);
                return result;
            }
            NBPMidExchange body = fetchedObject.getBody()[0];
            List<NBPMidRate> rates = body.getRates();
            if (rates == null || rates.isEmpty()) {
                log.warn("No rates found for currency: " + currencies);
                return result;
            }
            result = rates.stream().filter(rate -> currencies.contains(rate.getCode())).toList();

        } catch (Exception e) {
            log.error("Error when fetching data from API: " + sellURI);
        }

        return result;
    }
}
