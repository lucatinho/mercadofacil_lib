/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.mercado_lib.dao;

import br.com.fatec.mercado_lib.model.Cidade;
import br.com.fatec.mercado_lib.model.Mercado;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jeffersonpasserini
 */
public class MercadoDAO implements GenericDAO {
        
    private Connection conexao;
        
    public MercadoDAO() throws Exception{
        try{
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com sucesso");
        }catch(Exception ex){
            throw new Exception("Problemas ao conectar no BD! Erro: "+ex.getMessage());
        }
    }

    @Override
    public Boolean cadastrar(Object objeto) {
        Boolean retorno = false;
        try {
            Mercado oMercado = (Mercado) objeto;
            
            if (oMercado.getIdMercado()==0) {
                //verifica se já existe pessoa com este CPF cadastrada.
                int idMercado = this.verificarCpf(oMercado.getCpf());
                if (idMercado==0) {
                    //se não encontrou insere
                    retorno = this.inserir(oMercado);
                }else{
                    //se encontrou cliente com o cpf altera
                    oMercado.setIdMercado(idMercado);
                    retorno = this.alterar(oMercado);
                }
            } else {
              retorno = this.alterar(oMercado);
            }
            
        } catch (Exception ex){
            System.out.println("Problemas ao incluir mercado! Erro "+ex.getMessage());            
        }
        return retorno;

    }

    @Override
    public Boolean inserir(Object objeto) {
        Mercado oMercado = (Mercado) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into mercado (idPessoa, url, observacao, situacao) values (?, ?, ?, ?)";
        
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            int idPessoa = oPessoaDAO.cadastrar(oMercado);
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            stmt.setString(2, oMercado.getUrl());
            stmt.setString(3, oMercado.getObservacao());
            stmt.setString(4, "A");
            stmt.execute();
            
            return true;
        }catch(Exception ex){
            System.out.println("Problemas ao incluir mercado! Erro "+ex.getMessage());
            return false;
        }finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro: "+ex.getMessage());
            }
        }
    }

    @Override
    public Boolean alterar(Object objeto) {
        Mercado oMercado = (Mercado) objeto;
        PreparedStatement stmt = null;
        String sql = "update mercado set url=?, observacao=? "
                + "where idMercado=?";
        
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.cadastrar(oMercado);
            
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oMercado.getUrl());
            stmt.setString(2, oMercado.getObservacao());
            stmt.setInt(3, oMercado.getIdMercado());
            stmt.execute();
            
            return true;
        }catch(Exception ex){
            System.out.println("Problemas ao alterar mercado! Erro: "+ex.getMessage());
            return false;
        }finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro "+ex.getMessage());
            }
        }
    }

    @Override
    public Boolean excluir(int id) {
        
        PreparedStatement stmt = null;
                
        try{
            //carrega dados de mercado
            MercadoDAO oMercadoDAO = new MercadoDAO();
            Mercado oMercado = (Mercado) oMercadoDAO.carregar(id);
            //verifica a situação do mercado
            String situacao="A";
            if(oMercado.getSituacao().equals(situacao)){
                situacao = "I";
            }else{
                situacao = "A";
            }
            //prepara comando sql
            String sql = "update mercado set situacao=? where idMercado=?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, situacao);
            stmt.setInt(2, oMercado.getIdMercado());
            stmt.execute();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao ativar/desativar mercado! Erro: "+ex.getMessage());
            return false;
        }finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parâmetros de conexão! Erro: "+ex.getMessage());
            }
        }
    }

    @Override
    public Object carregar(int numero) {
        int idMercado = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Mercado oMercado = null;
        String sql = "Select p.*, c.idmercado, c.url, c.observacao, c.situacao"
                + " from mercado c, pessoa p "
                + "where c.idpessoa = p.idpessoa and c.idmercado=?";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idMercado);
            rs=stmt.executeQuery();
            
            while(rs.next()){
                //Busca a cidade
                Cidade oCidade = null;
                try{
                   CidadeDAO oCidadeDAO = new CidadeDAO();
                   oCidade = (Cidade) oCidadeDAO.carregar(rs.getInt("idcidade"));
                }catch(Exception ex){
                   System.out.println("Problemas ao carregar cidade!"
                       + "Erro:"+ex.getMessage());
                }
                
                oMercado = new Mercado(rs.getInt("idmercado"),
                                       rs.getString("url"),
                                       rs.getString("observacao"),
                                       rs.getString("situacao"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpf"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade);
            }
            return oMercado;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar mercado! Erro "+ex.getMessage());
            return null;
        }finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt, rs);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar os parâmetros de conexão! Erro "+ex.getMessage());
            }
        }   
    }

    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String sql= "Select p.*, c.idmercado, c.url, c.observacao, c.situacao"
                + " from mercado c, pessoa p "
                + "where c.idpessoa = p.idpessoa order by idPessoa";
        try{
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()){
                
                //busca cidade
                Cidade oCidade = null;
                try{
                   CidadeDAO oCidadeDAO = new CidadeDAO();
                   oCidade = (Cidade) oCidadeDAO.carregar(rs.getInt("idcidade"));
                }catch(Exception ex){
                   System.out.println("Problemas ao carregar mercado!"
                       + "Erro:"+ex.getMessage());
                }
                
                Mercado  oMercado = new Mercado(rs.getInt("idmercado"),
                                       rs.getString("url"),
                                       rs.getString("observacao"),
                                       rs.getString("situacao"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpf"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade);

                resultado.add(oMercado);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar mercado! Erro "+ex.getMessage());
        }finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt, rs);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parâmetros d econexão! Erro: "+ex.getMessage());
            }
        }
        return resultado;
    }
    
    public int verificarCpf(String cpf){
        PreparedStatement stmt = null;
        ResultSet rs= null;
        int idMercado = 0;
        String sql = "Select f.* from mercado f, pessoa p "
                + "where f.idpessoa = p.idPessoa and p.cpf=?;";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            
            while(rs.next()){
                idMercado = rs.getInt("idmercado");
            }
            return idMercado;
        }catch(SQLException ex){
            System.out.println("Problemas ai carregar pessoa! Erro: "+ex.getMessage());
            return idMercado;
        }
    }   
}
