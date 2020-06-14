package ru.stoliarenko.kafka.chat.client;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import ru.stoliarenko.kafka.chat.constant.ChatCommand;
import ru.stoliarenko.kafka.chat.constant.ChatTopic;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import static ru.stoliarenko.kafka.chat.constant.BootstrapServer.MAIN_SERVER;
import static ru.stoliarenko.kafka.chat.constant.KafkaPropertyValue.*;

/**
 * Клиент чата.
 */
@Log4j2
public class Client {

    /**
     * ID клиента, используется для общения с сервером.
     */
    @Nonnull
    private final String id = UUID.randomUUID().toString();

    /**
     * Поставщик сообщений.
     */
    @Nullable
    private KafkaProducer<String, String> producer;

    /**
     * Потребитель сообщений.
     */
    @Nullable
    private KafkaConsumer<String, String> consumer;

    /**
     * Поток, выводящий сообщения потребителя.
     */
    @Nullable
    private MessageReader messageReader;

    /**
     * Чтец строк.
     */
    @Nonnull
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Запустить клиент.
     */
    public void start() {
        init();

        if (Objects.isNull(producer) || Objects.isNull(consumer) || Objects.isNull(messageReader)) {
            log.warn("Null communication objects detected. Shutting down.");
            stop();
        }
        try {
            for (String line = reader.readLine(); !line.equals(ChatCommand.EXIT.getCommand()) ; line = reader.readLine()) {
                final ProducerRecord<String, String> record = new ProducerRecord<>(
                        ChatTopic.SERVER.getId(),
                        id,
                        line
                );
                producer.send(record);
            }
        } catch (IOException e) {
            log.error("Mayday, Mayday. Console is down!");
        } finally {
            stop();
        }

    }

    /**
     * Инициализирует потоебителя и получателя.
     */
    private void init() {
        producer = createProducer();
        consumer = createConsumer();
        messageReader = new MessageReader(consumer);
        messageReader.start();
        log.info("Connected.");
    }

    /**
     * Остановить клиент.
     */
    public void stop() {
        if (Objects.nonNull(producer)) {
            producer.close();
            log.info("Producer is stopped.");
        }
        if (Objects.nonNull(messageReader)) {
            messageReader.interrupt();
            log.info("Consumer is stopped.");
        }
        System.exit(0);
    }

    /**
     * Создаёт поставщика сообщений на сервер.
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
     * Создаёт потребителя для общих и личных сообщений.
     */
    @Nonnull
    private KafkaConsumer<String, String> createConsumer() {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, MAIN_SERVER.getAddress());
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_STRING.getValue());
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DESERIALIZER_STRING.getValue());
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, OFFSET_LATEST.getValue());
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, id);

        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(Arrays.asList(ChatTopic.MAIN_CHAT.getId(), id));
        return consumer;
    }

}
