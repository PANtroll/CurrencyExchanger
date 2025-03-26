package org.example.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NBPRate {

    private String no;
    private String effectiveDate;
    private BigDecimal mid;

}
