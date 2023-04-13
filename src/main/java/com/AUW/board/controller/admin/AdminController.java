package com.AUW.board.controller.admin;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AUW.board.config.auth.PrincipalDetail;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/main")
	public String adminMain(@AuthenticationPrincipal PrincipalDetail principal) {
		
		return "admin/main";
	}
	
}
