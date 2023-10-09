package com.kkh.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.board.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	User findByEmail(String email);
}
