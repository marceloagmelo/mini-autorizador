package com.br.vr.miniautorizador.exception;

public class CartaoInexistenteException extends Exception {

    @Override
    public String toString() {
        return "Cartão inexistente";
    }
}
