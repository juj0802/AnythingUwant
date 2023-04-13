package com.AUW.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AUW.board.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUserId(String userId);
	Optional<User> findByNickNm(String nickNm);
	Optional<User> findByEmail(String email);
	Optional<User> findByPhone(String phone);
}
