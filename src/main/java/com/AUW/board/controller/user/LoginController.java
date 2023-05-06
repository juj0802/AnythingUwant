package com.AUW.board.controller.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error,
			@RequestParam(value="exception", required=false) String exception
			, Model model) {
		
	
		
		model.addAttribute("addScript", new String[] {"user/login"});
		model.addAttribute("addCss", new String[] {"bootstrap/signin"});
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
	
		return "user/login";
	}
	
	@PostMapping("/login")
	public String loginPs() {
		
		return "login";
	}
	
	@RequestMapping("/loginRedirect")
	public String loginRedirect(HttpServletRequest request, @AuthenticationPrincipal PrincipalDetail principal) {

		if(request.isUserInRole("ADMIN")) {
			return "redirect:/admin/main";
		}
		return "redirect:/main";
	}
}
