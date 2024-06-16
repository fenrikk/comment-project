package org.example.components.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Message {
    @NotEmpty
    @Size(max =  5000)
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
