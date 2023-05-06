package com.AUW.board.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("com.AUW.board.controller")
public class ExceptionController {

	
	@ExceptionHandler(Exception.class)
	public String handleRuntimeException(RuntimeException e, Model model) {
		
		model.addAttribute("message", e.getMessage());
		e.printStackTrace();
		
		return "common/error"; 
	}
}
