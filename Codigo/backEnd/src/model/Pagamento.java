package model;

/*import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;*/

public class Pagamento {
	private int id;
	private String nomeCartao;
	private String numeroCartao;
	private String dataValidadeCartao;
	private int cvvCartao;
	private String tipoPlano;

	public Pagamento() {
		id = -1;
		nomeCartao = "";
		numeroCartao = "";
		dataValidadeCartao = "";
		cvvCartao = -1;
		tipoPlano = "";
	}

	public Pagamento(int id, String nomeCartao, String numeroCartao, String dataValidadeCartao, int cvvCartao, String tipoPlano) {
		setId(id);
		setNomeCartao(nomeCartao);
		setNumeroCartao(numeroCartao);
		setDataValidadeCartao(dataValidadeCartao);
		setCvvCartao(cvvCartao);
		setTipoPlano(tipoPlano);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNomeCartao(String nomeCartao) {
		this.nomeCartao = nomeCartao;
	}

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public void setDataValidadeCartao(String dataValidadeCartao) {
		this.dataValidadeCartao = dataValidadeCartao;
	}

	public void setCvvCartao(int cvvCartao) {
		this.cvvCartao = cvvCartao;
	}
	
	public void setTipoPlano(String tipoPlano) {
		this.tipoPlano = tipoPlano;
	}
	
	
///////////////////////////////////////////////////////////////////////
	

	public int getId() {
		return id;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getDataValidadeCartao() {
		return dataValidadeCartao;
	}

	public int getCvvCartao() {
		return cvvCartao;
	}
	
		public String getTipoPlano() {
		return tipoPlano;
	}

	/*
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Nome do Cartao: " + nomeCartao + "   numero do cartao" + numeroCartao + "   tipo do plano: " + tipoPlano;
	}

	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Pagamento) obj).getId());
	}
}
