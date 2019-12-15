package com.xiongyc.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author YouCai.Xiong
 * @Date 2019年12月15日 - 下午4:05:26
 * @Info 初始版本 Security 安全验证
 * @Version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
		.withUser("root").password(new BCryptPasswordEncoder().encode("xiongyc")).roles("USER").and()
		.withUser("admin").password(new BCryptPasswordEncoder().encode("xiongyc")).roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests().anyRequest().fullyAuthenticated();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		/**
		 * 放行路径
		 */
		web.ignoring().antMatchers("/actuator/hystrix.stream", "/turbine.stream");
	}

}