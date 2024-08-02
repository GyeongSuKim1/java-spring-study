package com.stock.infra;

import com.stock.domain.StockRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StockRepositoryImpl implements StockRepository {
    private final JpaStockRepository jpaStockRepository;

    public StockRepositoryImpl(final JpaStockRepository jpaStockRepository) {
        this.jpaStockRepository = jpaStockRepository;
    }


}
