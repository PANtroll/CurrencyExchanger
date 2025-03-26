package org.example.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.example.service.ExchangeService;
import org.example.to.ExchangeForCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/exchange")
@Log4j2
public class ExchangeRestController {

    @Autowired
    ExchangeService exchangeService;

    @GetMapping("/get/{currency}")
    public ResponseEntity<ExchangeForCurrency> getSellExchangeForCurrency(@PathVariable String currency, @RequestParam(required = false) String date) {
        ResponseEntity<ExchangeForCurrency> result = null;
        log.info("Request coming with {} and {}", currency, date);
        if (Strings.isBlank(currency)) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return result;
        }
        Date dateObject = null;
        if (date != null) {
            String[] split = date.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(split[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(split[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(split[2]));
            dateObject = calendar.getTime();
        }

        if (dateObject == null || dateObject.after(new Date())) {
            dateObject = new Date();
        }
        ExchangeForCurrency body = exchangeService.getSellExchangeForCurrency(currency, dateObject);
        result = new ResponseEntity<>(body, HttpStatus.OK);

        return result;
    }

    @GetMapping("/get2/{currency}")
    public ResponseEntity<ExchangeForCurrency> getSellExchangeForCurrency(@PathVariable String currency) {
        ResponseEntity<ExchangeForCurrency> result = null;
        Date date = new Date();
        log.info("Request coming with {} and {}", currency, date);
        if (Strings.isBlank(currency)) {
            result = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return result;
        }
        if (date == null || date.after(new Date())) {
            date = new Date();
        }
        ExchangeForCurrency body = exchangeService.getSellExchangeForCurrency(currency, date);
        result = new ResponseEntity<>(body, HttpStatus.OK);

        return result;
    }

}
