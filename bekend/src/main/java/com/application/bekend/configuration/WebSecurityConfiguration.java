package com.application.bekend.configuration;

import com.application.bekend.security.TokenUtils;
import com.application.bekend.security.auth.TokenAuthenticationFilter;
import com.application.bekend.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private MyUserService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/**/*.html",
                "/**/*.css", "/**/*.js");

        web.ignoring().antMatchers(HttpMethod.POST, "/api/houseReservations/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/api/houseReservations/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/identity/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/user/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/house/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/api/house/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/api/house/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/house/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/boat/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/api/boat/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/address/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/image/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/image/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/boatReservations/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/boatReservations/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/api/boatReservations/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/houseReservations/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/room/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/adventure/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/room/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/api/room/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/additionalServices/**");
        web.ignoring().antMatchers(HttpMethod.DELETE, "/api/additionalServices/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/additionalServices/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/api/fishingAdventure/**");

        web.ignoring().antMatchers(HttpMethod.GET, "/api/user/**");
        web.ignoring().antMatchers(HttpMethod.PUT, "/api/user/**");
        web.ignoring().antMatchers(HttpMethod.POST, "/api/user/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
//                .antMatchers("api/user/**").hasRole("USER")
                .anyRequest()
                .authenticated().and()
                .cors().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService),
                        BasicAuthenticationFilter.class)
                .httpBasic();
        http.csrf().disable();
    }

}
