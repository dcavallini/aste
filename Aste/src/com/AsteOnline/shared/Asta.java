package com.AsteOnline.shared;

import java.io.Serializable;

public class Asta implements Serializable{
	private static final long serialVersionUID = 1L;

	String scadenza;
	boolean esito;
	Utente utente = new Utente();
	
	public Asta() {
		
	}
	
	public Asta(Utente utente, String scadenza, boolean esito) {
		this.utente=utente;
		this.scadenza=scadenza;
		this.esito = esito;
	}
	
	public Utente getUtente() {
		return utente;
	}
	
	public String getScadenza() {
		return scadenza;
	}
	
	public boolean getEsito() {
		return esito;
	}
}
