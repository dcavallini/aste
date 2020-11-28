package com.AsteOnline.shared;

import java.io.Serializable;

public class Asta implements Serializable{
	private static final long serialVersionUID = 1L;

	String scadenza;
	boolean esito;
	Utente utenteVenditore = new Utente();
	Utente utenteOfferente = new Utente();
	Oggetto oggetto = new Oggetto();
	
	public Asta() {
	}
	
	public Asta(Utente utenteVenditore, Oggetto oggetto, String scadenza, boolean esito) {
		this.utenteVenditore=utenteVenditore;
		this.scadenza=scadenza;
		this.esito = esito;
		this.oggetto = oggetto;
	}
	public Utente getOfferente() {
		return utenteOfferente;
	}
	public Utente getVenditore() {
		return utenteVenditore;
	}
	public Oggetto getOggetto() {
		return oggetto;
	}
	public String getScadenza() {
		return scadenza;
	}
	public boolean getEsito() {
		return esito;
	}
}