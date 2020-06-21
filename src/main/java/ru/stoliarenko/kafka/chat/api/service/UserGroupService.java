package ru.stoliarenko.kafka.chat.api.service;

import ru.stoliarenko.kafka.chat.entity.UserGroup;

import javax.annotation.Nullable;

/**
 * Сервис для работы с группами пользователей.
 */
public interface UserGroupService {

    /**
     * Получить группу пользователей по её идентификатору.
     *
     * @return гркппу пользователей чата или {@code null} если группа с заданным идентификатором отсутствует.
     */
    @Nullable
    UserGroup getById(@Nullable String id);

}
