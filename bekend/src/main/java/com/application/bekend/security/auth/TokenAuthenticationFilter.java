package com.application.bekend.security.auth;

import com.application.bekend.model.MyUser;
import com.application.bekend.security.TokenUtils;
import com.application.bekend.service.MyUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    private MyUserService userService;

    public TokenAuthenticationFilter(TokenUtils tokenUtils, MyUserService userService) {
        this.tokenUtils = tokenUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final HttpServletRequest httpRequest = (HttpServletRequest) httpServletRequest;
        final String authToken = tokenUtils.getToken(httpRequest);
        final String email = tokenUtils.getEmailFromToken(authToken);


        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            final UserDetails userDetails = this.userService.findUserByEmail(email);
            if(tokenUtils.validateToken(authToken, userDetails)){
                final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


}
