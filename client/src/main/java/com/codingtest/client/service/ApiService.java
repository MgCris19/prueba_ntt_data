package com.codingtest.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Mono<String> callDeleteService(String url) {
        return webClientBuilder.build()
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }
}