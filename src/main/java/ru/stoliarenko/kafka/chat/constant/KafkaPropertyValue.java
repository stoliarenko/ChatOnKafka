package ru.stoliarenko.kafka.chat.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import javax.annotation.Nonnull;

/**
 * Значения свойств поставщиков и потребителей apache-kafka.
 */
@Getter
@RequiredArgsConstructor
public enum KafkaPropertyValue {

    /**
     * Сериализатор текстовых данных.
     */
    @Nonnull
    SERIALIZER_STRING(StringSerializer.class.getName()),

    /**
     * Десериализатор текстовых данных.
     */
    @Nonnull
    DESERIALIZER_STRING(StringDeserializer.class.getName()),

    /**
     * Чтение сообщений с самых ранних при создании группы.
     */
    @Nonnull
    OFFSET_EARLIEST("earliest"),

    /**
     * Чтение только сообщений появившихся после подключения потребиткля.
     */
    @Nonnull
    OFFSET_LATEST("latest"),

    /**
     * Идентификатор группы для сервера.
     */
    @Nonnull
    GROUP_ID_SERVER("server-group");

    /**
     * Значение параметра.
     */
    @Nonnull
    private final String value;

}
