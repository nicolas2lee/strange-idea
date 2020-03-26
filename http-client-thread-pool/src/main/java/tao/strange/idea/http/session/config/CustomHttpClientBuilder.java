package tao.strange.idea.http.session.config;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomHttpClientBuilder {
    @Bean("threadPool")
    public RestTemplate threadPool(RestTemplateBuilder restTemplateBuilder){
        //HttpClientBuilder

        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
        clientConnectionManager.setMaxTotal(5);
        final CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                .setConnectionManager(clientConnectionManager)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
        //SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        final RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

    @Bean("simple")
    public RestTemplate simple(RestTemplateBuilder restTemplateBuilder){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        final RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
}
