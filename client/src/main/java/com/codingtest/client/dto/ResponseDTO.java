package com.codingtest.client.dto;

public class ResponseDTO {
    private Boolean success;

    private String message;

    private Object data;

    private Error error;

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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    //@ApiModel(description = "Indica el detalle del error en el backend.")
    public static class Error {
        //@ApiModelProperty("Codigo de error en el backend.")
        private String code;

        //@ApiModelProperty("Detalle del error en el backend.")
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

