package com.medsilveira.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.medsilveira.api.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  UserDetails findByLogin(String username);

  // Aqui você pode adicionar métodos personalizados, se necessário
  // Por exemplo, para encontrar um usuário por login:
  // Optional<Usuario> findByLogin(String login);

  // Outros métodos de consulta podem ser adicionados conforme necessário

}
