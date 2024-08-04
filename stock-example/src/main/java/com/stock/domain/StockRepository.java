package com.stock.domain;

import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findById(final Long id);
    Stock save(final Stock stock);
}
