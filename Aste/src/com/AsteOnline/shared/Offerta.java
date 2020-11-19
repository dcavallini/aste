package com.AsteOnline.shared;

import java.io.Serializable;

public class Offerta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	double importo;
	Oggetto oggetto = new Oggetto();
	Utente utente = new Utente();
	
	public Offerta() {
		
	}
	
	public Offerta(Oggetto oggetto, Utente utente, double importo) {
		this.oggetto=oggetto;
		this.utente = utente;
		this.importo=importo;
	}
	
	public Offerta(Double importo) {
		this.importo=importo;
	}
	
	public double getImporto() {
		return importo;
	}
	
	public Oggetto getOggetto() {
		return oggetto;
	}
	
	public Utente getUtente() {
		return utente;
	}

}
