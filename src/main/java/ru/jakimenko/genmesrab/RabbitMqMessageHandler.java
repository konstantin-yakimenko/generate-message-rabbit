package ru.jakimenko.genmesrab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by kyyakime on 24.03.17.
 */
@Component
public class RabbitMqMessageHandler {
    private final AtomicLong exportedCount = new AtomicLong(0);

    public void handleMessage(RabbitMqMessage message) {
    }
}
