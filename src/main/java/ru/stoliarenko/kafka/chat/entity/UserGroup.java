package ru.stoliarenko.kafka.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

/**
 * Группа пользователей чата.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chat_user_group")
public class UserGroup {

    /**
     * Идентификатор группы.
     */
    @Id
    @Nonnull
    private String id = UUID.randomUUID().toString();

    /**
     * Имя группы.
     */
    @Nonnull
    private String name;

    /**
     * Участники группы.
     */
    @Nonnull
    @ManyToMany(mappedBy = "groups")
    private Set<User> users;

}
