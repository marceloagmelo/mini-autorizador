package com.br.vr.miniautorizador.controller;

import org.springframework.web.bind.annotation.RestController;

import com.br.vr.miniautorizador.entity.Cartao;
import com.br.vr.miniautorizador.entity.MensagemErro;
import com.br.vr.miniautorizador.exception.SaldoInsuficientException;
import com.br.vr.miniautorizador.exception.SenhaInvalidaException;
import com.br.vr.miniautorizador.service.CartaoService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class CartaoController {

    @Autowired
    CartaoService cartaoService;

    @PostMapping("/cartoes")
    public ResponseEntity<?> criar(@RequestBody Cartao cartao) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cartaoService.criarCartao(cartao));
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<?> obterSaldo(@PathVariable String numeroCartao) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cartaoService.getCartao(numeroCartao));
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping("/transacoes")
    public ResponseEntity<?> transacoes(@RequestBody Cartao cartao) throws IOException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cartaoService.efetuarTransacao(cartao));
        } catch (SenhaInvalidaException esi) {
            MensagemErro erro = new MensagemErro("Senha invalida");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
        } catch (SaldoInsuficientException esi) {
            MensagemErro erro = new MensagemErro("Saldo insuficiente");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
