package com.example.wolclient;


public class WolClient {

    private final Json json = Json.getInstance();
    private final Request request = Request.getInstance();

    public String startServer() throws java.io.IOException {
        return json.objectMapper.readValue(request.post(Config.startURL, null).getBody(), MessageDto.class).getMessage();
    }

    public String stopServer() throws java.io.IOException{
       return json.objectMapper.readValue(request.post(Config.stopURL, null).getBody(), MessageDto.class).getMessage();
    }

    public String getServerStatus() throws java.io.IOException{
        return json.objectMapper.readValue(request.get(Config.statusURL).getBody(), MessageDto.class).getMessage();
    }

    public String restartServer() throws java.io.IOException{
        return json.objectMapper.readValue(request.put(Config.restartURL, null).getBody(), MessageDto.class).getMessage();
    }

}
