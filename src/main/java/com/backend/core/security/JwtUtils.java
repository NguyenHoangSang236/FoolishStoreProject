package com.backend.core.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${FashionStoreProject.app.jwtSecret}")
    private String jwtSecret;

    @Value("${FashionStoreProject.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${FashionStoreProject.app.jwtCookieName}")
    private String jwtCookie;


    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);

        return (cookie != null) ? cookie.getValue() : null;
    }


    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/systemAuthentication").build();
        return cookie;
    }


    public boolean isJwtTokenValid(String authToken) {
        try {
            Jwts.parser().setSigningKey(key()).parseClaimsJws(authToken);

            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


    public ResponseCookie generateJwtCookie(UserDetailsImpl userDetails) {
        String jwt = generateTokenFromUsername(userDetails.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/systemAuthentication").maxAge(24 * 60 * 60).httpOnly(true).build();

        return cookie;
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }
}
