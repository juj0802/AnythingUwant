package com.AUW.board.controller.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AUW.board.domain.User;
import com.AUW.board.dto.UserDto;
import com.AUW.board.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class SignUpController {

	private final UserService userService;
	private final SignValidator signValidator;
	@GetMapping("/sign_up")
	public String signup(Model model) {
		
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		model.addAttribute("addScript", new String[] {"user/signUp"});
	
		return "user/sign_up";
	}
	@PostMapping("/sign_up_Ps")//회원가입 로직 수행
	public String signUpPs(@Valid UserDto userDto,Errors errors,Model model) {
	
		
		signValidator.validate(userDto, errors);
		
		if(errors.hasErrors()) {
			//에러가있을경우 데이터를 갖고 다시 회원가입 화면으로이동
			model.addAttribute("userDto", userDto);
			model.addAttribute("addScript", new String[] {"user/signUp"});
			
			
			return "user/sign_up";
		}
		User user = userService.insertUser(userDto);

		
		
		//회원가입 완료될시 메인페이지로 이동
		return "redirect:login";
	}
	
}
