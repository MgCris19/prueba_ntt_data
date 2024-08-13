package com.codingtest.client.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Error(String code, String error, String cause) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String code;
        private String error;
        private String cause;

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder cause(String cause) {
            this.cause = cause;
            return this;
        }

        public Error build() {
            return new Error(code, error, cause);
        }
    }
}