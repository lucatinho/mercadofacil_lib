/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Marca;
import br.com.fatec.mercado_lib.model.Produto;
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
public class ProdutoDAO implements GenericDAO {
    
    private Connection conexao;
    
    public ProdutoDAO() throws Exception{
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso"); 
       } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object object) {
        Produto oProduto = (Produto) object;
        Boolean retorno=false;
        if (oProduto.getIdProduto()== 0) {
            retorno = this.inserir(oProduto);
        }else{
            retorno = this.alterar(oProduto);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object object) {
        Produto oProduto = (Produto) object;
        PreparedStatement stmt = null;
        String sql = "insert into produto (nomeproduto,idmarca,preco,descricao) values (?,?,?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oProduto.getNomeProduto());        
            stmt.setInt(2, oProduto.getMarca().getIdMarca());
            stmt.setDouble(3, oProduto.getPreco());
            stmt.setString(4, oProduto.getDescricao()); 
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar a Produto! Erro: "+ex.getMessage());
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
        Produto oProduto = (Produto) object;
        PreparedStatement stmt = null;
        String sql= "update produto set nomeproduto=?, idmarca=?, idProduto=?, preco=?  where  descricao=?"; 
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oProduto.getNomeProduto());
            stmt.setInt(2, oProduto.getMarca().getIdMarca());
            stmt.setInt(3, oProduto.getIdProduto());
            stmt.setDouble(4, oProduto.getPreco()); 
            stmt.setString(5, oProduto.getDescricao()); 
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Produto! Erro: "+ex.getMessage());
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
        int idProduto = id;
        PreparedStatement stmt= null;

        String sql = "delete from produto where idproduto=?";
        try {
            stmt = conexao.prepareStatement(sql);         
            stmt.setInt(1, idProduto);
            stmt.execute();
            return true;         
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Produto! Erro: "+ex.getMessage());
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
        int idProduto = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Produto oProduto = null;
        String sql="select * from produto where idProduto=?";
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,idProduto);            
            rs=stmt.executeQuery();          
            while (rs.next()) {                
                oProduto = new Produto();
                oProduto.setIdProduto(rs.getInt("idProduto"));
                oProduto.setDescricao(rs.getString("descricao"));
                oProduto.setPreco(rs.getDouble("preco"));
                oProduto.setNomeProduto(rs.getString("nomeproduto"));
                
                                
                MarcaDAO oMarcaDAO = new MarcaDAO();               
                oProduto.setMarca((Marca) oMarcaDAO.carregar(rs.getInt("idmarca")));
            }
            return oProduto;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Produto! Erro: "+ex.getMessage());
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
        String sql = "Select * from produto order by nomeproduto";               
        try {
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Produto oProduto = new Produto();
                oProduto.setIdProduto(rs.getInt("idProduto"));
                oProduto.setNomeProduto(rs.getString("nomeproduto"));
                oProduto.setDescricao(rs.getString("descricao"));
                oProduto.setPreco(rs.getDouble("preco"));

                try{
                    MarcaDAO oMarcaDAO = new MarcaDAO();
                    oProduto.setMarca((Marca) oMarcaDAO.carregar(rs.getInt("idmarca")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Produto! Erro: "+ex.getMessage());
                }
                
                resultado.add(oProduto);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Produto! Erro: "+ex.getMessage());
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

    public List<Produto> listar(int idMarca) {
        List<Produto> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from produto where idmarca = ? order by nomeproduto";               
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idMarca);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Produto oProduto = new Produto();
                oProduto.setIdProduto(rs.getInt("idProduto"));
                oProduto.setNomeProduto(rs.getString("nomeproduto"));
                oProduto.setDescricao(rs.getString("descricao"));
                oProduto.setPreco(rs.getDouble("preco"));

                try{
                    MarcaDAO oMarcaDAO = new MarcaDAO();
                    oProduto.setMarca((Marca) 
                            oMarcaDAO.carregar(rs.getInt("idmarca")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Produto! Erro: "
                            +ex.getMessage());
                }
                
                resultado.add(oProduto);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Produto! Erro: "
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
