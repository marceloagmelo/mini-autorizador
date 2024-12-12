package com.br.vr.miniautorizador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.vr.miniautorizador.entity.Cartao;
import com.br.vr.miniautorizador.exception.CartaoInexistenteException;
import com.br.vr.miniautorizador.exception.SaldoInsuficientException;
import com.br.vr.miniautorizador.exception.SenhaInvalidaException;
import com.br.vr.miniautorizador.repository.CartaoRepository;

@ExtendWith(MockitoExtension.class)
public class CartaoServiceTest {
    @InjectMocks
    private CartaoService cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @Test
    @DisplayName("Criação de cartão e verificação de saldo")
    void criarCartao() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");

        cartaoService.criarCartao(cartao);
        verify(cartaoRepository).saveAndFlush(any());

        Assertions.assertEquals(Float.valueOf("500.00"), cartao.getValor());

    }

    @Test
    @DisplayName("Transção de cartão")
    void efetuarTransacaoCase1() throws Exception {
        Cartao cartaoEsperado = new Cartao();
        cartaoEsperado.setNumeroCartao("6549873025634501");
        cartaoEsperado.setSenha("1234");
        cartaoEsperado.setValor(Float.valueOf("0.00"));
        when(cartaoRepository.findByNumeroCartao(cartaoEsperado.getNumeroCartao())).thenReturn(cartaoEsperado);

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setValor(Float.valueOf("500.00"));
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(cartao);

        cartaoService.efetuarTransacao(cartao);
        verify(cartaoRepository).saveAndFlush(any());

        assertEquals(cartaoEsperado.getValor(), cartao.getValor());
    }

    @Test
    @DisplayName("Deve lançar exceção com saldo insuficiente")
    void efetuarTransacaoCase2() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setValor(Float.valueOf("500.00"));
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(cartao);

        Cartao cartaoTransacao = new Cartao();
        cartaoTransacao.setNumeroCartao("6549873025634501");
        cartaoTransacao.setSenha("1234");
        cartaoTransacao.setValor(Float.valueOf("600.00"));

        Assertions.assertThrows(SaldoInsuficientException.class, () -> cartaoService.efetuarTransacao(cartaoTransacao));
    }

    @Test
    @DisplayName("Deve lançar exceção senha inválida")
    void efetuarTransacaoCase3() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setValor(Float.valueOf("500.00"));
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(cartao);

        Cartao cartaoTransacao = new Cartao();
        cartaoTransacao.setNumeroCartao("6549873025634501");
        cartaoTransacao.setSenha("12345");
        cartaoTransacao.setValor(Float.valueOf("500.00"));

        Assertions.assertThrows(SenhaInvalidaException.class, () -> cartaoService.efetuarTransacao(cartaoTransacao));
    }

    @Test
    @DisplayName("Deve lançar exceção com cartão inexistente")
    void efetuarTransacaoCase4() throws Exception {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setValor(Float.valueOf("500.00"));
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(cartao);

        Cartao cartaoTransacao = new Cartao();
        cartaoTransacao.setNumeroCartao("6549873025634502");
        cartaoTransacao.setSenha("1234");
        cartaoTransacao.setValor(Float.valueOf("500.00"));

        Assertions.assertThrows(CartaoInexistenteException.class,
                () -> cartaoService.efetuarTransacao(cartaoTransacao));
    }

}
