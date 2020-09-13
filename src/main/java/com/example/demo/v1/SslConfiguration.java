package com.example.demo.v1;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

/**
 * SSL configuration for token validation request.
 */
@Configuration
public abstract class SslConfiguration {

//    @Value("${http.client.ssl.trust-store}")
//    private Resource keyStore;
//    @Value("${http.client.ssl.trust-store-password}")
//    private String keyStorePassword;

//    @Bean
//    RestTemplate restTemplate() throws Exception {
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(
//                        keyStore.getURL(),
//                        keyStorePassword.toCharArray()
//                                  ).build();
//        SSLConnectionSocketFactory socketFactory =
//                new SSLConnectionSocketFactory(sslContext);
//        HttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(socketFactory).build();
//        HttpComponentsClientHttpRequestFactory factory =
//                new HttpComponentsClientHttpRequestFactory(httpClient);
//        return new RestTemplate(factory);
//    }

    @PostConstruct
    private void configureSSL() {
        //set to TLSv1.1 or TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");

        //todo add configs
        System.setProperty("javax.net.ssl.trustStore", "classpath:client-truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword","wso2carbon");
    }
}
