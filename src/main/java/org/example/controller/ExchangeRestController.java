package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.example.service.ExchangeService;
import org.example.to.ExchangeForCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/exchange")
@Log4j2
public class ExchangeRestController {

    public static final String MINUS = "-";
    @Autowired
    ExchangeService exchangeService;

    private static Date parseDate(String date) {//todo date validation
        Date dateObject = null;
        if (date != null) {
            String[] split = date.split(MINUS);
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(Calendar.YEAR, Integer.parseInt(split[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(split[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split[2]));
            dateObject = calendar.getTime();
        }

        if (dateObject == null || dateObject.after(new Date())) {
            log.debug("Change future date {} to current", dateObject);
            dateObject = new Date();
        }
        return dateObject;
    }

    @GetMapping("/sell/{currency}")
    public ResponseEntity<ExchangeForCurrency> getSellExchangeForCurrency(@PathVariable String currency, @RequestParam(required = false) String date) {
        ResponseEntity<ExchangeForCurrency> result = null;
        log.debug("Request coming for getSellExchangeForCurrency with {} and {}", currency, date);
        if (Strings.isBlank(currency)) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return result;
        }
        Date dateObject = parseDate(date);
        ExchangeForCurrency body = exchangeService.getSellExchangeForCurrency(currency, dateObject);
        result = new ResponseEntity<>(body, HttpStatus.OK);

        return result;
    }

    @GetMapping("/buy")
    public ResponseEntity<List<ExchangeForCurrency>> getBuyExchangeForCurrencies(@RequestParam() String currencies, @RequestParam(required = false) String date) {//, @RequestParam BigDecimal money
        ResponseEntity<List<ExchangeForCurrency>> result = null;

        log.debug("Request coming for getBuyExchangeForCurrencies with {} and {}", currencies, date);
        String[] currenciesArr = currencies.split(",");
        if (currenciesArr == null || currenciesArr.length == 0) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return result;
        }
        List<String> currenciesList = Arrays.stream(currenciesArr).map(String::trim).toList();
        Date dateObject = parseDate(date);
        List<ExchangeForCurrency> body = exchangeService.buyPlnExchangeByCurrencies(currenciesList, dateObject);
        result = new ResponseEntity<>(body, HttpStatus.OK);

        return result;
    }

}
