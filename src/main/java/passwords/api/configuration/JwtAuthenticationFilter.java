package passwords.api.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import passwords.api.user.entity.User;
import passwords.api.user.service.UserService;

import java.io.IOException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtAuthenticator jwtAuthenticator;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                username = jwtAuthenticator.extractUsername(jwtToken);
            } catch (Exception e) {
                // Handle token extraction/validation errors
                System.out.println("Error extracting username from token: " + e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.loadUserByUsername(username);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                "", // Do not expose the password here
                new ArrayList<>()
            );

            if (jwtAuthenticator.isTokenValid(jwtToken) && !user.getToken().isExpired()) {
                mutableRequest.setUser(user);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(mutableRequest, response);
    }
}
