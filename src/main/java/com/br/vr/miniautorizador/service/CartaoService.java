package com.br.vr.miniautorizador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.vr.miniautorizador.entity.Cartao;
import com.br.vr.miniautorizador.exception.SaldoInsuficientException;
import com.br.vr.miniautorizador.repository.CartaoRepository;
import com.br.vr.miniautorizador.util.Validation;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    public Cartao getCartao(String numeroCartao) throws Exception {

        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao);

        if (cartao == null) {
            throw new Exception("Cartao não encontrado");
        }

        return cartao;

    }

    public List<Cartao> getCartoes() throws Exception {
        return cartaoRepository.findAll();
    }

    public Cartao criarCartao(Cartao cartao) throws Exception {
        cartao.setValor(Float.valueOf("500.00"));
        return cartaoRepository.saveAndFlush(cartao);
    }

    public Cartao efetuarTransacao(Cartao cartaoNovosDados) throws Exception, SaldoInsuficientException {
        Cartao cartao = cartaoRepository.findByNumeroCartao(cartaoNovosDados.getNumeroCartao());

        if (Validation.senhaCorreta(cartao, cartaoNovosDados.getSenha()) &&
                Validation.possuiSaldo(cartao, cartaoNovosDados.getValor())) {
            Float novoValor = (cartao.getValor() - cartaoNovosDados.getValor());
            cartao.setValor(novoValor);
            cartaoRepository.saveAndFlush(cartao);
        }

        return cartaoRepository.save(cartao);
    }
}
