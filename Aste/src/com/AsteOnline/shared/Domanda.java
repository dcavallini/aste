package com.AsteOnline.shared;

import java.io.Serializable;

import com.AsteOnline.shared.Utente.Admin;
import com.AsteOnline.shared.Utente.UtenteRegistrato;

public class Domanda implements Serializable{
	private static final long serialVersionUID = 1L;
	String idDomanda;
	String titolo, contenuto;
	Oggetto oggetto = new Oggetto();
	Admin admin;
	Utente utente = new Utente();
	UtenteRegistrato utenteR;
	
	public Domanda() {
		
	}
	
	public Domanda(String idDomanda, Oggetto oggetto, Utente utente, String contenuto ) {
		this.idDomanda=idDomanda;
		this.oggetto=oggetto;
		this.utente=utente;
		this.contenuto=contenuto;
	}
	
	public Domanda(String titolo, String contenuto, Oggetto oggetto, Admin admin, UtenteRegistrato utenteR) {
		this.titolo=titolo;
		this.contenuto=contenuto;
		this.oggetto=oggetto;
		this.admin=admin;
		this.utenteR=utenteR;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public String getContenuto() {
		return contenuto;
	}
	
	public Oggetto getOggetto() {
		return oggetto;
	}
	
	public UtenteRegistrato getUtenteRegistrato() {
		return utenteR;
	}
	
	public Admin getAdmin() {
		return admin;
	}
}
