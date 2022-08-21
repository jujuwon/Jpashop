package com.jujuwon.book.springboot.config.auth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.jujuwon.book.springboot.domain.user.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity	//Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.headers().frameOptions().disable()    //h2-console 화면을 사용하기 위해 옵션 disable
			.and()
				.authorizeRequests()    //URL별 권한 관리 설정, authorizeRequests가 선언되어야 antMatchers 옵션 사용가능
				.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
				.antMatchers("/api/v1/**").hasRole(Role.USER.name())    //권한관리 대상 지정. URL,HTTP 메소드별로 관리 가능.
				//"/" 등 지정된 URL 들은 permitAll() 옵션을 통해 전체 열람 권한 지정
				//"/api/v1/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 지정
				.anyRequest().authenticated()    //설정된 값들 이외 나머지 URL 나타냄. 여기서는 authenticated를 추가해 나머지 URL들은 모두 인증된 사용자들만 허용
			.and()
				.logout()
				.logoutSuccessUrl("/")    //로그아웃 기능에 대한 여러 설정의 진입점. 로그아웃 성공시 / 주소로 이동
			.and()
				.oauth2Login()    //Oauth2 로그인 기능에 대한 여러 설정의 진입점
				.userInfoEndpoint()    //Oauth2 로그인 성공 이후 사용자 정보를 가져올 때 설정을 담당하는 부분
				.userService(customOAuth2UserService);    //소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
				//리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시 가능
	}
}
