package com.api.AdvancedServiceWebFlux.security;

import com.api.AdvancedServiceWebFlux.exception.CustomException;
import com.api.AdvancedServiceWebFlux.util.AppConstants;
import com.api.AdvancedServiceWebFlux.util.ServerErrorCodes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtUserService {

    @Value("${jwt_secret}")
    private String secret;

    @Value("${time}")
    private Integer expiryDateTime;

    public String generateToken(String email) {

        return AppConstants.TOKEN_BEARER + Jwts.builder()
                .setSubject(email)
                .setIssuedAt(generateNewDate())
                .setExpiration(getExpirationNewDate())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();

    }

    public Date generateNewDate() {
        return new Date();
    }

    public Date getExpirationNewDate() {
        return new Date(generateNewDate().getTime() + expiryDateTime);
    }

    public Claims getClaimsByToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailByToken(String token) {

        return getClaimsByToken(token).getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            getClaimsByToken(token);
            if (getClaimsByToken(token).getExpiration().before(new Date())) {
                throw new CustomException(ServerErrorCodes.TOKEN_NOT_PROVIDED);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
