package ru.stoliarenko.kafka.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Группа пользователей чата.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chat_user_group")
public class UserGroup extends AbstractEntity {

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
