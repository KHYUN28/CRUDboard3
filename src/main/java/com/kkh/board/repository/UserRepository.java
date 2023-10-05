package com.kkh.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.board.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	// SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);

	User findByEmail(String email);
}


// JPA Naming 쿼리
// SELECT * FROM user WHERE username = ? AND password = ?;
//User findByUsernameAndPassword(String username, String password);

//	@Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
//	User login(String username, String password);
