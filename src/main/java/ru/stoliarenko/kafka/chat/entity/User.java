package ru.stoliarenko.kafka.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

/**
 * Пользователь чата.
 */
@Getter
@RequiredArgsConstructor
public class User {

    /**
     * Идентификатор пользователя.
     */
    @Nonnull
    private final String id;

    /**
     * Имя пользователя.
     */
    @Nonnull
    private final String name;
    
}
