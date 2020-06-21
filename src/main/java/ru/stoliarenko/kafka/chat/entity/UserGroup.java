package ru.stoliarenko.kafka.chat.entity;

import lombok.Data;

import javax.annotation.Nonnull;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

/**
 * Группа пользователей чата.
 */
@Data
@Table(name = "chat_user_group")
public class UserGroup {

    /**
     * Идентификатор группы.
     */
    @Id
    @Nonnull
    private final String id = UUID.randomUUID().toString();

    /**
     * Имя группы.
     */
    @Nonnull
    private final String name;

    /**
     * Участники группы.
     */
    @Nonnull
    @ManyToMany(mappedBy = "groups")
    private final Set<User> users;

}
