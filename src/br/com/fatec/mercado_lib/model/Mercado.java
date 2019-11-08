/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.model;


import br.com.fatec.mercado_lib.utils.Conversao;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author jeffersonpasserini
 */
public class Mercado extends Pessoa {
    
    private int idMercado;
    private String url;
    private String observacao;
    private String situacao;

    public Mercado(int idMercado, String url, String observacao, String situacao, int idPessoa, String cpf, String nome, Date dataNascimento, Cidade cidade) {
        super(idPessoa, cpf, nome, dataNascimento, cidade);
        this.idMercado = idMercado;
        this.url = url;
        this.observacao = observacao;
        this.situacao = situacao;
    }

    public static Mercado mercadoVazio() throws ParseException{
        Cidade oCidade = new Cidade();
        Date dataNascimento = Conversao.dataAtual();
        Mercado oMercado = new Mercado(0,"","","A",0,"","",dataNascimento,oCidade);
        return oMercado;
    }
    
    public int getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(int idMercado) {
        this.idMercado = idMercado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
}
