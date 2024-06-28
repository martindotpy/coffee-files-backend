package xyz.cupscoffee.backend.service.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.List;

@Component
public class FileSavFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @SuppressWarnings("null") HttpServletRequest request,
            @SuppressWarnings("null") HttpServletResponse response,
            @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("file") == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}
