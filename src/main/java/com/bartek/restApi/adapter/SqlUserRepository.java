package com.bartek.restApi.adapter;

import com.bartek.restApi.model.Role;
import com.bartek.restApi.model.User;
import com.bartek.restApi.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SqlUserRepository extends UserRepository, JpaRepository <User, Long> {

    @Override
    List<User> findAll();

    @Override
    Optional<User> findByEmail(String email);

    @Override
    Set<User> findAllByRole(Role role);

    @Override
    User save(User user);

    @Override
    Optional<User> findById(Long id);
}
