package com.AUW.board.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.AUW.board.domain.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/*
스프링 시큐리티가 로그인요청이 들어올때 인터셉트해서 로그인처리를 하고 로그인이 완료되면
 UserDetails를 상속받은 PrincipalDetail를 시큐리티 고유의 저장소에 보관해준다. 
*/
@Getter
public class PrincipalDetail implements UserDetails{
	private static final long serialVersionUID = 1L;
	private User user; // 컴포지션
	
	public PrincipalDetail(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> { return "ROLE_" + user.getUserType(); });
		return collectors;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getUserPw();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
