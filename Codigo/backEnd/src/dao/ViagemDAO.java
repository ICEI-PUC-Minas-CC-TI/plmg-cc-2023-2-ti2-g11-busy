package dao;

import model.Viagem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class ViagemDAO extends DAO {
	private int maxId = 0;
	
	public ViagemDAO()
	{
		super();
		conectar();
	}

	public void finalize()
	{
		close();
	}
	
	public boolean insert(Viagem viagem)
	{
		boolean status = false;
		try {
			String sql = "INSERT INTO viagem (origem, destino, partida, volta) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = conexao.prepareStatement(sql);
			preparedStatement.setString(1, viagem.getOrigem());
			preparedStatement.setString(2, viagem.getDestino());
			preparedStatement.setDate(3, Date.valueOf(viagem.getPartida()));
		    preparedStatement.setDate(4, Date.valueOf(viagem.getVolta()));
			preparedStatement.executeUpdate();

			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}


	public Viagem get(int id)
	{
		Viagem viagem = null;
		
		try
		{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM viagem WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 viagem = new Viagem(rs.getInt("id"), rs.getString("origem"), rs.getString("destino"),
	                				   rs.getDate("partida").toLocalDate(), rs.getDate("volta").toLocalDate());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return viagem;
	}

	public List<Viagem> get() {
		return get("");
	}

	
	public List<Viagem> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Viagem> getOrderByOrigem() {
		return get("origem");
	}
	
	
	public List<Viagem> getOrderByDestino() {
		return get("destino");
	}


	private List<Viagem> get(String orderBy)
	{
		List<Viagem> viagens = new ArrayList<Viagem>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM viagem" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Viagem p = new Viagem(rs.getInt("id"), rs.getString("origem"), rs.getString("destino"),
	                				   rs.getDate("partida").toLocalDate(), rs.getDate("volta").toLocalDate());
	            viagens.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return viagens;
	}
	

	public boolean update(Viagem viagem)
	{
		boolean status = false;
		try {  
			String sql = "UPDATE viagem SET origem = '" + viagem.getOrigem() + "', "
                   + "destino = '" + viagem.getDestino() + "', "
                   + "partida = '" + viagem.getPartida() + "' "
                   + "volta = " + viagem.getVolta() + "' "
			       + "WHERE id = " + viagem.getId();
			PreparedStatement st = conexao.prepareStatement(sql);
		    
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public boolean delete(int id)
	{
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM viagem WHERE id = " + id);
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


