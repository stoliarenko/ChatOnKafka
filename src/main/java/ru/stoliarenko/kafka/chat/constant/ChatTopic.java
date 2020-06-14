package ru.stoliarenko.kafka.chat.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

/**
 * Группы apache-kafka для поставки и чтения сообщений.
 */
@Getter
@RequiredArgsConstructor
public enum ChatTopic {

    /**
     * Группа для сообщений пользователям.
     */
    @Nonnull
    MAIN_CHAT("main-chat"),

    /**
     * Группа для сообщений серверу.
     */
    @Nonnull
    SERVER("server-messages");

    /**
     * Идентификатор группы.
     */
    @Nonnull
    private final String id;

}
