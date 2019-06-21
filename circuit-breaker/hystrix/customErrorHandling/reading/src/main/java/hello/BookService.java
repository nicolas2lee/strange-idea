package hello;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class BookService {

  private final static Logger LOG = LoggerFactory.getLogger(BookService.class);

  private final RestTemplate restTemplate;

  public BookService(RestTemplate rest) {
    this.restTemplate = rest;
  }

  private int count=0;
  private int fail=0;

/*  public String readingListWithoutHystrix() {
    count++;
    URI uri = URI.create("http://localhost:8090/recommended");
    System.out.println("count"+count);
    try {
      return this.restTemplate.getForObject(uri, String.class);
    } catch (ResourceAccessException e) {
      LOG.error("failed", e);
      return "timeout exception";
    }
  }*/

  @HystrixCommand(
          fallbackMethod = "fail",
          commandProperties = {
                  @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
                    ,@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
          }
  )
  public String readingList() {
    count++;
    URI uri = URI.create("http://localhost:8090/recommended");
    System.out.println("count"+count);
    return this.restTemplate.getForObject(uri, String.class);
  }

  public String fail(Throwable t) throws Throwable {
    LOG.error("",t);
    if (t instanceof HystrixTimeoutException){
      LOG.error("time out hystrix exception");
    }
    throw t;
  }

}
