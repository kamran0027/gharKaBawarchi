package com.Kamran.gharKaBawarchi.SecurityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Kamran.gharKaBawarchi.Service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
                String token=null;
                // 1) read the cookies
                Cookie [] cookies=request.getCookies();
                if(cookies!=null){
                    for(Cookie c: cookies){
                        if("JWT".equals(c.getName())){
                            token=c.getValue();
                            break;
                        }
                    }
                }

                // 2.) fall back to header

                if(token== null){
                    final String authHeader=request.getHeader("Authorization");
                    if (authHeader!=null && authHeader.startsWith("Bearer ")) {
                        token=authHeader.substring(7);
                    }
                }
                if(token==null){
                    filterChain.doFilter(request, response);
                    return;
                }

                try{
                    String username=jwtService.extractUserName(token);

                    if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

                        if(!jwtService.isTokenExpired(token)){
                            var authToken=new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    }
                }catch(io.jsonwebtoken.JwtException ex){
                    //invalid token:  clear context and remove cookies

                    SecurityContextHolder.clearContext();
                    Cookie cookie=new Cookie("JWT", "");
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                filterChain.doFilter(request, response);
            }


}
