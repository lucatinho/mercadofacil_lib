package br.com.fatec.mercado_lib.model;

/**
 *
 * @author lucat
 */

public class StatusPedido {

    /**
     * @return the idStatusPedido
     */
    public int getIdStatusPedido() {
        return idStatusPedido;
    }

    /**
     * @param idStatusPedido the idStatusPedido to set
     */
    public void setIdStatusPedido(int idStatusPedido) {
        this.idStatusPedido = idStatusPedido;
    }

    /**
     * @return the nomeStatusPedido
     */
    public String getNomeStatusPedido() {
        return nomeStatusPedido;
    }

    /**
     * @param nomeStatusPedido the nomeStatusPedido to set
     */
    public void setNomeStatusPedido(String nomeStatusPedido) {
        this.nomeStatusPedido = nomeStatusPedido;
    }

    private int idStatusPedido;
    private String nomeStatusPedido;
}
