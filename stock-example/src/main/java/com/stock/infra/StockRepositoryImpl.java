package com.stock.infra;

import com.stock.domain.Stock;
import com.stock.domain.StockRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StockRepositoryImpl implements StockRepository {
    private final JpaStockRepository jpaStockRepository;

    public StockRepositoryImpl(final JpaStockRepository jpaStockRepository) {
        this.jpaStockRepository = jpaStockRepository;
    }

    @Override
    public Optional<Stock> findById(final Long id) {
        return jpaStockRepository.findById(id);
    }

    @Override
    public Stock save(Stock stock) {
        return jpaStockRepository.saveAndFlush(stock);
    }

    @Override
    public void deleteAll() {
        jpaStockRepository.deleteAll();
    }
}
