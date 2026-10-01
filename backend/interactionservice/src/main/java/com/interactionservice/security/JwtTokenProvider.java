package com.interactionservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

        @Value("${jwt.secret}")
        private String jwtSecret;

        private Key getSigningKey() {
            return Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }

        public String extractRole(String token) {
            Claims claims = getClaims(token);
            return claims.get("role", String.class);
        }

        public Long extractUserId(String token){
            return getClaims(token).get("userId",Long.class);
        }

        public Long extractCollegeId(String token){
            return getClaims(token).get("collegeId",Long.class);
        }

        public Claims getClaims(String token){

            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        public String extractSubject(String token) {
            return getClaims(token).getSubject();
        }

        public Date extractExpiration(String token) {
            return getClaims(token).getExpiration();
        }

        public boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        public boolean isTokenValid(String token){

            try {
                Jwts.parserBuilder()
                        .setSigningKey(getSigningKey())
                        .build()
                        .parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {
                log.error("JWT token expired");
            } catch (MalformedJwtException e) {
                log.error("Malformed JWT token");
            } catch (SignatureException e) {
                log.error("Invalid JWT signature");
            } catch (JwtException e) {
                log.error("Invalid JWT token");
            } catch (IllegalArgumentException e) {
                log.error("JWT token is empty");
            }
            return false;
        }
}
