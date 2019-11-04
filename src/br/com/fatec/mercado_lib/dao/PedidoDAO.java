/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Produto;
import br.com.fatec.mercado_lib.model.Pedido;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffersonpasserini
 */
public class PedidoDAO implements GenericDAO {
    
    private Connection conexao;
    
    public PedidoDAO() throws Exception{
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso"); 
       } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object object) {
        Pedido oPedido = (Pedido) object;
        Boolean retorno=false;
        if (oPedido.getIdPedido()== 0) {
            retorno = this.inserir(oPedido);
        }else{
            retorno = this.alterar(oPedido);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object object) {
        Pedido oPedido = (Pedido) object;
        PreparedStatement stmt = null;
        String sql = "insert into pedido (idpedido,idcliente,idproduto,preco) values (?,?,?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, oPedido.getIdPedido());
            stmt.setInt(2, oPedido.getCliente().getIdCliente());
            stmt.setInt(3, oPedido.getProduto().getIdProduto());
            stmt.setDouble(4, oPedido.getProduto().getPreco());
            
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar a Pedido! Erro: "+ex.getMessage());
            return false;
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! Erro: "+ex.getMessage());
            }
        }
    }

    @Override
    public Boolean alterar(Object object) {
        Pedido oPedido = (Pedido) object;
        PreparedStatement stmt = null;
        String sql= "update pedido set idpedido?, idcliente=?, idproduto=? where preco=?"; 
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(3, oPedido.getIdPedido());
            stmt.setInt(1, oPedido.getCliente().getIdCliente());
            stmt.setInt(2, oPedido.getProduto().getIdProduto());
            stmt.setDouble(4, oPedido.getProduto().getPreco()); 
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Pedido! Erro: "+ex.getMessage());
            return false;
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! Erro: "+ex.getMessage());
            }
        }
    }

    @Override
    public Boolean excluir(int id) {
        int idPedido = id;
        PreparedStatement stmt= null;

        String sql = "delete from pedido where idpedido=?";
        try {
            stmt = conexao.prepareStatement(sql);         
            stmt.setInt(1, idPedido);
            stmt.execute();
            return true;         
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Pedido! Erro: "+ex.getMessage());
            return false;           
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! Erro: "+ex.getMessage());
            }
        }
    }

    @Override
    public Object carregar(int numero) {
        int idPedido = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Pedido oPedido = null;
        String sql="select * from pedido where idPedido=?";
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,idPedido);
            rs=stmt.executeQuery();          
            while (rs.next()) {                
                oPedido = new Pedido();
                oPedido.setIdPedido(rs.getInt("idPedido"));
                                
                ProdutoDAO oProdutoDAO = new ProdutoDAO();               
                oPedido.setProduto((Produto) oProdutoDAO.carregar(rs.getInt("idproduto")));
            }
            return oPedido;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Pedido! Erro: "+ex.getMessage());
            return false;   
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt,rs);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! Erro: "+ex.getMessage());
            }
        }
    }
    
    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from pedido order by nomepedido";               
        try {
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Pedido oPedido = new Pedido();
                oPedido.setIdPedido(rs.getInt("idPedido"));

                try{
                    ProdutoDAO oProdutoDAO = new ProdutoDAO();
                    oPedido.setProduto((Produto) oProdutoDAO.carregar(rs.getInt("idproduto")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Pedido! Erro: "+ex.getMessage());
                }
                
                resultado.add(oPedido);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Pedido! Erro: "+ex.getMessage());
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! Erro: "+ex.getMessage());
            }
        }
        return resultado;
    }

    public List<Pedido> listar(int idProduto) {
        List<Pedido> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from pedido where idproduto = ? order by nomepedido";               
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idProduto);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Pedido oPedido = new Pedido();
                oPedido.setIdPedido(rs.getInt("idPedido"));

                try{
                    ProdutoDAO oProdutoDAO = new ProdutoDAO();
                    oPedido.setProduto((Produto) 
                            oProdutoDAO.carregar(rs.getInt("idproduto")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Pedido! Erro: "
                            +ex.getMessage());
                }
                
                resultado.add(oPedido);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Pedido! Erro: "
                    +ex.getMessage());
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! "
                        + "Erro: "+ex.getMessage());
            }
        }
        return resultado;
    }
    
}
