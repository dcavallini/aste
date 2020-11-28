package com.AsteOnline.shared;

import java.io.Serializable;

public class Oggetto implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	String nome, descrizione;
	Double prezzoBase;
	String idOggetto;
	Utente utente = new Utente();
	Categoria categoria = new Categoria();
	
	public Oggetto() {
		
	}
	
	public Oggetto(Utente utente, String idOggetto, String nome, String descrizione, double prezzoBase, String categoria) {
		this.utente = utente;
		this.idOggetto=idOggetto;
		this.nome = nome;
		this.descrizione=descrizione;
		this.prezzoBase=prezzoBase;
		this.categoria.setCategoria(categoria);
	}
	
	public Oggetto(String idOggetto, String nome, String descrizione, double prezzoBase) {
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
	
	public Categoria getCategoria() {
		
		return categoria;
		
	}
	
	public void setPrezzoBase(double prezzo) {
		this.prezzoBase = prezzo;
	}
	
}
