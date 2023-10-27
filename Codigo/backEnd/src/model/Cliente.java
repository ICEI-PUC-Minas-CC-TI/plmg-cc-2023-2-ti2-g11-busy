package model;

/*import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;*/

public class Cliente {
	private int id;
	private String nome;
	private String email;
	private int telefone;
	private String senha;
	
	public Cliente() {
		id = -1;
		nome = "";
		email = "";
		telefone = -1;
		senha = "";
	}

	public Cliente(int id, String nome, String email, int telefone, String senha) {
		setId(id);
		setNome(nome);
		setEmail(email);
		setTelefone(telefone);
		setSenha(senha);
	}		
	
	public void setId(int id) {
		this.id = id;
	}

    public void setNome(String nome){
        this.nome = nome;
    } 

	public void setEmail(String email) {
		this.email = email;
	}

    public void setTelefone(int telefone){
        this.telefone = telefone;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public int getId() {
		return id;
	}

    public String getNome(){
        return nome;
    }

	public String getEmail() {
		return email;
	}

    public int getTelefone(){
        return telefone;
    }

    public String getSenha(){
        return senha;
    }


	/*
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Nome: " + nome + "   Email:" + email + "   Telefone.: " + telefone + "   Senha: " + senha;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Cliente) obj).getId());
	}	
}

