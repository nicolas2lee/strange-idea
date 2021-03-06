package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BookstoreApplication {

  int count =0;

  private final Client client;

  @Autowired
  public BookstoreApplication(Client client) {
    this.client = client;
  }

  @PostMapping(value = "/recommended")
  public ResponseEntity<String> readingList() throws InterruptedException {
    count ++;
  /*  if (Math.random()>0.5){
      System.out.println(count);
      Thread.sleep(Duration.ofSeconds(5).toMillis());
      return ResponseEntity.accepted().body( "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Pack)");
    }
    System.out.println("forbidden");*/
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not allowed");
  }

  @PostMapping(value = "/stockexchange")
  public ResponseEntity<String> stockExchange() throws InterruptedException {
    client.main("apple");
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("not allowed");
  }

  public static void main(String[] args) {
    SpringApplication.run(BookstoreApplication.class, args);
  }
}
