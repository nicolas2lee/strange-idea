package hello;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TokyoStockExchange implements StockExchange {
    @Override
    public BigDecimal currentPrice(String symbol) {
        return BigDecimal.ZERO;
    }
}
