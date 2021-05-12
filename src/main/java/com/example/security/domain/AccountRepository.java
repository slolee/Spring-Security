package com.example.security.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.OptionalInt;

public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByUserId(String userId);
    Optional<Account> findBySocialID(long socialId);
}
