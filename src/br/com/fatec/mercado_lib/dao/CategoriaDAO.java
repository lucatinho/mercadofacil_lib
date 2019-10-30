package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Categoria;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CategoriaDAO implements GenericDAO {
    
    private Connection conexao;
    
    public CategoriaDAO() throws Exception {
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso");
        } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: " + ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object objeto) {
        Categoria oCategoria = (Categoria) objeto;
        Boolean retorno = false;
        if (oCategoria.getIdCategoria() == 0) {
            retorno = this.inserir(oCategoria);
        } else {
            retorno = this.alterar(oCategoria);
        }
        return retorno;
    }
    
    @Override
    public Boolean inserir(Object objeto) {
        Categoria oCategoria = (Categoria) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into categoria (nomecategoria) values (?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oCategoria.getNomeCategoria());
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Probelmas ao cadastrar a Categoria! Error: " + ex.getMessage());
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
       Categoria oCategoria = (Categoria) objeto;
       PreparedStatement stmt = null;
       String sql = "update categoria set nomecategoria =? where idCategoria=?";
       try{
           stmt = conexao.prepareStatement(sql);
           stmt.setString(1, oCategoria.getNomeCategoria());
           stmt.setInt(2, oCategoria.getIdCategoria());
           stmt.execute();
           return true;
       }catch(Exception ex){
           System.out.println("Problemas ao alterar Categoria! Erro: " +ex.getMessage());
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
        int idCategoria = numero;
        PreparedStatement stmt = null;
        
        String sql = "delete from Categoria where idCategoria=? ";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCategoria);
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir o Categoria! Erro: " + ex.getMessage());
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
        int idCategoria = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Categoria oCategoria = null;
        String sql="select * from categoria where idCategoria=?";
        
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idCategoria);
            rs=stmt.executeQuery();
            while (rs.next()){
                oCategoria = new Categoria();
                oCategoria.setIdCategoria(rs.getInt("idCategoria"));
                oCategoria.setNomeCategoria(rs.getString("nomecategoria"));
            }
            return oCategoria;
        }catch (Exception ex){
            System.out.println("Problemas ao carregar Categoria! Erro: "+ex.getMessage());
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
        String sql = "Select * from categoria order by idCategoria";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()){
                Categoria oCategoria = new Categoria();
                oCategoria.setIdCategoria(rs.getInt("idcategoria"));
                oCategoria.setNomeCategoria(rs.getString("nomecategoria"));
                resultado.add(oCategoria);
            }
        }catch (SQLException ex){
            System.out.println("Problemas ao listar Categoria! Erro: "+ex.getMessage());
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
