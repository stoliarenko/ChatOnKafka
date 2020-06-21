package ru.stoliarenko.kafka.chat.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * Общий предок сущностей.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * Идентификатор сущности.
     */
    @Id
    @Nonnull
    protected String id = UUID.randomUUID().toString();

}
