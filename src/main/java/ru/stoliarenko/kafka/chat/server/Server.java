package ru.stoliarenko.kafka.chat.server;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.*;

import static ru.stoliarenko.kafka.chat.constant.BootstrapServer.MAIN_SERVER;
import static ru.stoliarenko.kafka.chat.constant.ChatTopic.MAIN_CHAT;
import static ru.stoliarenko.kafka.chat.constant.ChatTopic.SERVER;
import static ru.stoliarenko.kafka.chat.constant.KafkaPropertyValue.*;

/**
 * Сервер чата.
 */
@Log4j2
public class Server {

    /**
     * Участники чата, ключ - идентификатор пользователя.
     */
    @Nonnull
    private final Map<String, String> connectedUsers = new HashMap<>();

    /**
     * Поставщик сообщений apache-kafka.
     */
    @Nullable
    private KafkaProducer<String, String> producer;

    /**
     * Потребитель сообщений apache-kafka.
     */
    @Nullable
    private KafkaConsumer<String, String> consumer;

    /**
     * Максимальное время ожидания сообщения.
     */
    @Nonnull
    private final Duration messageReadTimeout = Duration.ofMillis(100);

    /**
     * Имя отправителя для системных сообщений.
     */
    @Nonnull
    private final String systemMessagePrefix = "SYSTEM";

    /**
     * Запустить сервер.
     */
    public void start() {
        init();

        if (Objects.isNull(producer) || Objects.isNull(consumer)) {
            log.warn("Null communication objects detected. Shutting down.");
            stop();
            System.exit(0);
        }

        while(true) {//TODO exit condition.
            ConsumerRecords<String, String> records = consumer.poll(messageReadTimeout);
            for (ConsumerRecord<String, String> record : records) {
                if (record.value().startsWith("/")) {
                    processCommand(record);
                    continue;
                }
                final String userName = connectedUsers.get(record.key());
                if (Objects.isNull(userName)) {
                    sendPrivateMessage(record.key(), systemMessagePrefix, "You are not logged in!");
                    continue;
                }
                sendBroadcastMessage(userName, record.value());
            }
        }

    }

    /**
     * Обрабатывает полученную команду.
     *
     * @param record - сообщение с командой.
     */
    private void processCommand(@Nonnull final ConsumerRecord<String, String> record) {
        //TODO implement.
    }

    /**
     * Остановить сервер.
     */
    public void stop() {
        if (Objects.nonNull(producer)) {
            producer.close();
            log.info("Producer is stopped.");
        }
        if (Objects.nonNull(consumer)) {
            consumer.close();
            log.info("Consumer is stopped.");
        }
    }

    /**
     * Инициализирует поставщиков и потребителей сообщений apache-kafka.
     */
    private void init() {
        producer = createProducer();
        consumer = createConsumer();
    }

    /**
     * Создаёт потребителя сообщений.
     */
    @Nonnull
    private KafkaConsumer<String, String> createConsumer() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, MAIN_SERVER.getAddress());
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_STRING.getValue());
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_STRING.getValue());
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_LATEST.getValue());
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_SERVER.getValue());

        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(Collections.singletonList(SERVER.getId()));
        return consumer;
    }

    /**
     * Создаёт поставщика для отправки ообщений пользователям.
     */
    @Nonnull
    private KafkaProducer<String, String> createProducer() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, MAIN_SERVER.getAddress());
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, SERIALIZER_STRING.getValue());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SERIALIZER_STRING.getValue());

        return new KafkaProducer<>(kafkaProperties);
    }

    /**
     * Отправляет личное сообщение.
     *
     * @param userId - пользователь, которому будет отправлено сообщение.
     * @param sourceName - имя отправителя.
     * @param message - текст сообщения.
     */
    private void sendPrivateMessage(
            @Nonnull final String userId,
            @Nonnull final String sourceName,
            @Nonnull final String message
    ) {
        if (Objects.isNull(producer)) {
            log.error("Message was not sent, producer is not initialized.");
            return;
        }
        final String compiledMessage = sourceName + ": " + message;
        final ProducerRecord<String, String> record = new ProducerRecord<>(
                userId,
                compiledMessage
        );
        producer.send(record);
    }

    /**
     * Отправляет сообщение в общий чат.
     *
     * @param sourceName - автор сообщения.
     * @param message - текст сообщения.
     */
    private void sendBroadcastMessage(
            @Nonnull final String sourceName,
            @Nonnull final String message
    ) {
        if (Objects.isNull(producer)) {
            log.error("Message was not sent, producer is not initialized.");
            return;
        }
        final String compiledMessage = sourceName + ": " + message;
        final ProducerRecord<String, String> record = new ProducerRecord<>(
                MAIN_CHAT.getId(),
                compiledMessage
        );
        producer.send(record);
    }

}
