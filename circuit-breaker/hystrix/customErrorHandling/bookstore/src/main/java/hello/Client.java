package hello;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class Client {
    private StockExchange stockExchange;

    public Client(@Qualifier("beijingStockExchange") StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    public void setStockExchange(StockExchange stockExchange) {
        this.stockExchange = stockExchange;
    }

    public void main(String symbol) {
	// write your code here
        final StockExchange stockExchange = createStockExchange("beijing");
        final BigDecimal tokyoPrice = stockExchange.currentPrice("apple");
        //final BigDecimal beijingPrice = new BeijingStockExchange().currentPrice("apple");
        //stockExchange.currentPrice(symbol);
        //System.out.println(tokyoPrice);
    }

    StockExchange createStockExchange(String stockExchangeType){
        switch (stockExchangeType) {
            case "beijing":
                return new BeijingStockExchange();
            case "tokyo":
                return new TokyoStockExchange();
            default:
                return new BeijingStockExchange();
        }
    }

    void test(){

    }

    private void doThrow() throws IOException {
        throw new IOException();
    }
}
