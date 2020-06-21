package ru.stoliarenko.kafka.chat.entity;

import ru.stoliarenko.kafka.chat.enumeration.OperatingSystem;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Настройки приложения.
 */
@Entity
@Table(name = "chat_settings_application")
public class ApplicationSettings extends Settings {

    /**
     * Используемая операционная система.
     */
    @Nonnull
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;

    /**
     * Объём памяти системы.
     */
    private long memory;

}
