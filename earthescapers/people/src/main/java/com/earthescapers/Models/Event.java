package com.earthescapers.Models;


public class Event {
    private String type;
    private String payload;

    public Event(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
