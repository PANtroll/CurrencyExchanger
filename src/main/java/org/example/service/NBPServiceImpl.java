package org.example.service;

import lombok.extern.log4j.Log4j2;
import org.example.to.NBPExchangeForCurrency;
import org.example.to.NBPRate;
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
    public NBPRate getSellExchangeForCurrency(String currency, Date date) {
        NBPRate result = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String URL = sellURI + currency + '/' + formatter.format(date);
            ResponseEntity<NBPExchangeForCurrency> fetchedObject = restTemplate.getForEntity(URL, NBPExchangeForCurrency.class);
            if (!HttpStatus.OK.equals(fetchedObject.getStatusCode())) {
                log.warn("Status code is not 200 for getting data from: " + sellURI);
                return result;
            }
            NBPExchangeForCurrency body = fetchedObject.getBody();
            List<NBPRate> rates = body.getRates();
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
    public List<NBPRate> getBuyExchangeForCurrencies(List<String> currencies, Date date) {
        return null;
    }
}
