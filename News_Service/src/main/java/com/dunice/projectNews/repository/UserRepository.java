package com.dunice.projectNews.repository;

import com.dunice.projectNews.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    Boolean existsByEmail(String email);
}
