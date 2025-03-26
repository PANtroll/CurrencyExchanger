package org.example.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NBPMidRate {

    private String currency;
    private String code;
    private BigDecimal mid;
}
