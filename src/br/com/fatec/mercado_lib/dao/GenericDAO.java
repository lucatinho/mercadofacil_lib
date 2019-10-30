package br.com.fatec.mercado_lib.dao;

import java.util.List;

/**
 * @author Edjunior
 */
public interface GenericDAO {
    
    public Boolean cadastrar(Object objeto);
    public Boolean inserir(Object objeto);
    public Boolean alterar(Object objeto);
    public Boolean excluir(int numero);
    public Object carregar(int numero);
    public List<Object>listar(); 
}