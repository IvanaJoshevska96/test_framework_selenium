package com.maersk.procurement.enums;

import lombok.Getter;

@Getter
public enum MessageType {
    LOCAL_TOPIC("test");

    private final String topicName;

    MessageType(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
