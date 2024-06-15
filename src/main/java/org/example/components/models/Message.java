package org.example.components.models;

public class Message {
    private String messageString;


    public Message() {
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    @Override
    public String toString() {
        return messageString;
    }
}
