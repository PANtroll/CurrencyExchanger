package org.example.dao;

import org.example.model.ExchangeForCurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ExchangeForCurrencyDAO extends JpaRepository<ExchangeForCurrencyEntity, Integer> {

    boolean existsByCurrencyAndDate(String currency, Date date);

    ExchangeForCurrencyEntity findByCurrencyAndDate(String currency, Date date);
}
