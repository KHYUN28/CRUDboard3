package com.kkh.board.service;

import com.kkh.board.handler.DuplicateEmailException;
import com.kkh.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.kkh.board.config.RoleType;
import com.kkh.board.model.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public User findUser(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	@Transactional
	public void registerUser(User user) {
		String rawPassword = user.getPassword(); // 1234원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		// 데이터 베이스에 있는 정보를 입력시 실패하도록 예외 추가
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException e) {
			// MySQL에서 발생한 데이터 무결성 예외를 처리합니다.
			throw new DuplicateEmailException("이미 사용 중인 이메일 주소입니다.");
		}
	}
	
	@Transactional
	public void updateUser(User user) {
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});

		// Validate 체크 => oauth 필드에 값이 없으면 수정 가능
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassowrd = user.getPassword();
			String encPassword = encoder.encode(rawPassowrd);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
	}
}
