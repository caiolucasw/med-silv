package com.medsilveira.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medsilveira.api.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
  // Aqui você pode adicionar métodos personalizados de consulta, se necessário

}
