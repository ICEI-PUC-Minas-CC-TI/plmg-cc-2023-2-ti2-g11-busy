package model;

import java.time.LocalDate;
/*import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;*/

public class Viagem {
	private int id;
	private String origem;
	private String destino;
	private LocalDate partida;
	private LocalDate volta;
	
	public Viagem() {
		id = -1;
		origem = "";
		destino = "";
		partida = LocalDate.of(0, 1, 1); // 0000-01-01
        volta = LocalDate.of(0, 1, 1); 
	}

	public Viagem(int id, String origem, String destino, LocalDate partida, LocalDate volta) {
		setId(id);
		setOrigem(origem);
		setDestino(destino);
		setPartida(partida);
		setVolta(volta);
	}		
	
	public void setId(int id) {
		this.id = id;
	}

    public void setOrigem(String origem){
        this.origem = origem;
    } 

	public void setDestino(String destino) {
		this.destino = destino;
	}

    public void setPartida(LocalDate partida){
        this.partida = partida;
    }

    public void setVolta(LocalDate volta){
        this.volta = volta;
    }

    public int getId() {
		return id;
	}

    public String getOrigem(){
        return origem;
    }

	public String getDestino() {
		return destino;
	}

    public LocalDate getPartida(){
        return partida;
    }

    public LocalDate getVolta(){
        return volta;
    }


	/*
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Origem: " + origem + "   Destino:" + destino + "   Partida: " + partida + "   Volta: " + volta;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId() == ((Viagem) obj).getId());
	}	
}

