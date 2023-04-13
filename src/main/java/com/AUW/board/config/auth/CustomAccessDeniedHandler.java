package com.AUW.board.config.auth;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// 허용하지 않은 권한 접근으로 인한 에러 상태일 때
		// 에러페이지로 이동시킴
		if(accessDeniedException instanceof AccessDeniedException) {

			
				request.setAttribute("msg", "유효하지 않은 접근입니다.");
				request.setAttribute("nextPage", "/main");
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				SecurityContextHolder.clearContext();
			
		} 
		request.getRequestDispatcher("/error/auth").forward(request, response);
		
	}

}
