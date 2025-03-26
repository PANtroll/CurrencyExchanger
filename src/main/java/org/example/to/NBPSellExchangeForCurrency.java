package org.example.to;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class NBPSellExchangeForCurrency {

    private String table;
    private String currency;
    private String code;
    private List<NBPSellRate> rates;
}
