/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Cidade;
import br.com.fatec.mercado_lib.model.Estado;
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
public class CidadeDAO implements GenericDAO {
    
    private Connection conexao;
    
    public CidadeDAO() throws Exception{
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso"); 
       } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object object) {
        Cidade oCidade = (Cidade) object;
        Boolean retorno=false;
        if (oCidade.getIdCidade()== 0) {
            retorno = this.inserir(oCidade);
        }else{
            retorno = this.alterar(oCidade);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object object) {
        Cidade oCidade = (Cidade) object;
        PreparedStatement stmt = null;
        String sql = "insert into cidade (nomecidade,idestado) values (?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oCidade.getNomeCidade());        
            stmt.setInt(2, oCidade.getEstado().getIdEstado());        
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar a Cidade! Erro: "+ex.getMessage());
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
        Cidade oCidade = (Cidade) object;
        PreparedStatement stmt = null;
        String sql= "update cidade set nomecidade=?, idestado=? where idCidade=?"; 
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oCidade.getNomeCidade());
            stmt.setInt(2, oCidade.getEstado().getIdEstado());
            stmt.setInt(3, oCidade.getIdCidade());            
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Cidade! Erro: "+ex.getMessage());
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
        int idCidade = id;
        PreparedStatement stmt= null;

        String sql = "delete from cidade where idcidade=?";
        try {
            stmt = conexao.prepareStatement(sql);         
            stmt.setInt(1, idCidade);
            stmt.execute();
            return true;         
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Cidade! Erro: "+ex.getMessage());
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
        int idCidade = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Cidade oCidade = null;
        String sql="select * from cidade where idCidade=?";
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,idCidade);            
            rs=stmt.executeQuery();          
            while (rs.next()) {                
                oCidade = new Cidade();
                oCidade.setIdCidade(rs.getInt("idCidade"));
                oCidade.setNomeCidade(rs.getString("nomecidade"));
                
                EstadoDAO oEstadoDAO = new EstadoDAO();               
                oCidade.setEstado((Estado) oEstadoDAO.carregar(rs.getInt("idestado")));
            }
            return oCidade;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Cidade! Erro: "+ex.getMessage());
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
        String sql = "Select * from cidade order by nomecidade";               
        try {
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Cidade oCidade = new Cidade();
                oCidade.setIdCidade(rs.getInt("idCidade"));
                oCidade.setNomeCidade(rs.getString("nomecidade"));

                try{
                    EstadoDAO oEstadoDAO = new EstadoDAO();
                    oCidade.setEstado((Estado) oEstadoDAO.carregar(rs.getInt("idestado")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Cidade! Erro: "+ex.getMessage());
                }
                
                resultado.add(oCidade);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Cidade! Erro: "+ex.getMessage());
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

    public List<Cidade> listar(int idEstado) {
        List<Cidade> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from cidade where idestado = ? order by nomecidade";               
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idEstado);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Cidade oCidade = new Cidade();
                oCidade.setIdCidade(rs.getInt("idCidade"));
                oCidade.setNomeCidade(rs.getString("nomecidade"));

                try{
                    EstadoDAO oEstadoDAO = new EstadoDAO();
                    oCidade.setEstado((Estado) 
                            oEstadoDAO.carregar(rs.getInt("idestado")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Cidade! Erro: "
                            +ex.getMessage());
                }
                
                resultado.add(oCidade);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Cidade! Erro: "
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
