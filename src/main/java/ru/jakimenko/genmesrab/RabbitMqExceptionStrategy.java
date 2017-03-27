package ru.jakimenko.genmesrab;

import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.stereotype.Component;

/**
 * Created by kyyakime on 24.03.17.
 */
@Component
public class RabbitMqExceptionStrategy implements FatalExceptionStrategy {
    @Override
    public boolean isFatal(Throwable throwable) {
        return true;
    }
}
