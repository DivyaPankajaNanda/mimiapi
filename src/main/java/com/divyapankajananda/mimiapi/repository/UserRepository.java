package com.divyapankajananda.mimiapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.divyapankajananda.mimiapi.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Optional<User> findByUserId(UUID currentUserId);
}