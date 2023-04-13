package com.AUW.board.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.AUW.board.config.auth.PrincipalDetail;

@Controller
public class MainController {

	@GetMapping("/main")//@AuthenticationPrincipal PrincipalDetail principalDetail,
	public String mainPage(Model model) {
		System.out.println("123");
		return "main";
	}
}
