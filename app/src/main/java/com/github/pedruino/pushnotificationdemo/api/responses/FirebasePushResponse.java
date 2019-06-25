package com.github.pedruino.pushnotificationdemo.api.responses;

public class FirebasePushResponse {

    private String messageId;
    private String error;

    public FirebasePushResponse() {
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

//    public int success;

}
