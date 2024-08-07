package com.codingtest.account.dto;

public class ApiResponse {

    private Boolean success;

    private String message;

    private Object data;

    private ApiResponse.Error error;

    private String traceid;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResponse.Error getError() {
        return error;
    }

    public void setError(ApiResponse.Error error) {
        this.error = error;
    }

    public String getTraceid() {
        return traceid;
    }

    public void setTraceid(String traceid) {
        this.traceid = traceid;
    }

    public static class Error {

        private String code;

        private String detail;

        public void setCode(String code) {
            this.code = code;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public Error() {}

        //@ConstructorProperties({"code", "detail"})
        public Error(String code, String detail) {
            this.code = code;
            this.detail = detail;
        }

        public String getCode() {
            return this.code;
        }

        public String getDetail() {
            return this.detail;
        }
    }
}