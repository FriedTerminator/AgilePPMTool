package io.nikitacherepanov.ppmtool.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.nikitacherepanov.ppmtool.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static io.nikitacherepanov.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static io.nikitacherepanov.ppmtool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    private final Key key = Keys.hmacShaKeyFor(SecurityConstants.SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    //Validate the token

    //Get user Id from token
}
