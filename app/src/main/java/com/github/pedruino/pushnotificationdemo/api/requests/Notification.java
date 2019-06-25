package com.github.pedruino.pushnotificationdemo.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Notification implements Serializable {
    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String message;

    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
