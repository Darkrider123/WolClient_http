package com.example.wolclient;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private static final Json json = new Json();

    public static Json getInstance() {
        return json;
    }

    public final ObjectMapper objectMapper = new ObjectMapper();

    private Json() {
    }

}