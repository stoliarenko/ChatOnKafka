package ru.stoliarenko.kafka.chat.api.repository;

import ru.stoliarenko.kafka.chat.entity.User;
import ru.stoliarenko.kafka.chat.enumeration.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Репозиторий пользователей чата.
 */
public interface UserRepository {

    /**
     * Поиск пользователя по идентификатору.
     *
     * @return пользователя чата или {@code null} если пользователь с заданным идентификатором отсутствует.
     */
    @Nullable
    User findOne(@Nonnull String id);

    /**
     * Поиск пользователей по заданным критериям.
     *
     * @param name - имя пользователя.
     * @param role - роль пользователя.
     * @return список пользователей удовлетворяющих критериям или пустой список
     * если удовлетворяющие критериям пользователи не найдены.
     */
    @Nonnull
    List<User> findByCriteria(@Nullable String name, @Nullable Role role);

}
