package com.example.wolclient;

public class GenericResponse {

    private final Integer responseCode;

    private final String responseMessage;

    private final HTTPMethods requestMethod;

    private final String body;


    public GenericResponse(Integer responseCode, String responseMessage, HTTPMethods requestMethod, String body) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.requestMethod = requestMethod;
        this.body = body;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public HTTPMethods getRequestMethod() {
        return requestMethod;
    }

    public String getBody() {
        return body;
    }
}