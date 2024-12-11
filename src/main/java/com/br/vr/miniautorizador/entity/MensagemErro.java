package com.br.vr.miniautorizador.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensagemErro {
    private String message;

    public MensagemErro(String mensagem) {
        this.message = mensagem;
    }
}
