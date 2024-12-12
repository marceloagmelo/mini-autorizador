package com.br.vr.miniautorizador.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.vr.miniautorizador.entity.Cartao;
import com.br.vr.miniautorizador.entity.MensagemErro;
import com.br.vr.miniautorizador.exception.SaldoInsuficientException;
import com.br.vr.miniautorizador.exception.SenhaInvalidaException;
import com.br.vr.miniautorizador.service.CartaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    @Autowired
    private CartaoService cartaoService;

    @PostMapping
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
            MensagemErro erro = new MensagemErro("Cartão inexistente");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }
    }
}
