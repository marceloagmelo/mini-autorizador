package com.br.vr.miniautorizador.controller;

import org.springframework.web.bind.annotation.RestController;

import com.br.vr.miniautorizador.entity.Cartao;
import com.br.vr.miniautorizador.model.AuthenticatedUser;
import com.br.vr.miniautorizador.service.CartaoService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Cartao cartao) throws IOException {
        try {
            AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return ResponseEntity.status(HttpStatus.CREATED).body(cartaoService.criarCartao(cartao));
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<?> obterSaldo(@PathVariable String numeroCartao) throws IOException {
        try {
            AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return ResponseEntity.status(HttpStatus.OK).body(cartaoService.getCartao(numeroCartao));
        } catch (IOException ioe) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

}
