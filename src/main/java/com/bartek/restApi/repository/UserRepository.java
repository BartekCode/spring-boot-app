package com.bartek.restApi.repository;

import com.bartek.restApi.model.Role;
import com.bartek.restApi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();
    Optional<User>findByEmail(String email);
    Set<User>findAllByRole(Role role);
    User save(User user);
    Optional<User>findById(Long id);
}
