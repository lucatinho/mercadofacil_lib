/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Cidade;
import br.com.fatec.mercado_lib.model.Pessoa;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import br.com.fatec.mercado_lib.utils.Conversao;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffersonpasserini
 */
public class PessoaDAO {
    
    
        private Connection conexao;
    
    public PessoaDAO() throws Exception{
        try{
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com sucesso!");
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public int cadastrar(Object objeto) throws ParseException {
        Pessoa oPessoa = (Pessoa) objeto;
        int retorno = 0;
        
        if (oPessoa.getIdPessoa()==0)
        {
            Pessoa objPessoa = this.carregarCpf(oPessoa.getCpf());
            if (objPessoa.getIdPessoa()==0)
                retorno = this.inserir(oPessoa);
            else
                retorno = objPessoa.getIdPessoa();
        }
        else {
            retorno = this.alterar(oPessoa);
        }
        
        return retorno;
    }

    public int inserir(Object objeto) {
        Pessoa oPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        Integer idPessoa=null;
        String sql = "insert into pessoa (cpf, nome, datanascimento, idcidade) "
                + "values (?, ?, ?, ?) returning idPessoa;";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getCpf());
            stmt.setString(2, oPessoa.getNome());
            stmt.setDate(3, new java.sql.Date(oPessoa.getDataNascimento().getTime()));
            stmt.setInt(4, oPessoa.getCidade().getIdCidade());

            rs=stmt.executeQuery();
            while (rs.next()){
                idPessoa = rs.getInt("idPessoa");
            }
        }
        catch (SQLException ex){
            System.out.println("Problemas ao cadastrar Pessoa! Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            try {
                ConnectionFactory.closeConnection(conexao, stmt,rs);
            }
            catch (Exception ex){
                System.out.println("Problemas ao fechar os parâmetos de conexão! Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;
    }

    public int alterar(Object objeto) {
        Pessoa oPessoa = (Pessoa) objeto;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        Integer idPessoa=null;
        String sql = "update pessoa set nome=?, datanascimento=?, idcidade=? "
                + "where idpessoa=? returning idPessoa;";
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oPessoa.getNome());
            stmt.setDate(2, new java.sql.Date(oPessoa.getDataNascimento().getTime()));
            stmt.setInt(3, oPessoa.getCidade().getIdCidade());
            stmt.setInt(4, oPessoa.getIdPessoa());
            rs=stmt.executeQuery();
            while (rs.next()){
                idPessoa = rs.getInt("idPessoa");
            }
        }
        catch (SQLException ex){
            System.out.println("Problemas ao cadastrar Pessoa! Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            try {
                ConnectionFactory.closeConnection(conexao, stmt,rs);
            }
            catch (Exception ex){
                System.out.println("Problemas ao fechar os parâmetos de conexão! Erro: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        return idPessoa;

    }
    
    public Pessoa carregarCpf(String cpf) throws ParseException {
       PreparedStatement stmt = null;
       ResultSet rs = null;
       Pessoa oPessoa = null;
       String sql = "Select * from pessoa where cpf=?;";

       try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
           
            while (rs.next()){
               
               oPessoa = this.carregar(rs.getInt("idpessoa"));
            }
            
            if (oPessoa == null)
            {
                Date novaData = Conversao.dataAtual();
                Cidade oCidade = new Cidade();
                oPessoa = new Pessoa(0,"","",novaData,oCidade);
            }
            
       }
       catch(SQLException ex){
           System.out.println("Problemas ao carregar pessoa!"
                   + "Erro:"+ex.getMessage());
       }
      
       return oPessoa;
    }
    
    public Pessoa carregar(int id){
        int idPessoa = id;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Pessoa oPessoa = null;
        String sql = "Select * from pessoa where idpessoa=?";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            rs=stmt.executeQuery();            

            while(rs.next()){
                
                Cidade oCidade = null;
                try{
                   CidadeDAO oCidadeDAO = new CidadeDAO();
                   int idCidade = rs.getInt("idcidade");
                   oCidade = (Cidade) oCidadeDAO.carregar(idCidade);
                }catch(Exception ex){
                   System.out.println("Problemas ao carregar usuario!"
                       + "Erro:"+ex.getMessage());
                }

                
                oPessoa = new Pessoa(rs.getInt("idpessoa"),
                                       rs.getString("cpf"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade);
            }
            return oPessoa;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar comprador! Erro "+ex.getMessage());
            return null;
        }finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt, rs);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro "+ex.getMessage());
            }
        }           
    }
    
}