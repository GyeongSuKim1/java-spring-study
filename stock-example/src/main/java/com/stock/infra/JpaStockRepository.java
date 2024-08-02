package com.stock.infra;

import com.stock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStockRepository extends JpaRepository<Stock, Long> {
}
