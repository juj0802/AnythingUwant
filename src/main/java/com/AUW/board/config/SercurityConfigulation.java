package com.AUW.board.config;

import jakarta.servlet.DispatcherType;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.AUW.board.config.auth.CustomAccessDeniedHandler;
import com.AUW.board.config.auth.CustomAuthenticationFailureHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // 설정 파일을 빈 등록
@EnableMethodSecurity(prePostEnabled = true)// 스프링 시큐리티의 설정을 해당 파일에서 관리
@EnableWebSecurity
@RequiredArgsConstructor
public class SercurityConfigulation {

    @Bean
    public PasswordEncoder passwordEncoder() {//비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
    
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomAccessDeniedHandler cAccessDeniedHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {//경로에따라 인증이 필요한지 필요없는지 나누는 메서드
        http.csrf().disable().cors().disable()
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                        .requestMatchers("/board_write/**","board_delete/**","board_update/**").authenticated() //인증이 필요한 경로
                        .requestMatchers("/myPage/**").hasAnyRole("USER","ADMIN") // myPage로 오는 경로는 권한체크
                        .requestMatchers("/admin/**").hasRole("ADMIN") // admin으로 오는 경로는 권한체크
                        .anyRequest().permitAll() //그외 나머지 경로는 인증 불필요
                )
                
                .formLogin(login -> login
                        .loginPage("/user/login") // 로그인 페이지
                        .loginProcessingUrl("/login-process") //form의 action 경로랑 동일하게하면 로그인 업무를 가로채서한다
                        .usernameParameter("userId")
                        .passwordParameter("userPw")
                        .defaultSuccessUrl("/user/loginRedirect", true) //성공시
                        .failureHandler(customAuthenticationFailureHandler) //실패시
                        
                )
                .logout(withDefaults());
        http.exceptionHandling().accessDeniedHandler(cAccessDeniedHandler);

        return http.build();
    }
	

}
