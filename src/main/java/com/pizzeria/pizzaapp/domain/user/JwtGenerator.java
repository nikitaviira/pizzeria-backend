package com.pizzeria.pizzaapp.domain.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class JwtGenerator {
  private static final long DEFAULT_EXPIRATION_IN_DAYS = 30L;

  private final Algorithm algorithm;

  public JwtGenerator(@Value("${token.secret}") String secret)
      throws UnsupportedEncodingException {
    this.algorithm = Algorithm.HMAC512(secret);
  }

  public String generate(User user) {
    Instant now = Instant.now();
    return JWT.create()
        .withClaim("userId", user.id)
        .withClaim("userEmail", user.email)
        .withIssuer("NekitosPizzeria")
        .withIssuedAt(Date.from(now))
        .withExpiresAt(Date.from(now.plus(DEFAULT_EXPIRATION_IN_DAYS, DAYS)))
        .sign(algorithm);
  }
}
