package com.technoelevate.inventory.util;

import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.technoelevate.inventory.exception.TokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

	@Value("${auth.app.jwt-secret: qwerty}")
	private String jwtSecret;

	@Value("${auth.app.jwt-expiration-ms: 86400000}") // 24*60*60*1000
	private int jwtExpirationMs;

	@Value("${auth.app.jwt-refresh-ms: 1296000000}") // 15*24*60*60*1000
	private int jwtRefreshMs;

	@Value("${auth.app.jwt-not-before-ms: 3000}") // 3*1000
	private int jwtNotBefore;

	public String[] generateToken(String username, Collection<GrantedAuthority> authorities) {
		final Key hmacKey = new SecretKeySpec(Base64.getEncoder().encode(jwtSecret.getBytes()),
				SignatureAlgorithm.HS512.getJcaName());
		String accessToken = Jwts.builder()
				.claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuer("Technoelevate Group").setSubject(username).setAudience("you")
				.setExpiration(new Date(Date.from(Instant.now()).getTime() + jwtExpirationMs))
				.setNotBefore(new Date(Date.from(Instant.now()).getTime() + jwtNotBefore))
				.setIssuedAt(Date.from(Instant.now())).setHeaderParam("typ", "JWT").setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS512, hmacKey).compact();

		String refreshToken = Jwts.builder().setSubject(username).setAudience("you")
				.setExpiration(new Date(Date.from(Instant.now()).getTime() + jwtExpirationMs))
				.setNotBefore(new Date(Date.from(Instant.now()).getTime() + jwtNotBefore))
				.setIssuedAt(Date.from(Instant.now())).setHeaderParam("typ", "JWT").setId(UUID.randomUUID().toString())
				.signWith(SignatureAlgorithm.HS512, hmacKey).compact();
		return new String[] { accessToken, refreshToken };

	}

	public Claims getClaimsFromToken(String token) {
		final Key hmacKey = new SecretKeySpec(Base64.getEncoder().encode(jwtSecret.getBytes()),
				SignatureAlgorithm.HS512.getJcaName());
		return Jwts.parser().setSigningKey(hmacKey).parseClaimsJws(token).getBody();
	}

	public String getUsernameFromToken(String token) {
		return getClaimsFromToken(token).getSubject();
	}

	public Date getExpirationDate(String token) {
		return getClaimsFromToken(token).getExpiration();
	}

	public Boolean isTokenExpired(String token) {
		Date expirationDate = getExpirationDate(token);
		log.info("isTokenExpired: " + expirationDate.before(new Date()));
		return expirationDate.before(new Date());
	}

	public boolean validateJwtToken(String authToken) {
		try {
			final Key hmacKey = new SecretKeySpec(Base64.getEncoder().encode(jwtSecret.getBytes()),
					SignatureAlgorithm.HS512.getJcaName());
			Jwts.parser().setSigningKey(hmacKey).parseClaimsJws(authToken);
			if (Boolean.FALSE.equals(isTokenValidated(authToken)))
				throw new TokenExpiredException("JWT token is expired");
			return true;
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}

	public Boolean isTokenValidated(String token) {
		return !isTokenExpired(token);
	}
}