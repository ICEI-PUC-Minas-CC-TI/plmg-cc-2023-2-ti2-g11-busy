package dao;

import model.Viagem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.beans.Statement;
import java.sql.Date;

public class ViagemDAO extends DAO {	
	
	public ViagemDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	

	public void insert(Viagem viagem) {
		// Inserir dados do formulário no banco de dados
		String sql = "INSERT INTO viagem (origem, destino, partida, volta) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, viagem.getOrigem());
            stmt.setString(2, viagem.getDestino());
            stmt.setDate(3, Date.valueOf(viagem.getPartida()));
            stmt.setDate(4, Date.valueOf(viagem.getVolta()));
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("INSERIDO VIAGEM COM SUCESSO");
		
	}
	
	public Viagem get(int id) {
	    Viagem viagem = null;

	    try {
	        java.sql.Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        String sql = "SELECT * FROM viagem WHERE id=" + id;
	        ResultSet rs = st.executeQuery(sql);
	        if (rs.next()) {
	            viagem = new Viagem(
	                    rs.getInt("id"),
	                    rs.getString("origem"),
	                    rs.getString("destino"),
	                    rs.getDate("partida").toLocalDate(),
	                    rs.getDate("volta").toLocalDate()
	            );
	        }
	        st.close();
	    } catch (Exception e) {
	        System.err.println(e.getMessage());
	    }
	    
	    System.out.println("EXIBIDO VIAGEM COM SUCESSO");
	    return viagem;
	}

	
	public boolean update(Viagem viagem) {
		boolean status = false;
		try {  
			String sql = "UPDATE viagem SET origem = '" + viagem.getOrigem() + "', "
                   + "email = '" + viagem.getDestino() + "', "
                   + "telefone = '" + viagem.getPartida() + "', "
                   + "senha = '" + viagem.getVolta() + "' "
                   + "WHERE id = " + viagem.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
		    
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		System.out.println("UPDATE VIAGEM COM SUCESSO");
		return status;
	}
	
	public boolean delete(int id) {
	    boolean status = false;
	    try {
	        java.sql.Statement st = conexao.createStatement();
	        st.executeUpdate("DELETE FROM viagem WHERE id = " + id);
	        st.close();
	        status = true;
	    } catch (SQLException u) {
	        throw new RuntimeException(u);
	    }
	    
	    System.out.println("DELETADO VIAGEM COM SUCESSO");
	    return status;
	}

	

}    		//FIM DA FUNCAO VIAGEMDAO

