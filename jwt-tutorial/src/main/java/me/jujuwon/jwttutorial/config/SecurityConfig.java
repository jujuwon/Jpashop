package me.jujuwon.jwttutorial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import me.jujuwon.jwttutorial.jwt.JwtAccessDeniedHandler;
import me.jujuwon.jwttutorial.jwt.JwtAuthenticationEntryPoint;
import me.jujuwon.jwttutorial.jwt.JwtSecurityConfig;
import me.jujuwon.jwttutorial.jwt.TokenProvider;

@EnableWebSecurity	//기본적인 Web 보안 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true)	//@PreAuthorize 어노테이션을 메소드 단위로 추가하기 위해 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	public SecurityConfig(TokenProvider tokenProvider,
		JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
		JwtAccessDeniedHandler jwtAccessDeniedHandler)
	{
		this.tokenProvider = tokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

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
			.csrf().disable()

			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)

			// h2-console 을 위한 설정 START
			.and()
			.headers()
			.frameOptions()
			.sameOrigin()
			// h2-console 을 위한 설정 STOP

			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // 세션을 사용하지 않기 때문에 세션 설정 STATELESS 로 설정

			.and()
			.authorizeRequests()    //HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다
			.antMatchers("/api/hello").permitAll()    // /api/hello에 대한 요청은 인증없이 접근을 허용하겠다
			.antMatchers("/api/authenticate").permitAll()
			.antMatchers("/api/signup").permitAll()
			.anyRequest().authenticated()    //나머지 요청들은 전부 인증을 받아야한다.

			.and()
			.apply(new JwtSecurityConfig(tokenProvider));

	}
}
