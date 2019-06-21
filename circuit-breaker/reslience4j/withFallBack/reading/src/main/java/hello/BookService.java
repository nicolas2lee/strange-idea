package hello;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class BookService {

  private final RestTemplate restTemplate;

  public BookService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  private int count=0;
  private int fail=0;


  public String readingList() {
    count++;
    URI uri = URI.create("http://localhost:8090/recommended");
    System.out.println("count"+count);
    return this.restTemplate.getForObject(uri, String.class);
    
  }

/*  public String reliable() {
    fail++;
    System.out.println("fail"+fail);
    System.out.println(fail*1.0/count*100+"%");
    return "Cloud Native Java (O'Reilly)";
  }*/

}
