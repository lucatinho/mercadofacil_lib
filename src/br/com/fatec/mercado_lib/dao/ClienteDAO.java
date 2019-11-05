/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Cliente;
import br.com.fatec.mercado_lib.model.Endereco;
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
public class ClienteDAO implements GenericDAO {
    
    private Connection conexao;
    
    public ClienteDAO() throws Exception{
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso"); 
       } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object object) {
        Cliente oCliente = (Cliente) object;
        Boolean retorno=false;
        if (oCliente.getIdCliente()== 0) {
            retorno = this.inserir(oCliente);
        }else{
            retorno = this.alterar(oCliente);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object object) {
        Cliente oCliente = (Cliente) object;
        PreparedStatement stmt = null;
        String sql = "insert into cidade (nomepessoa,CPF,idendereco) values (?,?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oCliente.getNomePessoa());        
            stmt.setDouble(2, oCliente.getCPF());        
            stmt.setInt(3, oCliente.getEndereco().getIdEndereco());        
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar a Cliente! Erro: "+ex.getMessage());
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
        Cliente oCliente = (Cliente) object;
        PreparedStatement stmt = null;
        String sql= "update cidade set nomecidade=?, CPF=?, idEndereco=? where idCliente=?"; 
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oCliente.getNomePessoa());
            stmt.setDouble(2, oCliente.getCPF());
            stmt.setInt(3, oCliente.getEndereco().getIdEndereco());
            stmt.setInt(4, oCliente.getIdCliente());            
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Cliente! Erro: "+ex.getMessage());
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
        int idCliente = id;
        PreparedStatement stmt= null;

        String sql = "delete from cidade where idcidade=?";
        try {
            stmt = conexao.prepareStatement(sql);         
            stmt.setInt(1, idCliente);
            stmt.execute();
            return true;         
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Cliente! Erro: "+ex.getMessage());
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
        int idCliente = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Cliente oCliente = null;
        String sql="select * from cidade where idCliente=?";
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,idCliente);            
            rs=stmt.executeQuery();          
            while (rs.next()) {                
                oCliente = new Cliente();
                oCliente.setIdCliente(rs.getInt("idCliente"));
                oCliente.setNomePessoa(rs.getString("nomecidade"));
                
                EnderecoDAO oEnderecoDAO = new EnderecoDAO();               
                oCliente.setEndereco((Endereco) oEnderecoDAO.carregar(rs.getInt("idendereco")));
            }
            return oCliente;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Cliente! Erro: "+ex.getMessage());
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
                Cliente oCliente = new Cliente();
                oCliente.setIdCliente(rs.getInt("idCliente"));
                oCliente.setNomePessoa(rs.getString("nomecidade"));

                try{
                    EnderecoDAO oEnderecoDAO = new EnderecoDAO();
                    oCliente.setEndereco((Endereco) oEnderecoDAO.carregar(rs.getInt("idendereco")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Cliente! Erro: "+ex.getMessage());
                }
                
                resultado.add(oCliente);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Cliente! Erro: "+ex.getMessage());
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

    public List<Cliente> listar(int idEndereco) {
        List<Cliente> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from cidade where idendereco = ? order by nomecidade";               
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idEndereco);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Cliente oCliente = new Cliente();
                oCliente.setIdCliente(rs.getInt("idCliente"));
                oCliente.setNomePessoa(rs.getString("nomecidade"));

                try{
                    EnderecoDAO oEnderecoDAO = new EnderecoDAO();
                    oCliente.setEndereco((Endereco) 
                            oEnderecoDAO.carregar(rs.getInt("idendereco")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Cliente! Erro: "
                            +ex.getMessage());
                }
                
                resultado.add(oCliente);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Cliente! Erro: "
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
