package org.example.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "RATES")
@Data
public class ExchangeForCurrencyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private BigDecimal rate;
    @Column
    private String currency;
    @Column
    private Date date;

}
