package ru.stoliarenko.kafka.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.stoliarenko.kafka.chat.api.repository.UserGroupRepository;
import ru.stoliarenko.kafka.chat.api.service.UserGroupService;
import ru.stoliarenko.kafka.chat.entity.UserGroup;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Реализация сервиса для работы с группами пользователяей.
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserGroupServiceImpl implements UserGroupService {

    @Nonnull
    private final UserGroupRepository repository;

    @Nullable
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UserGroup getById(@Nullable String id) {
        Objects.requireNonNull(id);
        return repository.findOne(id);
    }

}
