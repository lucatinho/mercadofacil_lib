package br.com.fatec.mercado_lib.model;

/**
 *
 * @author lucat
 */
public class Categoria {

    private int idCategoria;
    private String nomeCategoria;

    public Categoria() {
        this.idCategoria = 0;
        this.nomeCategoria = "";
    }

    public Categoria(int idCategoria, String nomeCategoria) {
        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

}
