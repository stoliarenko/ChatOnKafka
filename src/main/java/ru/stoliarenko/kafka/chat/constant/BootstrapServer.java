package ru.stoliarenko.kafka.chat.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

/**
 * Адреса серверов apache-kafka.
 */
@Getter
@RequiredArgsConstructor
public enum BootstrapServer {

    /**
     * Основной сервер.
     */
    @Nonnull
    MAIN_SERVER("localhost:9092");

    /**
     * Адрес сервера.
     */
    @Nonnull
    private final String address;

}
