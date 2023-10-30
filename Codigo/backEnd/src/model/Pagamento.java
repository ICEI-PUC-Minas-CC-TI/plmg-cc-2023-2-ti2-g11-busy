package model;


public class Pagamento {
	private int id;
	private String nomeCartao;
	private String numeroCartao;
	private String dataValidade;
	private String cvvCartao;
	private String tipoPlano;
	
	public Pagamento() {
		id = -1;
		nomeCartao = "";
		numeroCartao = "";
		dataValidade = "";
		cvvCartao = "";
		tipoPlano = "";
	}

	public Pagamento(int id, String nomeCartao, String numeroCartao, String dataValidade, String cvvCartao, String tipoPlano) {
		setId(id);
		setNomeCartao(nomeCartao);
		setNumeroCartao(numeroCartao);
		setDataValidade(dataValidade);
		setCvvCartao(cvvCartao);
		setTipoPlano(tipoPlano);
	}		
	
	public void setId(int id) {
		this.id = id;
	}

    public void setNomeCartao(String nomeCartao){
        this.nomeCartao = nomeCartao;
    } 

	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

    public void setDataValidade(String dataValidade){
        this.dataValidade = dataValidade;
    }

    public void setCvvCartao(String cvvCartao){
        this.cvvCartao = cvvCartao;
    }
    
    public void setTipoPlano(String tipoPlano){
        this.tipoPlano = tipoPlano;
    }

    public int getId() {
		return id;
	}

    public String getNomeCartao(){
        return nomeCartao;
    }

	public String getNumeroCartao() {
		return numeroCartao;
	}

    public String getDataValidade(){
        return dataValidade;
    }

    public String getCvvCartao(){
        return cvvCartao;
    }
    
    public String getTipoPlano(){
        return tipoPlano;
    }


	/*
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Nome Cartao: " + nomeCartao + "   Numero Cartao:" + numeroCartao + "   Data Validade: " + dataValidade + "   CVV Cartao: " + cvvCartao + "   Tipo Plano: " + tipoPlano;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Pagamento) obj).getId());
	}	
}

