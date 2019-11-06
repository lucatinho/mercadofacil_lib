/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Endereco;
import br.com.fatec.mercado_lib.model.Cidade;
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
public class EnderecoDAO implements GenericDAO {
    
    private Connection conexao;
    
    public EnderecoDAO() throws Exception{
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso"); 
       } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object object) {
        Endereco oEndereco = (Endereco) object;
        Boolean retorno=false;
        if (oEndereco.getIdEndereco()== 0) {
            retorno = this.inserir(oEndereco);
        }else{
            retorno = this.alterar(oEndereco);
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object object) {
        Endereco oEndereco = (Endereco) object;
        PreparedStatement stmt = null;
        String sql = "insert into endereco (CEP,rua,numerocasa,idcidade) values (?,?,?,?)";  
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, oEndereco.getCEP());                
            stmt.setString(2, oEndereco.getRua());
            stmt.setInt(3, oEndereco.getNumeroCasa());
            stmt.setInt(4, oEndereco.getCidade().getIdCidade());  
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao cadastrar a Endereco! Erro: "+ex.getMessage());
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
        Endereco oEndereco = (Endereco) object;
        PreparedStatement stmt = null;
        String sql= "update endereco set CEP=?, rua=?, numerocasa=? idcidade=? where idEndereco=?"; 
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setDouble(1, oEndereco.getCEP());                
            stmt.setString(2, oEndereco.getRua());
            stmt.setInt(3, oEndereco.getNumeroCasa());
            stmt.setInt(4, oEndereco.getCidade().getIdCidade());          
            stmt.setInt(5, oEndereco.getIdEndereco());          
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao alterar Endereco! Erro: "+ex.getMessage());
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
        int idEndereco = id;
        PreparedStatement stmt= null;

        String sql = "delete from endereco where idendereco=?";
        try {
            stmt = conexao.prepareStatement(sql);         
            stmt.setInt(1, idEndereco);
            stmt.execute();
            return true;         
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir a Endereco! Erro: "+ex.getMessage());
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
        int idEndereco = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Endereco oEndereco = null;
        String sql="select * from endereco where idEndereco=?";
        
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1,idEndereco);            
            rs=stmt.executeQuery();         
            while (rs.next()) {                
                oEndereco = new Endereco();
                oEndereco.setIdEndereco(rs.getInt("idEndereco"));
                oEndereco.setCEP(rs.getInt("CEP"));
                oEndereco.setRua(rs.getString("rua"));
                oEndereco.setNumeroCasa(rs.getInt("numeroCasa"));
                
                CidadeDAO oCidadeDAO = new CidadeDAO();               
                oEndereco.setCidade((Cidade) oCidadeDAO.carregar(rs.getInt("idcidade")));
            }
            return oEndereco;
        } catch (Exception ex) {
            System.out.println("Problemas ao carregar Endereco! Erro: "+ex.getMessage());
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
        String sql = "Select * from endereco order by rua";               
        try {
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Endereco oEndereco = new Endereco();
                oEndereco.setIdEndereco(rs.getInt("idEndereco"));
                oEndereco.setRua(rs.getString("rua"));
                oEndereco.setNumeroCasa(rs.getInt("numeroCasa"));
                
                try{
                    CidadeDAO oCidadeDAO = new CidadeDAO();
                    oEndereco.setCidade((Cidade) oCidadeDAO.carregar(rs.getInt("idcidade")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Endereco! Erro: "+ex.getMessage());
                }
                
                resultado.add(oEndereco);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Endereco! Erro: "+ex.getMessage());
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

    public List<Endereco> listar(int idCidade) {
        List<Endereco> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from endereco where idcidade = ? order by rua";               
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCidade);
            rs=stmt.executeQuery();           
            while (rs.next()) {                
                Endereco oEndereco = new Endereco();
                oEndereco.setIdEndereco(rs.getInt("idEndereco"));
                oEndereco.setRua(rs.getString("rua"));
                oEndereco.setNumeroCasa(rs.getInt("numeroCasa"));

                try{
                    CidadeDAO oCidadeDAO = new CidadeDAO();
                    oEndereco.setCidade((Cidade) 
                            oCidadeDAO.carregar(rs.getInt("idcidade")));
                } catch (Exception ex) {
                    System.out.println("Problemas ao listar Endereco! Erro: "
                            +ex.getMessage());
                }
                
                resultado.add(oEndereco);
            }
        
        }catch (SQLException ex) {
            System.out.println("Problemas ao listar Endereco! Erro: "
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
