package com.AUW.board.config.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.AUW.board.domain.User;
import com.AUW.board.repository.UserRepository;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService{
	
	private final UserService userService; 
	
	/*
	  
	 로그인 요청을 가로채는 메서드  
	 DB에 접근하여 데이터를 확인한후 user객체를 반환한다. 
	 비밀번호는 스프링이 알아서 확인해주므로 id만 매개변수로 넘겨준다.
	  */ 
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		User user = userService.findOneByUserId(userId);
		
		return new PrincipalDetail(user);
	}

}
