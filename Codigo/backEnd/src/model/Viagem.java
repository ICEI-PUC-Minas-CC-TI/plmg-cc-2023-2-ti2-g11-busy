package model;

import java.time.LocalDate;
import java.time.Month;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;

public class Viagem {
	private int id;
	private String origem;
	private String destino;
	private LocalDate partida;
	private LocalDate volta;

	public Viagem(int id, String origem, String destino, LocalDate partida, LocalDate volta)
	{
		this.id = id;
		this.origem = origem;
		this.destino = destino;
		this.partida = partida;
		this.volta = volta;
	}

	public Viagem()
	{
		id = -1;
		origem = "";
		destino = "";
		partida = null;
		volta = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public LocalDate getPartida() {
		if (partida == null) {
	        return LocalDate.of(101, Month.JANUARY, 1); 
	    } else {
	        return partida;
	    }
	}

	public void setPartida(LocalDate partida) {
		if(partida == null)
			partida = LocalDate.of(101, Month.JANUARY, 1);
		
		else
			this.partida = partida;
	}

	public LocalDate getVolta() {
		if (volta == null) {
	        return LocalDate.of(101, Month.JANUARY, 1);
	    } else {
	        return volta;
	    }
	}

	public void setVolta(LocalDate volta) {
		if(volta == null)
			volta = LocalDate.of(101, Month.JANUARY, 1);
		
		else
			this.volta = volta;
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
