package dao;

import model.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/*import java.sql.Timestamp;
import java.sql.Date;*/

public class ClienteDAO extends DAO {	
	private int maxId = 0;
	
	public ClienteDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	
	
	
	public boolean insert(Cliente cliente) {
		boolean status = false;
		try {
			String sql = "INSERT INTO cliente (nome, email, telefone, senha) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, cliente.getNome());
			preparedStatement.setString(2, cliente.getEmail());
			preparedStatement.setInt(3, cliente.getTelefone());
			preparedStatement.setString(4, cliente.getSenha());
			preparedStatement.executeUpdate();
			
			/*String sql = "INSERT INTO cliente (nome, email, telefone, senha) "
		             + "VALUES ('" + cliente.getNome() + "', '" 
		             + cliente.getEmail() + "', '" + cliente.getTelefone() + "', '" 
		             + cliente.getSenha() + "');";*/

			//String sql = "INSERT INTO cliente (nome, email, telefone, senha) VALUES ('sss', 'arthur@gmail.com', 1234, 'senha')";


			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	
	
	
	public Cliente get(int id) {
		Cliente cliente = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 cliente = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), 
	                				   rs.getInt("telefone"), rs.getString("senha"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return cliente;
	}
	
	
	
	
	
	public List<Cliente> get() {
		return get("");
	}

	
	public List<Cliente> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Cliente> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Cliente> getOrderByEmail() {
		return get("email");		
	}
	
	
	
	
	
	
	private List<Cliente> get(String orderBy) {
		List<Cliente> clientes = new ArrayList<Cliente>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM cliente" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Cliente p = new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("email"), 
	                				   rs.getInt("telefone"), rs.getString("senha"));
	            clientes.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return clientes;
	}
	
	
	
	
	
	
	public boolean update(Cliente cliente) {
		boolean status = false;
		try {  
			String sql = "UPDATE cliente SET nome = '" + cliente.getNome() + "', "
                   + "email = '" + cliente.getEmail() + "', "
                   + "telefone = '" + cliente.getTelefone() + "', "
                   + "senha = '" + cliente.getSenha() + "' "
                   + "WHERE id = " + cliente.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
		    
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
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
		return status;
	}
	
	
	
	
	
	
	public int getMaxId() {
		return maxId;
    }
}


