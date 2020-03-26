package hello;

import java.math.BigDecimal;

public interface StockExchange {
    BigDecimal currentPrice(String symbol);
}
