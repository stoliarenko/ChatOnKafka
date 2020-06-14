package ru.stoliarenko.kafka.chat.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

/**
 * Команды для взаимодействия с сервером чата.
 */
@Getter
@RequiredArgsConstructor
public enum ChatCommand {

    /**
     * Присоединиться к серверу.
     */
    @Nonnull
    LOGIN("/connect "),

    /**
     * Отключиться от сервера.
     */
    @Nonnull
    LOGOUT("/disconnect "),

    /**
     * Личное сообщение.
     */
    @Nonnull
    WHISPER("/w "),

    /**
     * Выйти из чата.
     */
    @Nonnull
    EXIT("/exit");

    /**
     * Текст команды.
     */
    @Nonnull
    private final String command;

}
