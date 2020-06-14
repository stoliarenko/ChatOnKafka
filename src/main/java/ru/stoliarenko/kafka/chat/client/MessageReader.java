package ru.stoliarenko.kafka.chat.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.annotation.Nonnull;
import java.time.Duration;

/**
 * Класс для чтения сообщений потребителем в отдельном потоке.
 */
@Log4j2
@RequiredArgsConstructor
public class MessageReader extends Thread {

    /**
     * Потребитель сообщений.
     */
    @Nonnull
    private final KafkaConsumer<String, String> consumer;

    /**
     * Максимальное время блокировки на чтение сообщений(мс).
     */
    private final long delay = 100;

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(delay));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.value());
                }
            } catch (Exception e) {
                log.info("MessageReader thread is interrupted.");
            }
        }
        consumer.close();
    }

}
