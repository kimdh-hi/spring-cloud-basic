package com.example.userservice.security;

import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 다른 Bean보다 앞서서 빈으로 등록
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;

    // Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/**")
                .hasIpAddress("172.30.1.50")
                .and()
                .addFilter(getAuthenticationFilter());

        http.headers().frameOptions().disable(); // To solve h2-console error
    }

    // Authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
          1. email에 해당하는 password를 불러오는 기능 (select pw from users where email=?)
             => UserService 로직으로 수행
          2. DB의 암호화된 pw와 입력받은 pw를 비교
             => 암호화 시 사용된 암호 모듈로 수행
         */
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter =
                new AuthenticationFilter(authenticationManager(), userService, env);

        return authenticationFilter;
    }
}
