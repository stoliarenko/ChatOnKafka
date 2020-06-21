package ru.stoliarenko.kafka.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stoliarenko.kafka.chat.api.repository.UserRepository;
import ru.stoliarenko.kafka.chat.api.service.UserService;
import ru.stoliarenko.kafka.chat.entity.User;
import ru.stoliarenko.kafka.chat.enumeration.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Реализация сервиса для работы с пользователями.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserServiceImpl implements UserService {

    @Nonnull
    private final UserRepository repository;

    @Nullable
    @Override
    public User getById(@Nullable String id) {
        Objects.requireNonNull(id);
        return repository.findOne(id);
    }

    @Nonnull
    @Override
    public List<User> getByRole(@Nullable Role role) {
        Objects.requireNonNull(role);
        return repository.findByCriteria(null, role);
    }

}
