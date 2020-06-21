package ru.stoliarenko.kafka.chat.api.service;

import ru.stoliarenko.kafka.chat.entity.User;
import ru.stoliarenko.kafka.chat.enumeration.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Сервис для работы с пользователями.
 */
public interface UserService {

    /**
     * Получить пользователя по его идентификатору.
     *
     * @return пользователя чата или {@code null} если пользователь с заданным идентификатором отсутствует.
     */
    @Nullable
    User getById(@Nullable String id);

    /**
     * Получить пользователей с указанной ролью.
     *
     * @return список пользователей с указанной ролью или пустой список
     * если удовлетворяющие критериям пользователи не найдены или роль не существует.
     */
    @Nonnull
    List<User> getByRole(@Nullable Role role);

}
