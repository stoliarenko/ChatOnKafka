package ru.stoliarenko.kafka.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stoliarenko.kafka.chat.enumeration.Role;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.Set;

/**
 * Пользователь чата.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat_user")
public class User extends AbstractEntity {

    /**
     * Имя пользователя.
     */
    @Nonnull
    private String name;

    /**
     * Роль пользователя.
     */
    @Nonnull
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Группы в которых состоит пользователь.
     */
    @Nonnull
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "chat_user_and_group",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") }
    )
    private Set<UserGroup> groups;
    
}
