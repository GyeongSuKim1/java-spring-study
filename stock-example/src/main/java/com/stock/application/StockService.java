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

//    @Transactional

    /**
     * @synchronized 를 사용 했을때 생기는 문제점
     * JAVA 의 @synchronized 는 하나의 프로세스안에서만 보장을 해줌, 서버가 한대일 경우 데이터의 접근을 서버가 한대만 해서 괜찮지만 서버가 여러개일 경우
     * 데이터의 접근을 여러개가 할 수 있음.
     * 실제 운영하는 서비스는 대부분 두대 이상의 서버를 사용하기 때문에 @synchronized 는 거의 사용하지 않음.
     */
    public synchronized void decrease(final Long id, final Long quantity) {
        final Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decreaseQuantity(quantity);

        stockRepository.save(stock);
    }
}
