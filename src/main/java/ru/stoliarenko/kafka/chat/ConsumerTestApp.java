package ru.stoliarenko.kafka.chat;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Log4j2
public class ConsumerTestApp {

    private static final String serverAddress = "localhost:9092";
    private static final String topic = "third-topic";
    private static final String group = "group-zero";
    private static final String offset = "earliest";

    public static void main(String[] args) {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, group);

        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(Collections.singleton(topic));
        log.info("CLIENT STARTED");
        while (true) {
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.key() + ": " + record.value());
            }
        }
    }

}
