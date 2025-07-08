package com.medsilveira.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medsilveira.api.dto.DadosLoginDTO;
import com.medsilveira.api.dto.TokenDTO;
import com.medsilveira.api.entities.Usuario;
import com.medsilveira.api.services.TokenService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @Value("${api.token.secret}")
  private String TOKEN_SECRET;

  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid DadosLoginDTO dadosLogin) {
    var dados = new UsernamePasswordAuthenticationToken(dadosLogin.login(), dadosLogin.senha());
    Authentication authentication = authenticationManager.authenticate(dados);

    String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());

    return ResponseEntity.ok(new TokenDTO(token));
  }

}
