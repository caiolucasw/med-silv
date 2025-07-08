package com.medsilveira.api.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.medsilveira.api.repositories.UsuarioRepository;
import com.medsilveira.api.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AutorizacaoFilter extends OncePerRequestFilter {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    System.out.println("hahahahahahahahahahahahahahhhaha");
    var authorizationHeader = request.getHeader("Authorization");
    var token = authorizationHeader != null ? authorizationHeader.replace("Bearer ", "") : null;

    System.out.println("dasdasdsda a");
    if (token != null) {
      DecodedJWT decodedJWT = tokenService.verificaToken(token);
      autenticarUsuarioByToken(decodedJWT);
    }

    filterChain.doFilter(request, response);

  }

  public void autenticarUsuarioByToken(DecodedJWT decodedJWT) {

    System.out.println("Autenticando usuário pelo token JWT");

    String login = decodedJWT.getSubject();

    var usuario = usuarioRepository.findByLogin(login);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuário ou senha inválidos");
    }

    var authToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authToken);

  }

}
