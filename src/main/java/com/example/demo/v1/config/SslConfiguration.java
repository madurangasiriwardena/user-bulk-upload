package com.example.demo.v1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * SSL configuration for token validation request.
 */
@Configuration
public abstract class SslConfiguration {

    @Value("${http.client.ssl.trust-store}")
    private String trustStore;
    @Value("${http.client.ssl.trust-store-password}")
    private String trustStorePassword;

    @PostConstruct
    private void configureSSL() {
        //set to TLSv1.1 or TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");

        System.setProperty("javax.net.ssl.trustStore", trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    }
}
