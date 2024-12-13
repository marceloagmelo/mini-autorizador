package com.br.vr.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.vr.miniautorizador.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
