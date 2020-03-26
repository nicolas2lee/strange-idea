package tao.strange.idea.http.session.controller;

import io.github.resilience4j.bulkhead.*;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Consumer;
import java.util.function.Supplier;

@RestController
public class HelloController {

    private final static Logger LOG = LoggerFactory.getLogger(HelloController.class);

    private final RestTemplate simpleRestTemplate;
    private final RestTemplate threadpoolRestTemplate;

    private final static  String GITHUB_URL = "https://raw.githubusercontent.com/nicolas2lee/ibmcloud-workshop/master/env/play-with-kube/install.sh";
    private final static  String LOCAL_URL = "http://localhost:8090/longtest";

    public HelloController(@Qualifier("simple") RestTemplate simpleRestTemplate,
                           @Qualifier("threadPool")RestTemplate threadpoolRestTemplate) {
        this.simpleRestTemplate = simpleRestTemplate;
        this.threadpoolRestTemplate = threadpoolRestTemplate;
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) throws InterruptedException {
        String url = LOCAL_URL;
        final String result = simpleRestTemplate.getForObject(url, String.class);
        //System.out.println(result);
        LOG.info("test");
        //Thread.sleep(5000);
        return result;
    }

    @GetMapping("/test1")
    public String test1(HttpServletRequest request) throws InterruptedException {
        String url = LOCAL_URL;
        final String result = threadpoolRestTemplate.getForObject(url, String.class);
        //System.out.println(result);
        LOG.info("test1");
        //Thread.sleep(5000);
        return result;
    }

    @GetMapping("/test2")
    public String test2(HttpServletRequest request){
        //BulkheadRegistry bulkheadRegistry = BulkheadRegistry.ofDefaults();
        BulkheadConfig config = BulkheadConfig.ofDefaults();

        Bulkhead bulkhead = Bulkhead.of("name", config);

        Supplier<String> supplier = Bulkhead.decorateSupplier(bulkhead,
                () -> doHttpOperation());
        Consumer consumer = o -> System.out.println(o.toString());
        return (String) Try.ofSupplier(supplier).andThen(consumer).get();
    }

    private String doHttpOperation(){
        String url = "https://raw.githubusercontent.com/nicolas2lee/ibmcloud-workshop/master/env/play-with-kube/install.sh";
        return simpleRestTemplate.getForObject(url, String.class);
    }
}
