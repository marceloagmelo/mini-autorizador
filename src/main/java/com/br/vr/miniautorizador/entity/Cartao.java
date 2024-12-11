package com.br.vr.miniautorizador.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cartao")
public class Cartao implements Serializable {
    private static final long serialVersionUID = -2468212262147809331L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "numero_cartao")
    private String numeroCartao;
    @Column(name = "senha")
    private String senha;
    @Column(name = "valor")
    private Float valor;
}
