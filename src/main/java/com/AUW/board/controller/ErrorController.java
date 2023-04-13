package com.AUW.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

	@RequestMapping("/error/auth")
	public String error(HttpServletRequest request, Model model) {
		
		System.out.println("테스트 에러페이지 이동");
		
		model.addAttribute("msg", request.getAttribute("msg"));
		model.addAttribute("nextPage", request.getAttribute("nextPage"));
		
		return "common/error";
	}
	
}
