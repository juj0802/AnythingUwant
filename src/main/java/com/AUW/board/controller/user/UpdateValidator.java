package com.AUW.board.controller.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.AUW.board.domain.User;
import com.AUW.board.dto.UserDto;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class UpdateValidator implements Validator{
	
	
	private final UserService userService;
	private final PasswordEncoder encoder;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return UserDto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserDto user = (UserDto) target;
		User entity = userService.getOneUser(user.getUserNo());
		String userNewPw = user.getUserNewPw();
		String userPwRe = user.getUserPwRe();
		String userPw = user.getUserPw();
		
		String orgPw = entity.getUserPw();
		System.out.println("기존 비밀번호체크");
		System.out.println(userPw);
		System.out.println(orgPw);
		//기존 비밀번호 일치하는지 체크
		if(!encoder.matches(userPw, orgPw)) {
			errors.rejectValue("userPw", "userPwNotSame");
			
		}
		
		//새 비밀번호 검증
		if(!userNewPw.equals(userPwRe)) {
			errors.rejectValue("userPwRe", "userPwReError");
			
		}
		if(userPw.equals(userNewPw)) {
			errors.rejectValue("userNewPw", "userNewPwSame");
		}
		if(!userNewPw.isEmpty()) {
			if(userNewPw.length()>30 || userNewPw.length()<6) {
				errors.rejectValue("userNewPw", "userNewPwSize");
			}
		}

		
		//휴대전화번호 검증
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
		Long userNo = user.getUserNo();
		if(!userService.checkUserPhone(phone,userNo)) {
			errors.rejectValue("phone", "sameUserPhone");
		}
		
		
		if(!userService.checkUserId(user.getUserId(),userNo)) {//중복된 아이디 검증
			errors.rejectValue("userId", "sameUserId");
		}
		
		if(!userService.chceckUserNick(user.getNickNm(),userNo)) {
			errors.rejectValue("nickNm", "sameUserNickNm");
		}
		
		if(!userService.checkUserEmail(user.getEmail(),userNo)) {
			errors.rejectValue("email", "sameUserEmail");
		}
		
		
		
	}

}
