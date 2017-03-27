package ru.jakimenko.genmesrab;

import com.google.common.collect.Lists;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by kyyakime on 24.03.17.
 */
@Component
public class GenerateMessages {

    private final MessagesDAO messagesDAO;
    private final RabbitTemplate rabbitTemplate;
    private final Integer threadPoolSize;
    ExecutorService taskExecutor;

    public GenerateMessages(final MessagesDAO messagesDAO, final RabbitTemplate rabbitTemplate,
                            @Value("${thread.pool.size}") final Integer threadPoolSize) {
        this.messagesDAO = messagesDAO;
        this.rabbitTemplate = rabbitTemplate;
        this.threadPoolSize = threadPoolSize;
        taskExecutor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void generate() throws Exception {
        List<Long> td_list = messagesDAO.getTerminalDevices();

        if (td_list.stream().distinct().count() != 300) {
            System.err.println("Has repeated elements");
            return;
        }

        if (td_list.isEmpty()) {
            return;
        }

        List<List<Long>> lists = Lists.partition(td_list, 30);
        for (List<Long> list : lists) {
            taskExecutor.submit(new SendMessageTask(new ArrayList<>(list)));
        }
        taskExecutor.shutdown();
        taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.print("Finish send" );
    }

    private class SendMessageTask implements Runnable {
        private final List<Long>  td_ids;

        public SendMessageTask(final List<Long> td_ids) {
            this.td_ids = td_ids;
        }

        @Override
        public void run() {
            td_ids.stream().forEach(t -> rabbitTemplate.convertAndSend(t));
        }
    }


}
