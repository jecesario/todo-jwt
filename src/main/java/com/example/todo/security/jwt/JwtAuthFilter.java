package com.example.todo.security.jwt;

import com.example.todo.domain.services.impl.AccountServiceImpl;
import com.example.todo.exceptions.InvalidJwtException;
import com.example.todo.exceptions.Issue;
import com.example.todo.exceptions.IssueEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccountServiceImpl accountService;

    public JwtAuthFilter(JwtService jwtService, AccountServiceImpl accountService) {
        this.jwtService = jwtService;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

            var authorization = request.getHeader("authorization");

            if(!ObjectUtils.isEmpty(authorization) && authorization.startsWith("Bearer")) {

                var token = authorization.split(" ")[1];

                if (jwtService.isValidToken(token)) {

                    var loggedAccount = jwtService.getLoggedAccount(token);
                    var userDetails = accountService.loadUserByUsername(loggedAccount);
                    var user = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(user);
                }
            }


        filterChain.doFilter(request, response);
    }

    private String getAuthorization(HttpServletRequest request) {
        var headers = request.getHeaderNames();
        List<String> headerList = new ArrayList<>();

        while (headers.hasMoreElements()) {
            headerList.add(headers.nextElement());
        }

        if(headerList.contains("authorization")) {

            return request.getHeader("authorization");
        }
        throw new InvalidJwtException(
                new Issue(IssueEnum.HEADER_REQUIRED_ERROR, List.of("The 'Authorization' field is required"))
        );
    }
}
