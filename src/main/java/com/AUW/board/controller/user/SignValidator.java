package com.AUW.board.controller.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.AUW.board.dto.UserDto;
import com.AUW.board.service.UserService;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SignValidator implements Validator{
	
	private final UserService userService;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UserDto user = (UserDto) target;
		// 1. 비밀번호 확인 비교
		String userPw = user.getUserPw();
		String userPwRe = user.getUserPwRe();
	
		if(!userPw.equals(userPwRe)) {
			errors.rejectValue("userPwRe","userPwReError");
		}
		
		//2. 휴대전화번호 검증
		String phone = user.getPhone();
		
		if(phone != null && !phone.isBlank()) {//휴대폰 번호 값이 존재할경우 로직 실행
			phone = phone.replaceAll("\\D", "");//문자제거
			System.out.println(phone);
			String pattern = "01[06]\\d{3,4}\\d{4}";
			
			
			if(!(phone.matches(pattern))){
			
				errors.rejectValue("phone","phoneError");
			}
		}
		//db값이 unipue인 항목들은 중복검사
		if(!userService.checkUserPhone(phone)) {
			errors.rejectValue("phone", "sameUserPhone");
		}
		
		
		if(!userService.checkUserId(user.getUserId())) {//중복된 아이디 검증
			errors.rejectValue("userId", "sameUserId");
		}
		
		if(!userService.chceckUserNick(user.getNickNm())) {
			errors.rejectValue("nickNm", "sameUserNickNm");
		}
		
		if(!userService.checkUserEmail(user.getEmail())) {
			errors.rejectValue("email", "sameUserEmail");
		}
		
	}



}
