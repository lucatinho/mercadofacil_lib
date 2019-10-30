package br.com.fatec.mercado_lib.model;

/**
 *
 * @author lucat
 */

public class FormaPagamento {

    /**
     * @return the idFormaPagamento
     */
    public int getIdFormaPagamento() {
        return idFormaPagamento;
    }

    /**
     * @param idFormaPagamento the idFormaPagamento to set
     */
    public void setIdFormaPagamento(int idFormaPagamento) {
        this.idFormaPagamento = idFormaPagamento;
    }

    /**
     * @return the nomeFormaPagamento
     */
    public String getNomeFormaPagamento() {
        return nomeFormaPagamento;
    }

    /**
     * @param nomeFormaPagamento the nomeFormaPagamento to set
     */
    public void setNomeFormaPagamento(String nomeFormaPagamento) {
        this.nomeFormaPagamento = nomeFormaPagamento;
    }

    private int idFormaPagamento;
    private String nomeFormaPagamento;
}
