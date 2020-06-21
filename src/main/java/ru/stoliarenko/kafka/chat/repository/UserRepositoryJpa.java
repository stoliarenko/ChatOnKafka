package ru.stoliarenko.kafka.chat.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.stoliarenko.kafka.chat.api.repository.UserRepository;
import ru.stoliarenko.kafka.chat.entity.User;
import ru.stoliarenko.kafka.chat.enumeration.Role;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Репозиторий пользователей реализованный с помощью JPA.
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserRepositoryJpa implements UserRepository {

    @Nonnull
    private final EntityManager entityManager;

    @Nullable
    @Override
    public User findOne(@Nonnull String id) {
        final TypedQuery<User> query = entityManager.createQuery(
                "SELECT e FROM User e WHERE e.id = :id", User.class
        );
        query.setParameter("id", id);
        return query.getResultStream().findAny().orElse(null);
    }

    @Nonnull
    @Override
    public List<User> findByCriteria(@Nullable String name, @Nullable Role role) {
        final CriteriaQuery<User> userCriteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);
        final Root<User> root = userCriteriaQuery.from(User.class);
        final List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(name)) {
            final Expression<String> nameExpression = root.get("name");
            final Predicate namePredicate = nameExpression.in(name);
            predicates.add(namePredicate);
        }
        if (Objects.nonNull(role)) {
            final Expression<String> roleExpression = root.get("role");
            final Predicate rolePredicate = roleExpression.in(role);
            predicates.add(rolePredicate);
        }
        CriteriaQuery<User> criteriaQueryWithPredicates = userCriteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<User> query = entityManager.createQuery(criteriaQueryWithPredicates);
        return query.getResultList();
    }

}
