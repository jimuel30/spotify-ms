package com.aparzero.spotfy_ms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {


    private final String frontEndDomain;
    private final String proxyHost;

    public CorsConfig(@Value("${cors.front-end-domain}") String frontEndDomain,
                      @Value("${proxy.host}") String proxyHost) {
        this.frontEndDomain = frontEndDomain;
        this.proxyHost = proxyHost;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from localhost:4200 (your Angular app)
        registry.addMapping("/spotify-ms/**")  // Adjust the pattern to match your API
                .allowedOrigins(frontEndDomain,"http://localhost:80",proxyHost)  // Allow requests from Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow these HTTP methods
                .allowCredentials(true)
                .allowedHeaders("*");  // Allow any headers
    }
}
