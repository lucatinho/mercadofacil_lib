package br.com.fatec.mercado_lib.dao;


import br.com.fatec.mercado_lib.model.StatusPedido;
import br.com.fatec.mercado_lib.utils.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StatusPedidoDAO implements GenericDAO {
    
    private Connection conexao;
    
    public StatusPedidoDAO() throws Exception {
        try {
            this.conexao = ConnectionFactory.getConnection();
            System.out.println("Conectado com Sucesso");
        } catch (Exception ex) {
            System.out.println("Problemas ao conectar no BD! Erro: " + ex.getMessage());
        }
    }
    
    @Override
    public Boolean cadastrar(Object objeto) {
        StatusPedido oStatusPedido = (StatusPedido) objeto;
        Boolean retorno = false;
        if (oStatusPedido.getIdStatusPedido() == 0) {
            retorno = this.inserir(oStatusPedido);
        } else {
            retorno = this.alterar(oStatusPedido);
        }
        return retorno;
    }
    
    @Override
    public Boolean inserir(Object objeto) {
        StatusPedido oStatusPedido = (StatusPedido) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into statusPedido (nomestatusPedido) values (?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oStatusPedido.getNomeStatusPedido());
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Probelmas ao cadastrar a StatusPedido! Error: " + ex.getMessage());
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
       StatusPedido oStatusPedido = (StatusPedido) objeto;
       PreparedStatement stmt = null;
       String sql = "update statusPedido set nomestatusPedido =? where idStatusPedido=?";
       try{
           stmt = conexao.prepareStatement(sql);
           stmt.setString(1, oStatusPedido.getNomeStatusPedido());
           stmt.setInt(2, oStatusPedido.getIdStatusPedido());
           stmt.execute();
           return true;
       }catch(Exception ex){
           System.out.println("Problemas ao alterar StatusPedido! Erro: " +ex.getMessage());
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
        int idStatusPedido = numero;
        PreparedStatement stmt = null;
        
        String sql = "delete from StatusPedido where idStatusPedido=? ";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idStatusPedido);
            stmt.execute();
            return true;
        } catch (Exception ex) {
            System.out.println("Problemas ao excluir o StatusPedido! Erro: " + ex.getMessage());
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
        int idStatusPedido = numero;
        PreparedStatement stmt = null;
        ResultSet rs= null;
        StatusPedido oStatusPedido = null;
        String sql="select * from statusPedido where idStatusPedido=?";
        
        try{
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idStatusPedido);
            rs=stmt.executeQuery();
            while (rs.next()){
                oStatusPedido = new StatusPedido();
                oStatusPedido.setIdStatusPedido(rs.getInt("idStatusPedido"));
                oStatusPedido.setNomeStatusPedido(rs.getString("nomestatusPedido"));
            }
            return oStatusPedido;
        }catch (Exception ex){
            System.out.println("Problemas ao carregar StatusPedido! Erro: "+ex.getMessage());
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
        String sql = "Select * from statusPedido order by idStatusPedido";
        try{
            stmt = conexao.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()){
                StatusPedido oStatusPedido = new StatusPedido();
                oStatusPedido.setIdStatusPedido(rs.getInt("idstatusPedido"));
                oStatusPedido.setNomeStatusPedido(rs.getString("nomestatusPedido"));
                resultado.add(oStatusPedido);
            }
        }catch (SQLException ex){
            System.out.println("Problemas ao listar StatusPedido! Erro: "+ex.getMessage());
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
