package com.stock.application;

import com.stock.domain.Stock;
import com.stock.domain.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(final Long id, final Long quantity) {
        final Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decreaseQuantity(quantity);

        stockRepository.save(stock);
    }
}
