package com.AsteOnline.shared;

import java.io.Serializable;

public class Oggetto implements Serializable {
	private static final long serialVersionUID = 1L;

	String nome, descrizione;
	Double prezzoBase;
	String idOggetto;
	Utente utente = new Utente();
	
	public Oggetto() {
		
	}
	
	public Oggetto(Utente utente, String idOggetto, String nome, String descrizione, Double prezzoBase) {
		this.utente = utente;
		this.idOggetto=idOggetto;
		this.nome = nome;
		this.descrizione=descrizione;
		this.prezzoBase=prezzoBase;
	}
	
	
	public Oggetto(String idOggetto, String nome, String descrizione, Double prezzoBase) {
		this.idOggetto=idOggetto;
		this.nome = nome;
		this.descrizione=descrizione;
		this.prezzoBase=prezzoBase;
	}
	
	
	public Utente getUtente()
	{
		return utente;
	}
	
	
	public String getIdOggetto() {
		return idOggetto;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public double getPrezzoBase() {
		return prezzoBase;
	}
	
}
