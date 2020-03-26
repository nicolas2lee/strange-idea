package hello;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("beijingStockExchange")
public class BeijingStockExchange implements StockExchange {
    @Override
    public BigDecimal currentPrice(String symbol) {
        return BigDecimal.ONE;
    }
}
