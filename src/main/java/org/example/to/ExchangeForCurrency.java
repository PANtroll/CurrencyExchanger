package org.example.to;

import lombok.Data;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@Data
@ResponseStatus
public class ExchangeForCurrency {

    private BigDecimal rate;
    private String currency;
    private String date;

}
