package com.AUW.board.controller.user;

import org.springframework.stereotype.Component;

import com.AUW.board.repository.UserRepository;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginValidator {

	private final UserService userService;
	
	
	public boolean checkIdAndPw(String userId, String userPw) {
		
	
		
		return true;
	}
	
}
