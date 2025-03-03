package com.api.AdvancedServiceWebFlux.repository;

import com.api.AdvancedServiceWebFlux.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<Boolean> existsByEmail(String email);

    Mono<User> findByEmail(String email);

    void deleteByEmail(String email);
}
