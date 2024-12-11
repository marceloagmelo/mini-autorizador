package com.br.vr.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.vr.miniautorizador.entity.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Integer> {
    @Query(value = "select id, numero_cartao, senha, valor from cartao where numero_cartao = :numeroCartao", nativeQuery = true)
    Cartao findByNumeroCartao(String numeroCartao);
}
