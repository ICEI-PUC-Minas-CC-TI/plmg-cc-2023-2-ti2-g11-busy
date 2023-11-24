package dao;

import model.Pagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.beans.Statement;

public class PagamentoDAO extends DAO {	
	
	public PagamentoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	

	public void insert(Pagamento pagamento) {
		// Inserir dados do formulário no banco de dados
		String sql = "INSERT INTO pagamento (nomecartao, numerocartao, datavalidadecartao, cvvcartao, tipoplano) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, pagamento.getNomeCartao());
            stmt.setString(2, pagamento.getNumeroCartao());
            stmt.setString(3, pagamento.getDataValidade());
            stmt.setString(4, pagamento.getCvvCartao());
            stmt.setString(5, pagamento.getTipoPlano());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("INSERIDO PAGAMENTO COM SUCESSO");
		
	}
	
	public Pagamento get(int id) {
		Pagamento pagamento = null;
		
		try {
			java.sql.Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pagamento WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	pagamento = new Pagamento(rs.getInt("id"), rs.getString("nomecartao"), rs.getString("numerocartao"), 
	        			rs.getString("datavalidadecartao"), rs.getString("cvvcartao"), rs.getString("tipoPlano"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		System.out.println("EXIBIDO PAGAMENTO COM SUCESSO");
		return pagamento;
	}
	
	public boolean update(Pagamento pagamento) {
	    boolean status = false;
	    try {
	        String sql = "UPDATE pagamento SET nomecartao = ?, "
	                + "numerocartao = ?, "
	                + "datavalidadecartao = ?, "
	                + "cvvcartao = ?, "
	                + "tipoplano = ? "
	                + "WHERE id = ?";
	        
	        try (PreparedStatement st = conexao.prepareStatement(sql)) {
	            st.setString(1, pagamento.getNomeCartao());
	            st.setString(2, pagamento.getNumeroCartao());
	            st.setString(3, pagamento.getDataValidade());
	            st.setString(4, pagamento.getCvvCartao());
	            st.setString(5, pagamento.getTipoPlano());
	            st.setInt(6, pagamento.getId());

	            int rowsUpdated = st.executeUpdate();

	            if (rowsUpdated > 0) {
	                status = true;
	            }
	        }
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    
	    System.out.println("UPDATE PAGAMENTO COM SUCESSO");
	    return status;
	}
	
	public boolean delete(int id) {
	    boolean status = false;
	    try {
	        java.sql.Statement st = conexao.createStatement();
	        st.executeUpdate("DELETE FROM pagamento WHERE id = " + id);
	        st.close();
	        status = true;
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    
	    System.out.println("DELETE PAGAMENTO COM SUCESSO");
	    return status;
	}
	

}    		//FIM DA FUNCAO VIAGEMDAO

