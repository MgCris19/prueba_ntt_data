package com.codingtest.client.service.impl;

import com.codingtest.client.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> callDeleteService(String url) {
        return webClientBuilder.build()
                .delete()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class);
    }
}