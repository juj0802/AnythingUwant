package com.AUW.board.controller.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.domain.User;
import com.AUW.board.dto.UserDto;
import com.AUW.board.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/myPage")
@RequiredArgsConstructor
@Controller
public class MypageController {

	private final UserService userService;
	private final UpdateValidator updateValidator;
	@GetMapping("/update")
	public String myPageUpdate(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
		
		UserDto userDto = userService.entityToDto(principalDetail.getUser());
		
		model.addAttribute("userDto", userDto);
		model.addAttribute("addScript", new String[] {"user/mypage"});
		return "user/my_page";
	}
	
	
	@PostMapping("/update_ps/{userNo}")
	public String updateProcess(@PathVariable Long userNo,@Valid UserDto userDto,Errors errors,Model model) {
		
	
		updateValidator.validate(userDto, errors);
		if(errors.hasErrors()) {
			model.addAttribute("userDto", userDto);
			model.addAttribute("addScript", new String[] {"user/mypage"});
	
			return "user/my_page";
		}
		
		User user = userService.updateUser(userDto,userNo);
		
		model.addAttribute("scripts", " alert('수정되었습니다.'); location.replace('/main');");
		return "common/excution";
	}
	
	@GetMapping("/delete/{userNo}")
	public String deleteUser(@PathVariable Long userNo, Model model) {
		
		userService.deleteUser(userNo);
		SecurityContextHolder.clearContext();
		
		model.addAttribute("scripts", " alert('삭제되었습니다.'); location.replace('/user/login');");
		return "common/excution";
		
	}
	
	
}
