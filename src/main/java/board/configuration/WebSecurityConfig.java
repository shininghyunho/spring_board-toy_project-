package board.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

import board.board.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserService userService; // 유저 정보를 가져오는 서비스
	
	// 인코더를 빈으로 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) {
		// 시큐리티 제외 static 하위
		web.ignoring().antMatchers("/css/**","/js/**","/img/**","/h2-console/**","/error/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.authorizeRequests()
				.antMatchers("/login","/signup","/user","/board").permitAll() // 모두 접근 가능
				.antMatchers("/").hasRole("USER") // USER,ADMIN만 접근 가능
				.antMatchers("/admin").hasRole("ADMIN") // ADMIN만 접근 가능
				.anyRequest().permitAll() // 나머지 권한 필요없음
				//.anyRequest().authenticated() // 나머지 요청도 권한이 있어야 접근 가능
			.and()
				.formLogin()
					.loginPage("/login") // 로그인 페이지
					.defaultSuccessUrl("/board") // 로그인 후 리다이렉트
			.and()
				.logout()
					.logoutSuccessUrl("/login") // 로그아웃 후 리다이렉트
					.invalidateHttpSession(true) // 로그아웃 후 세션 삭제
		//;
		//
			.and()
			.exceptionHandling()
			.accessDeniedPage("/denied.html")
			.accessDeniedHandler(new AccessDeniedHandler(){
				@Override
				public void handle(HttpServletRequest request
						, HttpServletResponse response
						, AccessDeniedException exception) throws IOException, ServletException{
					if (exception instanceof MissingCsrfTokenException) {
						//Some Exception Handling
					} else if (exception instanceof InvalidCsrfTokenException) {
						//Some Exception Handling
					}
				}
			})
			;
		//
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		// 유저 정보를 가져옴
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
}
