package org.example.to;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class NBPMidExchange {

    private String table;
    private String no;
    private String effectiveDate;
    private List<NBPMidRate> rates;
}

