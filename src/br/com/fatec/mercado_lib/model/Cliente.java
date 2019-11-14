package br.com.fatec.mercado_lib.model;


import br.com.fatec.mercado_lib.utils.Conversao;
import java.text.ParseException;
import java.util.Date;

/**
 *
 * @author jeffersonpasserini
 */
public class Cliente extends Pessoa {

    private int idCliente;
    private String situacao;
    private String rua;
    
    public Cliente(int idCliente, String situacao, String rua, int idPessoa, String cpf, String nome, Date dataNascimento, Cidade cidade) {
        super(idPessoa, cpf, nome, dataNascimento, cidade);
        this.idCliente = idCliente;
        this.situacao = situacao;
        this.rua=rua;
    }
    
    public static Cliente clienteVazio() throws ParseException{
        Cidade oCidade = new Cidade();
        Date dataNascimento = Conversao.dataAtual();
        Cliente oCliente = new Cliente(0,"A","",0,"","",dataNascimento,oCidade);
        return oCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }
}
