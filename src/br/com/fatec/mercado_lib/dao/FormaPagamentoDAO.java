package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.FormaPagamento;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FormaPagamentoDAO implements GenericDAO {
    
    private Connection conexao;
    
    public FormaPagamentoDAO() throws Exception {
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso");
        } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: " + ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object objeto) {
        FormaPagamento oFormaPagamento = (FormaPagamento) objeto;
        Boolean retorno = false;
        if (oFormaPagamento.getIdFormaPagamento() == 0) {
            retorno = this.inserir(oFormaPagamento);
        } else {
            retorno = this.alterar(oFormaPagamento);
        }
        return retorno;
    }
    
    @Override
    public Boolean inserir(Object objeto) {
        FormaPagamento oFormaPagamento = (FormaPagamento) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into formaPagamento (nomeformaPagamento) values (?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oFormaPagamento.getNomeFormaPagamento());
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Probelmas ao cadastrar a FormaPagamento! Error: " + ex.getMessage());
            return false;
        } finally {
            try {
                ConnectionFactory.closeConnection(conexao, stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar parametros de conexão! Erro: " + ex.getMessage());
            }
        }
    }
    
    @Override
        public Boolean alterar(Object objeto) {
       FormaPagamento oFormaPagamento = (FormaPagamento) objeto;
       PreparedStatement stmt = null;
       String sql = "update formaPagamento set nomeformaPagamento =? where idFormaPagamento=?";
       try{
           stmt = conexao.prepareStatement(sql);
           stmt.setString(1, oFormaPagamento.getNomeFormaPagamento());
           stmt.setInt(2, oFormaPagamento.getIdFormaPagamento());
           stmt.execute();
           return true;
       }catch(Exception ex){
           System.out.println("Problemas ao alterar FormaPagamento! Erro: " +ex.getMessage());
           return false;
       }
       finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão! Erro: " +ex.getMessage());
            }
        }
    }
    
    @Override
    public Boolean excluir(int numero) {
        int idFormaPagamento = numero;
        PreparedStatement stmt = null;
        
        String sql = "delete from FormaPagamento where idFormaPagamento=? ";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idFormaPagamento);
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir o FormaPagamento! Erro: " + ex.getMessage());
            return false;
        } finally {
            try {
                ConnectionFactory.closeConnection(conexao, stmt);
            } catch (Exception ex) {
                System.out.println("Problemas ao fechar parametros de conexão! Erro: " + ex.getMessage());
            }
        }
    }
    
    @Override
    public Object carregar(int numero) {
        int idFormaPagamento = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        FormaPagamento oFormaPagamento = null;
        String sql="select * from formaPagamento where idFormaPagamento=?";
        
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idFormaPagamento);
            rs=stmt.executeQuery();
            while (rs.next()){
                oFormaPagamento = new FormaPagamento();
                oFormaPagamento.setIdFormaPagamento(rs.getInt("idFormaPagamento"));
                oFormaPagamento.setNomeFormaPagamento(rs.getString("nomeformaPagamento"));
            }
            return oFormaPagamento;
        }catch (Exception ex){
            System.out.println("Problemas ao carregar FormaPagamento! Erro: "+ex.getMessage());
            return false;
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão!" + " Erro: "+ex.getMessage());
            }
        }
    }
    
    @Override
    public List<Object> listar() {
        List<Object> resultado = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "Select * from formaPagamento order by idFormaPagamento";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()){
                FormaPagamento oFormaPagamento = new FormaPagamento();
                oFormaPagamento.setIdFormaPagamento(rs.getInt("idformaPagamento"));
                oFormaPagamento.setNomeFormaPagamento(rs.getString("nomeformaPagamento"));
                resultado.add(oFormaPagamento);
            }
        }catch (SQLException ex){
            System.out.println("Problemas ao listar FormaPagamento! Erro: "+ex.getMessage());
        }
        finally{
            try{
                ConnectionFactory.closeConnection(conexao, stmt,rs);
            }catch(Exception ex){
                System.out.println("Problemas ao fechar parametros de conexão!"+"Erro: "+ex.getMessage());
            }
        }
        return resultado;
    }
    
}
