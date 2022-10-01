package com.musala.drones.message;


import com.musala.drones.message.constant.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RestMessage {
    private String header;
    private MessageType messageType;
    private List<String> messages;

    public RestMessage(List<String> messages) {
        this.messages = messages;
    }


    public RestMessage(String header, MessageType messageType) {
        this.header = header;
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "{" +
                "\"header\":\"" + header + "\""+
                ", \"messages\":" + messages +
                "}";
    }
}