package org.example.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeForCurrency {

    private BigDecimal rate;
    private String currency;
    private String date;

}
