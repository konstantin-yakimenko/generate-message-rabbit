package ru.jakimenko.genmesrab;

/**
 * Created by kyyakime on 24.03.17.
 */
public class RabbitMqMessage {

    private final Long id;
    private final String messageProperties;
    private final byte[] body;

    public RabbitMqMessage(String messageProperties, byte[] body) {
        this(null, messageProperties, body);
    }

    public RabbitMqMessage(Long id, String messageProperties, byte[] body) {
        this.id = id;
        this.messageProperties = messageProperties;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public String getMessageProperties() {
        return messageProperties;
    }

    public byte[] getBody() {
        return body;
    }

}
