package me.jujuwon.jwttutorial.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity	//기본적인 Web 보안 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
			.ignoring()
			.antMatchers(
				"/h2-console/**"
				, "/favicon.ico"
			);	// h2-console 하위 모든 요청과 파비콘은 모두 무시
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()	//HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다
			.antMatchers("/api/hello").permitAll()	// /api/hello에 대한 요청은 인증없이 접근을 허용하겠다
			.anyRequest().authenticated();	//나머지 요청들은 전부 인증을 받아야한다.
	}
}
