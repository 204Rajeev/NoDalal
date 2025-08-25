package com.example.NoDalal.security;

import com.example.NoDalal.entity.Auth;
import com.example.NoDalal.repository.AuthRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthRepository authRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Skip login and register endpoints
          String path = request.getRequestURI();
        return path.equals("/user/login")
                || path.equals("/user/check-username")
                || path.equals("/user/check-email")
                || path.equals("/user/save");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        String sessionId = header.substring(7); // Remove "Bearer "

        Optional<Auth> authOpt = authRepository.findById(sessionId);

        if (authOpt.isEmpty() || (authOpt.get().getExpiresAt() != null
                && authOpt.get().getExpiresAt().before(new Timestamp(System.currentTimeMillis())))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired session");
            return;
        }

        // Optionally: set userId in request attribute for controllers
        request.setAttribute("userId", authOpt.get().getUserId());

        filterChain.doFilter(request, response);
    }
}
