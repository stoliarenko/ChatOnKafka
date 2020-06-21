package ru.stoliarenko.kafka.chat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.stoliarenko.kafka.chat.api.repository.UserGroupRepository;
import ru.stoliarenko.kafka.chat.entity.UserGroup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Репозиторий групп пользователей чата реализованный с помощью JPA.
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserGroupRepositoryJpa implements UserGroupRepository {

    @Nonnull
    private final EntityManager entityManager;

    @Nullable
    @Override
    public UserGroup findOne(@Nonnull String id) {
        TypedQuery<UserGroup> query = entityManager.createQuery(
                "SELECT g FROM UserGroup g WHERE g.id = :id", UserGroup.class
        );
        query.setParameter("id", id);
        return query.getResultStream().findAny().orElse(null);
    }

}
