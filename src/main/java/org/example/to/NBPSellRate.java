package org.example.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NBPSellRate {

    private String currency;
    private String code;
    private BigDecimal bid;
    private BigDecimal ask;

}
