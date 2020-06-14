package ru.stoliarenko.kafka.chat;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@Log4j2
public class ProducerTestApp {

    private static final String serverAddress = "localhost:9092";
    private static final String topic = "third-topic";
    private static final String acks = "all";

    public static void main(String[] args) {
        final Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serverAddress);
        kafkaProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProperties.put(ProducerConfig.ACKS_CONFIG, acks);

        final KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProperties);

        final String key = "Me";
        final String value = "HELLO WORLD!";
        final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);

        producer.send(record);
        producer.close();
    }
}
