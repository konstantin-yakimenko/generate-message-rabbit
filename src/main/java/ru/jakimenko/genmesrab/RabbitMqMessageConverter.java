package ru.jakimenko.genmesrab;

import com.google.gson.Gson;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.*;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Created by kyyakime on 24.03.17.
 */
@Component
public class RabbitMqMessageConverter implements MessageConverter {

    private final static String HEADER_TERMINAL_DEVICE = "TerminalDeviceId";
    private final static String HEADER_OPERATION_DATE = "OperationDate";
    private final static String HEADER_TRIES = "Tries";

    private static final ThreadLocal<DateTimeFormatter> THREAD_CACHE = new ThreadLocal<DateTimeFormatter> ();
    private final static String FORMAT_DATETIME = "yyyy-MM-dd'T'HH:mm:ssX";

    private final static String DEFAULT_DESCRIPTION = "Rabbit verification";

    @Override
    public Message toMessage(final Object o, final MessageProperties messageProperties) throws MessageConversionException {
        Long td_id = (Long)o;
        MessageProperties mp = new MessageProperties();
        byte[] body = DEFAULT_DESCRIPTION.getBytes();
        Map<String, Object> headers = mp.getHeaders();
        headers.put(HEADER_TERMINAL_DEVICE, td_id);
        headers.put(HEADER_OPERATION_DATE, getFormat().format(ZonedDateTime.now()));
        headers.put(HEADER_TRIES, 0);
        return MessageBuilder.withBody(body).andProperties(mp).build();
    }

    @Override
    public Object fromMessage(final Message message) throws MessageConversionException {
        return null;
    }

    private static DateTimeFormatter getFormat() {
        DateTimeFormatter format = THREAD_CACHE.get();
        if (format == null) {
            format = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
            THREAD_CACHE.set(format);
        }
        return format;
    }

}
