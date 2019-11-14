package br.com.fatec.mercado_lib.dao;



import br.com.fatec.mercado_lib.model.Cidade;
import br.com.fatec.mercado_lib.model.Cliente;
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
            Cliente oCliente = (Cliente) objeto;
            
            if (oCliente.getIdCliente()==0) {
                //verifica se já existe pessoa com este CPF cadastrada.
                int idCliente = this.verificarCpf(oCliente.getCpf());
                if (idCliente==0) {
                    //se não encontrou insere
                    retorno = this.inserir(oCliente);
                }else{
                    //se encontrou cliente com o cpf altera
                    oCliente.setIdCliente(idCliente);
                    retorno = this.alterar(oCliente);
                }
            } else {
              retorno = this.alterar(oCliente);
            }
            
        } catch (Exception ex){
            System.out.println("Problemas ao incluir cliente! Erro "+ex.getMessage());            
        }
        return retorno;
    }

    @Override
    public Boolean inserir(Object objeto) {
        Cliente oCliente = (Cliente) objeto;
        PreparedStatement stmt = null;
        String sql = "insert into cliente (idPessoa, rua, situacao) values (?, ?, ?)";
        
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            int idPessoa = oPessoaDAO.cadastrar(oCliente);
            
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idPessoa);
            stmt.setString(2, oCliente.getRua());
            stmt.setString(3, "A");
            stmt.execute();
            
            return true;
        }catch(Exception ex){
            System.out.println("Problemas ao incluir cliente! Erro "+ex.getMessage());
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
        Cliente oCliente = (Cliente) objeto;
        PreparedStatement stmt = null;
        String sql = "update cliente set rua=? where idCliente=?";
        
        try{
            PessoaDAO oPessoaDAO = new PessoaDAO();
            oPessoaDAO.cadastrar(oCliente);
            
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, oCliente.getRua());
            stmt.setInt(2, oCliente.getIdCliente());
            stmt.execute();
            
            return true;
        }catch(Exception ex){
            System.out.println("Problemas ao alterar cliente! Erro: "+ex.getMessage());
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
            //carrega dados de cliente
            ClienteDAO oClienteDAO = new ClienteDAO();
            Cliente oCliente = (Cliente) oClienteDAO.carregar(id);
            
            //verifica e troca a situação do cliente
            String situacao="A";
            if(oCliente.getSituacao().equals(situacao)){
                situacao = "I";
            }else{
                situacao = "A";
            }
            String sql = "update cliente set situacao=? where idCliente=?";
            
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, situacao);
            stmt.setInt(2, oCliente.getIdCliente());
            stmt.execute();
            return true;
        }catch (Exception ex){
            System.out.println("Problemas ao ativar/desativar cliente! Erro: "+ex.getMessage());
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
        int idCliente = numero;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente oCliente = null;
        String sql = "Select * from cliente c, pessoa p "
                + "where c.idpessoa = p.idpessoa and c.idcliente=?";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setInt(1, idCliente);
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
            
                oCliente = new Cliente(rs.getInt("idcliente"),
                                       rs.getString("rua"),
                                       rs.getString("situacao"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpf"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade);
            }
            return oCliente;
        }catch(SQLException ex){
            System.out.println("Problemas ao carregar cliente! Erro "+ex.getMessage());
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
        
        String sql= "Select p.*, c.idcliente, c.rua, c.situacao "
                + "from cliente c, pessoa p "
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
                   System.out.println("Problemas ao carregar usuario!"
                       + "Erro:"+ex.getMessage());
                }
                
                Cliente oCliente = new Cliente(rs.getInt("idcliente"),
                                       rs.getString("rua"),
                                       rs.getString("situacao"),
                                       rs.getInt("idpessoa"),
                                       rs.getString("cpf"),
                                       rs.getString("nome"),
                                       rs.getDate("datanascimento"),
                                       oCidade);
                resultado.add(oCliente);
            }
        }catch(SQLException ex){
            System.out.println("Problemas ao listar cliente! Erro "+ex.getMessage());
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
        int idCliente = 0;
        String sql = "Select c.* from cliente c, pessoa p "
                + "where c.idpessoa = p.idPessoa and p.cpf=?;";
        
        try{
            stmt=conexao.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs=stmt.executeQuery();
            
            while(rs.next()){
                idCliente = rs.getInt("idcliente");
            }
            return idCliente;
        }catch(SQLException ex){
            System.out.println("Problemas ai carregar pessoa! Erro: "+ex.getMessage());
            return idCliente;
        }
    }      
}
