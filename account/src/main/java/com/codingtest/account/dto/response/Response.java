package com.codingtest.account.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.Collections;

public record Response<T, E>(
        Boolean success,
        LocalDateTime timestamp,
        T data,
        E error) {

    public ResponseEntity<Response<T, E>> toResponseEntity(HttpStatus status) {
        return new ResponseEntity<>(this, status);
    }

    public static <T, E> Builder<T, E> builder() {
        return new Builder<>();
    }

    public static <T, E> Response<T, E> ok(T data) {
        return Response.<T, E>builder()
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static <T, E> Response<T, E> created(T data) {
        return Response.<T, E>builder()
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static <T, E> Response<T, E> noContent() {
        return Response.<T, E>builder()
                .success(true)
                .data(null)
                .error(null)
                .build();
    }

    public static <T, E> Response<T, E> error(E error) {
        return Response.<T, E>builder()
                .success(false)
                .data(null)
                .error(error)
                .build();
    }

    public static class Builder<T, E> {
        private final LocalDateTime timestamp = LocalDateTime.now();
        private boolean success;
        private T data;
        private E error;

        public Builder<T, E> success(boolean success) {
            this.success = success;
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder<T, E> data(T data) {
            this.data = data != null ? data : (T) Collections.emptyMap();
            return this;
        }

        @SuppressWarnings("unchecked")
        public Builder<T, E> error(E error) {
            this.error = error != null ? error : (E) Error.builder().build();
            return this;
        }

        public Response<T, E> build() {
            return new Response<>(success, timestamp, data, error);
        }
    }
}