package com.AUW.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AUW.board.domain.QUser;
import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.UserDto;
import com.AUW.board.dto.UserType;
import com.AUW.board.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
/*
  User 엔티티를 다루는 서비스
  
  */
@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final BoardService boardService;
	private final PasswordEncoder encoder;
	
	//pk값으로 user찾기
	public User getOneUser(Long userNo) {
		Optional<User> _user = userRepository.findById(userNo);
		
		User user = _user.orElseThrow(() -> new UsernameNotFoundException("존재하지않는 회원입니다."));//user를 찾지못하면 예외발생
		return user;
	}
	//unipue값으로 user찾기
	public User findOneByUserId(String userId) {
		
		Optional<User> _user = userRepository.findByUserId(userId);
		User user = _user.orElseThrow(() -> new UsernameNotFoundException("존재하지않는 회원입니다."));//user를 찾지못하면 예외발생
		
		return user;
	}
	
	//관리자페이지 전용
	public Page<User> findAllUsers(Pageable pageable){
		
		BooleanBuilder builder = new BooleanBuilder();
		QUser user = QUser.user;
		
		builder.and(user.userType.eq(UserType.USER)); //관리자는 제외
		
		Page<User> list = userRepository.findAll(builder,pageable);

	
		return list;
	}
	public Page<User> findAllUsers(Pageable pageable,String searchKeyword){
		
		BooleanBuilder builder = new BooleanBuilder();
		QUser user = QUser.user;
		
		builder.and(user.nickNm.contains(searchKeyword)); //관리자는 제외
		
		Page<User> list = userRepository.findAll(builder,pageable);

	
		return list;
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
	
	@Transactional
	public User updateUser(UserDto dto,Long userNo) {
		User user = this.getOneUser(userNo);
		if(!dto.getUserNewPw().isEmpty()) {
			user.setUserPw(encoder.encode(dto.getUserNewPw()));
		}
		user.setUserId(dto.getUserId());
		
		user.setUserNm(dto.getUserNm());
		user.setNickNm(dto.getNickNm());
		user.setBirthDay(dto.getBirthDay());
		user.setEmail(dto.getEmail());
		user.setPhone(dto.getPhone());
		
		
		return user;
	}
	
	public void deleteUser(Long userNo) {
		
		User user = this.getOneUser(userNo);
		List<Board> list = boardService.findByUserNo(userNo);
		if(!list.isEmpty()&&list.size()>0) {
			
			for(Board board : list) {
				boardService.deleteBoardForAdmin(board.getBoardNo());
			}
			
		}
		userRepository.delete(user);
	}
	
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
	
		//update관련 unipue db검사s
		public boolean checkUserId(String userId,Long userNo) {
			Optional<User> _user = userRepository.findByUserId(userId);
			User user = _user.orElse(null);
			User org = this.getOneUser(userNo);
			if(user==null) {
			return true;	//user객체를 찾지못하면 중복아이디가 아니므로 true return
			}else if(user==org){ //기존 유저와 동일하다면 true return
				return true;	
			}else {
				return false; //user객체를 찾으면 중복아이디이므로 false return
			}
			
		}
		public boolean chceckUserNick(String nickNm,Long userNo) {
			Optional<User> _user = userRepository.findByNickNm(nickNm);
			User user = _user.orElse(null);
			User org = this.getOneUser(userNo);
			if(user==null) {
			return true;	
			}else if(user==org){
				return true;	
			}else {
				return false; 
			}
		}
		public boolean checkUserEmail(String email,Long userNo) {
			Optional<User> _user = userRepository.findByEmail(email);
			User user = _user.orElse(null);
			User org = this.getOneUser(userNo);
			if(user==null) {
				return true;	
				}else if(user==org){
					return true;	
				}else {
					return false; 
				}
		}public boolean checkUserPhone(String phone,Long userNo) {
			Optional<User> _user = userRepository.findByPhone(phone);
			User user = _user.orElse(null);
			User org = this.getOneUser(userNo);
			if(user==null) {
			return true;	
			}else if(user==org){
				return true;	
			}else {
				return false; 
			}
		}
		
		
		
		//update관련 unipue db검사e
	
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
				.userNo(entity.getUserNo())
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
