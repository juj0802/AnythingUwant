package com.AUW.board.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AUW.board.domain.User;
import com.AUW.board.dto.UserDto;
import com.AUW.board.dto.UserType;
import com.AUW.board.repository.UserRepository;

import lombok.RequiredArgsConstructor;
/*
  User 엔티티를 다루는 서비스
  
  */
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder encoder;
	public User findOneByUserId(String userId) {
		
		Optional<User> _user = userRepository.findByUserId(userId);
		User user = _user.orElseThrow(() -> new UsernameNotFoundException("존재하지않는 회원입니다."));//user를 찾지못하면 예외발생
		
		return user;
	}
	
	
	
	
	
	public User insertUser(UserDto dto) {//user정보 db저장
		String pw = encoder.encode(dto.getUserPw());
		User user = User.builder()
				.userId(dto.getUserId())
				.userPw(pw)
				.userNm(dto.getUserNm())
				.nickNm(dto.getNickNm())
				.birthDay(dto.getBirthDay())
				.email(dto.getEmail())
				.phone(dto.getPhone())
				.userType(UserType.USER)
				.build();
		
		user = userRepository.save(user);
		
		return user;
		
	}
	//비밀번호 체크
//	public boolean checkUserPw(String userPw) {
//		Optional<User> _user = userRepository.findByUserId(user);
//		User user = _user.orElse(null);
//		if(user==null) {
//		return true;	//user객체를 찾지못하면 중복아이디가 아니므로 true return
//		}
//		return false; //user객체를 찾으면 중복아이디이므로 false return
//		
//		return false;
//	}
	//db unipue 중복검사s
		public boolean checkUserId(String userId) {
			Optional<User> _user = userRepository.findByUserId(userId);
			User user = _user.orElse(null);
			if(user==null) {
			return true;	//user객체를 찾지못하면 중복아이디가 아니므로 true return
			}
			return false; //user객체를 찾으면 중복아이디이므로 false return
		}
		public boolean chceckUserNick(String nickNm) {
			Optional<User> _user = userRepository.findByNickNm(nickNm);
			User user = _user.orElse(null);
			if(user == null) {
				return true;
			}
			return false;
		}
		public boolean checkUserEmail(String email) {
			Optional<User> _user = userRepository.findByEmail(email);
			User user = _user.orElse(null);
			if(user == null) {
				return true;
			}
			return false;
		}public boolean checkUserPhone(String phone) {
			Optional<User> _user = userRepository.findByPhone(phone);
			User user = _user.orElse(null);
			if(user == null) {
				return true;
			}
			return false;
		}
		//db unipue 중복검사e
	
	
	public static User dtoToEntity(UserDto dto) {
		
		User user = User.builder()
				.userId(dto.getUserId())
				.userPw(dto.getUserPw())
				.userNm(dto.getUserNm())
				.nickNm(dto.getNickNm())
				.birthDay(dto.getBirthDay())
				.email(dto.getEmail())
				.phone(dto.getPhone())
				.userType(UserType.USER)
				.build()
			
				;
		
		
		return user;
	}
	public static UserDto entityToDto(User entity) {
		
		UserDto dto = UserDto.builder()
				.userId(entity.getUserId())
				.userPw(entity.getUserPw())
				.userNm(entity.getUserNm())
				.nickNm(entity.getNickNm())
				.birthDay(entity.getBirthDay())
				.email(entity.getEmail())
				.phone(entity.getPhone())
				
				.build();
		
		return dto;
		
	}
	
	
	
	
}
