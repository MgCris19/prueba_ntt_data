package com.codingtest.client.service;

import reactor.core.publisher.Mono;

public interface ApiService {

    Mono<String> callDeleteService(String url);
}
