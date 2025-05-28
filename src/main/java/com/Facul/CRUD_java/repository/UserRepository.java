package com.Facul.CRUD_java.repository;

import com.Facul.CRUD_java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndCodigoRecuperacao(String email, String codigo);
}
