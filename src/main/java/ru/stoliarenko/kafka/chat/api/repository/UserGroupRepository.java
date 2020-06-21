package ru.stoliarenko.kafka.chat.api.repository;

import ru.stoliarenko.kafka.chat.entity.UserGroup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Репозиторий групп пользователей.
 */
public interface UserGroupRepository {

    /**
     * Поиск группы пользователей по идентификатору группы.
     *
     * @return группу пользователей чата или {@code null} если группа с заданным идентификатором отсутствует.
     */
    @Nullable
    UserGroup findOne(@Nonnull String id);

}
