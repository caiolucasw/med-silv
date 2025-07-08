package com.medsilveira.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.medsilveira.api.entities.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.token.secret}")
  private String TOKEN_SECRET;

  public String gerarToken(Usuario usuario) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      return JWT.create()
          .withIssuer("API MED_SILVEIRA")
          .withSubject(usuario.getLogin())
          .withExpiresAt(expiracaoData())
          .sign(algorithm);
    } catch (JWTCreationException exception) {
      throw new RuntimeException("erro ao gerrar token jwt", exception);
    }
  }

  public DecodedJWT verificaToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
      return JWT.require(algorithm)
          .withIssuer("API MED_SILVEIRA")
          .build()
          .verify(token);
    } catch (JWTVerificationException e) {
      throw new RuntimeException("Token JWT inválido ou expirado " + e.getMessage());
    }
  }

  private Instant expiracaoData() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}