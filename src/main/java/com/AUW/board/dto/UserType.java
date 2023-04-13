package com.AUW.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
	USER("사용자", 1), // 일반회원
	ADMIN("관리자", 2); // 관리자
	
	private String type;
	private int seq;
	

}
