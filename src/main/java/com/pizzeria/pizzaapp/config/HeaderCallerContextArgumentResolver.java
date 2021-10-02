package com.pizzeria.pizzaapp.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pizzeria.pizzaapp.config.exceptions.UnauthorizedException;
import com.pizzeria.pizzaapp.dto.JwtTokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.UnsupportedEncodingException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class HeaderCallerContextArgumentResolver implements HandlerMethodArgumentResolver {
  @Value("${token.secret}")
  private String secret;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().equals(JwtTokenDto.class);
  }

  @Override
  @Nullable
  public Object resolveArgument(MethodParameter parameter,
                                @Nullable ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                @Nullable WebDataBinderFactory binderFactory) {
    String token = extractToken(webRequest);
    DecodedJWT decodedJwt;
    try {
      decodedJwt = validateJwt(token);
    } catch (Exception e) {
      throw new UnauthorizedException("Authorization did not succeed");
    }
    JwtTokenDto jwtTokenDto = new JwtTokenDto();
    jwtTokenDto.userId = decodedJwt.getClaim("userId").asLong();
    jwtTokenDto.expiresAt = decodedJwt.getClaim("exp").asLong();
    jwtTokenDto.email = decodedJwt.getClaim("userEmail").asString();
    jwtTokenDto.hash = decodedJwt.getSignature();
    return jwtTokenDto;
  }

  private DecodedJWT validateJwt(String token) throws UnsupportedEncodingException {
    return JWT.require(HMAC512(secret))
      .withIssuer("NekitosPizzeria")
      .build()
      .verify(token);
  }

  private String extractToken(NativeWebRequest request) {
    String token = extractHeaderToken(request);
    if (token == null) {
      throw new UnauthorizedException("No token found");
    }
    return token;
  }

  private String extractHeaderToken(NativeWebRequest request) {
    String value = request.getHeader("authorization");
    if (value == null) return null;

    String[] parts = value.split(" ");

    if (parts.length > 1) {
      return parts[1];
    }
    return null;
  }
}
