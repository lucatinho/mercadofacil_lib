/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Cidade;
import br.com.fatec.mercado_lib.model.Pessoa;
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
public class PessoaDAO implements GenericDAO {
    
    private Connection conexao;
    
    public PessoaDAO() throws Exception{
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso"); 
       } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object object) {
        Pessoa oPessoa = (Pessoa) object;
        Boolean retorno=false;
        if (oPessoa.getIdPessoa()== 0) {
            retorno = this.inserir(oPessoa);
        }else{
            retorno = this.alterar(oPessoa);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object object) {
        Pessoa oPessoa = (Pessoa) object;
        PreparedStatement stmt = null;
        String sql = "insert into pessoa (nomepessoa,idcidade,preco,descricao) values (?,?,?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getNomePessoa());        
            stmt.setInt(2, oPessoa.getCidade().getIdCidade());
            stmt.setDouble(3, oPessoa.getPreco());
            stmt.setString(4, oPessoa.getDescricao()); 
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar a Pessoa! Erro: "+ex.getMessage());
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
        Pessoa oPessoa = (Pessoa) object;
        PreparedStatement stmt = null;
        String sql= "update pessoa set nomepessoa=?, idcidade=?, idPessoa=?, preco=?  where  descricao=?"; 
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getNomePessoa());
            stmt.setInt(2, oPessoa.getCidade().getIdCidade());
            stmt.setInt(3, oPessoa.getIdPessoa());
            stmt.setDouble(4, oPessoa.getPreco()); 
            stmt.setString(5, oPessoa.getDescricao()); 
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Pessoa! Erro: "+ex.getMessage());
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
        int idPessoa = id;
        PreparedStatement stmt= null;

        String sql = "delete from pessoa where idpessoa=?";
        try {
            stmt = conexao.prepareStatement(sql);         
            stmt.setInt(1, idPessoa);
            stmt.execute();
            return true;         
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Pessoa! Erro: "+ex.getMessage());
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
        int idPessoa = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Pessoa oPessoa = null;
        String sql="select * from pessoa where idPessoa=?";
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,idPessoa);            
            rs=stmt.executeQuery();          
            while (rs.next()) {                
                oPessoa = new Pessoa();
                oPessoa.setIdPessoa(rs.getInt("idPessoa"));
                oPessoa.setDescricao(rs.getString("descricao"));
                oPessoa.setPreco(rs.getDouble("preco"));
                oPessoa.setNomePessoa(rs.getString("nomepessoa"));
                
                                
                CidadeDAO oCidadeDAO = new CidadeDAO();               
                oPessoa.setCidade((Cidade) oCidadeDAO.carregar(rs.getInt("idcidade")));
            }
            return oPessoa;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Pessoa! Erro: "+ex.getMessage());
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
        String sql = "Select * from pessoa order by nomepessoa";               
        try {
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Pessoa oPessoa = new Pessoa();
                oPessoa.setIdPessoa(rs.getInt("idPessoa"));
                oPessoa.setNomePessoa(rs.getString("nomepessoa"));
                oPessoa.setDescricao(rs.getString("descricao"));
                oPessoa.setPreco(rs.getDouble("preco"));

                try{
                    CidadeDAO oCidadeDAO = new CidadeDAO();
                    oPessoa.setCidade((Cidade) oCidadeDAO.carregar(rs.getInt("idcidade")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Pessoa! Erro: "+ex.getMessage());
                }
                
                resultado.add(oPessoa);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Pessoa! Erro: "+ex.getMessage());
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

    public List<Pessoa> listar(int idCidade) {
        List<Pessoa> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from pessoa where idcidade = ? order by nomepessoa";               
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCidade);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Pessoa oPessoa = new Pessoa();
                oPessoa.setIdPessoa(rs.getInt("idPessoa"));
                oPessoa.setNomePessoa(rs.getString("nomepessoa"));
                oPessoa.setDescricao(rs.getString("descricao"));
                oPessoa.setPreco(rs.getDouble("preco"));

                try{
                    CidadeDAO oCidadeDAO = new CidadeDAO();
                    oPessoa.setCidade((Cidade) 
                            oCidadeDAO.carregar(rs.getInt("idcidade")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Pessoa! Erro: "
                            +ex.getMessage());
                }
                
                resultado.add(oPessoa);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Pessoa! Erro: "
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
