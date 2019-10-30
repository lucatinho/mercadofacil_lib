package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.Marca;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MarcaDAO implements GenericDAO {
    
    private Connection conexao;
    
    public MarcaDAO() throws Exception {
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso");
        } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: " + ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object objeto) {
        Marca oMarca = (Marca) objeto;
        Boolean retorno = false;
        if (oMarca.getIdMarca() == 0) {
            retorno = this.inserir(oMarca);
        } else {
            retorno = this.alterar(oMarca);
        }
        return retorno;
    }
    
    @Override
    public Boolean inserir(Object objeto) {
        Marca oMarca = (Marca) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into marca (nomemarca) values (?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oMarca.getNomeMarca());
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Probelmas ao cadastrar a Marca! Error: " + ex.getMessage());
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
       Marca oMarca = (Marca) objeto;
       PreparedStatement stmt = null;
       String sql = "update marca set nomemarca =? where idMarca=?";
       try{
           stmt = conexao.prepareStatement(sql);
           stmt.setString(1, oMarca.getNomeMarca());
           stmt.setInt(2, oMarca.getIdMarca());
           stmt.execute();
           return true;
       }catch(Exception ex){
           System.out.println("Problemas ao alterar Marca! Erro: " +ex.getMessage());
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
        int idMarca = numero;
        PreparedStatement stmt = null;
        
        String sql = "delete from Marca where idMarca=? ";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idMarca);
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir o Marca! Erro: " + ex.getMessage());
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
        int idMarca = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        Marca oMarca = null;
        String sql="select * from marca where idMarca=?";
        
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idMarca);
            rs=stmt.executeQuery();
            while (rs.next()){
                oMarca = new Marca();
                oMarca.setIdMarca(rs.getInt("idMarca"));
                oMarca.setNomeMarca(rs.getString("nomemarca"));
            }
            return oMarca;
        }catch (Exception ex){
            System.out.println("Problemas ao carregar Marca! Erro: "+ex.getMessage());
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
        String sql = "Select * from marca order by idMarca";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()){
                Marca oMarca = new Marca();
                oMarca.setIdMarca(rs.getInt("idmarca"));
                oMarca.setNomeMarca(rs.getString("nomemarca"));
                resultado.add(oMarca);
            }
        }catch (SQLException ex){
            System.out.println("Problemas ao listar Marca! Erro: "+ex.getMessage());
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
