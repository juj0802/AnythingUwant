package com.AUW.board.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
	
	@NotBlank
	@Size(min=6,max=20)
	private String userId;
	@NotBlank
	@Size(min=6,max=30)
	private String userPw;
	@NotBlank
	private String userPwRe;
	
	private String userNewPw;
	@NotBlank
	@Size(max=30)
	private String userNm;
	@NotBlank
	@Size(min=2,max=30)
	private String nickNm;
	@NotBlank
	@Size(min=6,max=6)
	private String birthDay; 
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String phone;

	private boolean check1;

	private boolean check2;

	private boolean checkAll;
	
	
	
}
