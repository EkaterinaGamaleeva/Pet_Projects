package com.api.AdvancedServiceWebFlux.security;

import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.repository.UserRepository;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsService  implements ReactiveUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new CustomException(ServerErrorCodes.USER_NOT_FOUND)))
                .map(CustomUserDetails::new);
    }
}
