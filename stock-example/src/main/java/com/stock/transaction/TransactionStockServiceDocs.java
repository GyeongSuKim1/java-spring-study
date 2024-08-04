package com.stock.transaction;

import com.stock.application.StockService;

/**
 * 트렌잭션을 실행하면 아래와 같이 트랜잭션 클래스를 새로 생성해서 실행하고
 * 트랜잭션이 시작한 후에 메소드를 호출 하고 호출이 된다면 트랜잭션이 종료됨
 * 트랜잭션 종료시점에 데이터베이스에 업데이트 하기 때문에 실제 데이터가 업데이트 되기 전에 해당 메서드를 실행할수 있음,
 * 그렇게 되면 다른 쓰레드가 데이터가 갱신되기 전에 데이터를 가져 가게 된다.
 */
public class TransactionStockServiceDocs {
    private final StockService stockService;

    public TransactionStockServiceDocs(StockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        startTransaction();

        stockService.decrease(id, quantity);

        endTransaction();
    }

    private void startTransaction() {
        System.out.println("Transaction Start");
    }

    private void endTransaction() {
        System.out.println("Commit");
    }
}
