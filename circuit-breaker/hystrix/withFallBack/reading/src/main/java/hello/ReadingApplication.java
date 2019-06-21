package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@EnableCircuitBreaker
@RestController
@SpringBootApplication
public class ReadingApplication {

  @Autowired
  private BookService bookService;

  @Bean
  public RestTemplate rest(RestTemplateBuilder builder) {
    return builder
    //        .setConnectTimeout(Duration.ofSeconds(5))
      //      .setReadTimeout(Duration.ofSeconds(5))
            .build();
  }

  @RequestMapping("/to-read")
  public String toRead() {
    return bookService.readingList();
  }

  public static void main(String[] args) {
    SpringApplication.run(ReadingApplication.class, args);
  }
}
