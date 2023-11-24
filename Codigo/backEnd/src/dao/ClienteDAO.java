package dao;

import model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*import java.sql.Timestamp;
import java.sql.Date;*/

public class ClienteDAO extends DAO {	
	
	public ClienteDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	

	public void insert(Cliente cliente) {
		// Inserir dados do formulário no banco de dados
		String sql = "INSERT INTO cliente (nome, email, telefone, senha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getSenha());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("INSERIDO CLIENTE COM SUCESSO");
		
	}
	
	public Cliente get(int id) {
		Cliente cliente = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), 
	                				   rs.getString("telefone"), rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("EXIBIDO CLIENTE COM SUCESSO");
		
		return cliente;
	}
	
	public Cliente getByEmail(String email) {
	    Cliente cliente = null;
	    
	    try {
	        Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        String sql = "SELECT * FROM cliente WHERE email='" + email + "'";
	        ResultSet rs = st.executeQuery(sql);
	        if (rs.next()) {
	            cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"),
	                    rs.getString("telefone"), rs.getString("senha"));
	        }
	        st.close();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    
	    System.out.println("EXIBIDO CLIENTE COM SUCESSO");
	    
	    return cliente;
	}

	
	public boolean update(Cliente cliente) {
	    boolean status = false;
	    try {
	        String sql = "UPDATE cliente SET nome = ?, "
	                + "email = ?, "
	                + "telefone = ?, "
	                + "senha = ? "
	                + "WHERE id = ?";
	        
	        try (PreparedStatement st = conexao.prepareStatement(sql)) {
	            st.setString(1, cliente.getNome());
	            st.setString(2, cliente.getEmail());
	            st.setString(3, cliente.getTelefone());
	            st.setString(4, cliente.getSenha());
	            st.setInt(5, cliente.getId());

	            int rowsUpdated = st.executeUpdate();

	            if (rowsUpdated > 0) {
	                status = true;
	            }
	        }
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    
	    System.out.println("UPDATE CLIENTE COM SUCESSO");
	    return status;
	}
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM cliente WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		System.out.println("DELETADO CLIENTE COM SUCESSO");
		return status;
	}
	

}    		//FIM DA FUNCAO CLIENTEDAO

