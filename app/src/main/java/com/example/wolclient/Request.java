package com.example.wolclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Request {

    private static Request request = new Request();

    public static Request getInstance() {
        return request;
    }

    private final Json json;

    private Request() {
        this.json = Json.getInstance();
    }

    public GenericResponse get(String url) throws IOException {
        return makeRequest(url, HTTPMethods.GET, null);
    }

    public GenericResponse post(String url, Object body_object) throws IOException {
        return makeRequest(url, HTTPMethods.POST, body_object);
    }

    public GenericResponse put(String url, Object body_object) throws IOException {
        return makeRequest(url, HTTPMethods.PUT, body_object);
    }

    public GenericResponse makeRequest(String url, HTTPMethods httpMethod, Object body_object) throws IOException {

        URL url_object = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) url_object.openConnection();
        connection.setConnectTimeout(Config.connectionTimeout);
        connection.setReadTimeout(Config.connectionReadTimeout);
        connection.setRequestMethod(httpMethod.toString());

        connection.setRequestProperty("Content-TYpe", "application/json");
        connection.setRequestProperty("Accept", "application/json");

        if (httpMethod == HTTPMethods.POST)
            connection.setDoOutput(true);

        if (body_object != null) {
            String objectAsString = json.objectMapper.writeValueAsString(body_object);
            byte[] body = objectAsString.getBytes("utf-8");
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(body, 0, body.length);
        }
        else
            connection.setRequestProperty("Content-Length", Integer.toString(0));

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;

        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        GenericResponse genericResponse = new GenericResponse(
                connection.getResponseCode(),
                connection.getResponseMessage(),
                HTTPMethods.valueOf(connection.getRequestMethod()),
                response.toString()
        );

        connection.disconnect();
        return genericResponse;
    }

}