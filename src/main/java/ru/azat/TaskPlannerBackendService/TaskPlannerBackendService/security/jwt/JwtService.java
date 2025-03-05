package ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.dto.JwtAuthDTO;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.Role;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.entity.User;
import ru.azat.TaskPlannerBackendService.TaskPlannerBackendService.repository.UserRepository;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt_secret}")
    private String jwtSecret;

    private final UserRepository userRepository;

    private String generateJwtToken(User user) {
        Date expiration = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getName).toList())
                .expiration(expiration)
                .signWith(getSignKey())
                .compact();
    }

    public JwtAuthDTO generateAuthToken(User user) {
        String token = generateJwtToken(user);
        String refreshToken = generateRefreshToken(user.getEmail());

        return JwtAuthDTO.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public String generateRefreshToken(String email) {
        Date expiryDate = Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(expiryDate)
                .signWith(getSignKey())
                .compact();
    }

    public JwtAuthDTO refreshBaseToken(String refreshToken) {
        if (!validateJwtToken(refreshToken)) {
            throw new JwtException("Недействительный refresh-токен");
        }

        String email = getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return new JwtAuthDTO(generateJwtToken(user), refreshToken);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Произошла ошибка: Expired JwtException", expEx);
        } catch (UnsupportedJwtException unEx) {
            log.error("Произошла ошибка: Unsupported JwtException", unEx);
        } catch (MalformedJwtException malEx) {
            log.error("Произошла ошибка: Malformed JwtException", malEx);
        } catch (SecurityException secEx) {
            log.error("Произошла ошибка: Security Exception", secEx);
        } catch (Exception ex) {
            log.error("Произошла ошибка: Invalid token", ex);
        }
        return false;
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        return getAllClaimsFromToken(token).get("roles", List.class);
    }

    public Long getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("userId", Long.class);
    }


    private Claims getAllClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expEx) {
            log.error("Произошла ошибка: Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Произошла ошибка: Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Произошла ошибка: Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Произошла ошибка: Invalid signature", sEx);
        } catch (Exception e) {
            log.error("Произошла ошибка: invalid token", e);
        }
        return claims;
    }
}
