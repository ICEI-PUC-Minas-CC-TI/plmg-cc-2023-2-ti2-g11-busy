package dao;

import model.Pagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/*import java.sql.Timestamp;
import java.sql.Date;*/

public class PagamentoDAO extends DAO {	
	private int maxId = 0;
	
	public PagamentoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	
	// id int , nomeCartao string , numeroCartao string , dataValidadeCartao string , cvvCartao int , tipoPlano string
	
	public boolean insert(Pagamento pagamento) {
		boolean status = false;
		try {
			String sql = "INSERT INTO pagamento (id, nomeCartao, numeroCartao, dataValidadeCartao, cvvCartao, tipoPlano ) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setInt(1, pagamento.getId());
			preparedStatement.setString(2, pagamento.getNomeCartao());
			preparedStatement.setString(3, pagamento.getNumeroCartao());
			preparedStatement.setString(4, pagamento.getDataValidadeCartao());
			preparedStatement.setInt(5, pagamento.getCvvCartao());
			preparedStatement.setString(6, pagamento.getTipoPlano());
			preparedStatement.executeUpdate();
			
			/*String sql = "INSERT INTO pagamento (nome, email, telefone, senha) "
		             + "VALUES ('" + pagamento.getNome() + "', '" 
		             + pagamento.getEmail() + "', '" + pagamento.getTelefone() + "', '" 
		             + pagamento.getSenha() + "');";*/

			//String sql = "INSERT INTO pagamento (id, nomeCartao, numeroCartao, dataValidadeCartao, cvvCartao, tipoPlano ) VALUES ('1','Joshua Victor' ,'12345678', '12/29', 123, 'luxo');


			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	
	
	
	public Pagamento get(int id) {
		Pagamento pagamento = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pagamento WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 pagamento = new Pagamento(rs.getInt("id"), rs.getString("nomeCartao"), rs.getString("numeroCartao"), 
	                				   rs.getString("dataValidadeCartao"), rs.getInt("cvvCartao"), rs.getString("tipoPlano"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pagamento;
	}
	
	
	
	
	
	public List<Pagamento> get() {
		return get("");
	}

	
	public List<Pagamento> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Pagamento> getOrderByNomeCartao() {
		return get("nomeCartao");		
	}
	
	
	public List<Pagamento> getOrderByNumeroCartao() {
		return get("numeroCartao");		
	}
	
		public List<Pagamento> getOrderByDataValidadeCartao() {
		return get("dataValidadeCartao");		
	}
	
	
	public List<Pagamento> getOrderByCvvCartao() {
		return get("cvvCartao");		
	}
	
	
	public List<Pagamento> getOrderByTipoPlano() {
		return get("tipoPlano");		
	}
	
	
	
	
	
	
	private List<Pagamento> get(String orderBy) {
		List<Pagamento> pagamentos = new ArrayList<Pagamento>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pagamento" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Pagamento p = new Pagamento(rs.getInt("id"), rs.getString("nomeCartao"), rs.getString("numeroCartao"), 
	                				   rs.getString("dataValidadeCartao"), rs.getInt("cvvCartao"), rs.getString("tipoPlano"));
	            pagamentos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pagamentos;
	}
	
	
	
	
	
	
	public boolean update(Pagamento pagamento) {
		boolean status = false;
		try {  
			String sql = "UPDATE pagamento SET nomeCartao = '" + pagamento.getNomeCartao() + "', "
                   + "numeroCartao = '" + pagamento.getNumeroCartao() + "', "
                   + "dataValidadeCartao = '" + pagamento.getDataValidadeCartao() + "', "
                   + "cvvCartao = '" + pagamento.getCvvCartao() + "' "
                   + "tipoPlano = '" + pagamento.getTipoPlano() + "' "
                   + "WHERE id = " + pagamento.getId();
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
			st.executeUpdate("DELETE FROM pagamento WHERE id = " + id);
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


