package ru.stoliarenko.kafka.chat.entity;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Настройки разработчика.
 */
@Entity
@Table(name = "chat_settings_developer")
public class DeveloperSettings extends Settings {

    /**
     * Имя разработчика.
     */
    @Nonnull
    private String firstName;

    /**
     * Фамилия разработчика.
     */
    @Nonnull
    private String lastName;

    /**
     * Отчество разработчика.
     */
    @Nonnull
    private String middleName;

    /**
     * Электронная почта разработчика.
     */
    @Nonnull
    private String email;

}
