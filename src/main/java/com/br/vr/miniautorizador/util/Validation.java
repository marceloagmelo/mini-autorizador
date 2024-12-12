package com.br.vr.miniautorizador.util;

import com.br.vr.miniautorizador.entity.Cartao;
import com.br.vr.miniautorizador.exception.SaldoInsuficientException;
import com.br.vr.miniautorizador.exception.SenhaInvalidaException;

public class Validation {

    public static boolean possuiSaldo(Cartao cartao, float valor) throws SaldoInsuficientException {
        if (valor > cartao.getValor()) {
            throw new SaldoInsuficientException();
        }
        return true;
    }

    public static boolean senhaCorreta(Cartao cartao, String senha) throws SenhaInvalidaException {
        if (!senha.equals(cartao.getSenha())) {
            throw new SenhaInvalidaException();
        }
        return true;
    }

    public static boolean autorizarTransacao(Cartao cartao, String senha, float valor)
            throws SenhaInvalidaException, SaldoInsuficientException {
        return (senhaCorreta(cartao, senha) && possuiSaldo(cartao, valor));
    }

}
