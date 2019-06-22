package org.xmlws.gatewayservice.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtility tokenUtility;
    private WebClient.Builder webClientBuilder;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = tokenUtility.getToken(request);
        if (token != null) {
            UserState userState = webClientBuilder.build()
                    .get()
                    .uri("http://authentication-service/authentication/" + token)
                    .retrieve()
                    .bodyToMono(UserState.class)
                    .block();
            if (userState == null) {
                SecurityContextHolder.clearContext();
            } else {
                UsernamePasswordAuthenticationToken auth
                        = new UsernamePasswordAuthenticationToken(userState.getUsername(),
                        null,
                        userState.getAuthorities()
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList()));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }
}