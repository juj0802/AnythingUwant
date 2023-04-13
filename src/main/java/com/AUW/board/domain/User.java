package com.AUW.board.domain;


import com.AUW.board.dto.UserDto;
import com.AUW.board.dto.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends BaseEntity{
	/*
	 * unique = userId, nickNm, email, phone
	 * 
	 * */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo; //Pk 
	@Column(unique=true, length=20, nullable=false)
	private String userId; // 사용자 아이디
	@Column(length=70, nullable=false)
	private String userPw;// 사용자 비번
	@Column(length=30, nullable=false)
	private String userNm; // 사용자이름
	@Column(unique = true,length = 30, nullable = false)
	private String nickNm;
	@Column(length=6, nullable=false)
	private String birthDay; // 생일
	@Column(unique = true,length = 60, nullable = false)
	private String email;
	@Column(unique = true, length = 12 , nullable = true)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private UserType userType;
}
