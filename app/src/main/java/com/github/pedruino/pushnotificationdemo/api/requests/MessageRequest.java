package com.github.pedruino.pushnotificationdemo.api.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MessageRequest implements Serializable {

    @JsonProperty("notification")
    private Notification notification;

    @JsonProperty("to")
    private String to;

    public MessageRequest(String to, String title, String message) {
        this.to = to;
        this.notification = new Notification(title, message);
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}
