package com.AsteOnline.shared;

import java.io.Serializable;

import com.AsteOnline.shared.Utente.Admin;
import com.AsteOnline.shared.Utente.UtenteRegistrato;

public class Risposta implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String titolo, contenuto, idDomanda;
	Oggetto oggetto= new Oggetto();
//	Admin admin;
//	UtenteRegistrato utenteR;
	Utente utente = new Utente();
	Domanda domanda = new Domanda();
	String idRisposta;
	
	public Risposta() {
		
	}
	
	/*
	public Risposta(String idRisposta, Domanda domanda, String contenuto, Oggetto oggetto, Utente utente) {
		this.idRisposta=idRisposta;
		this.domanda=domanda;
		this.contenuto=contenuto;
		this.oggetto=oggetto;
		this.utente=utente;
	}
	*/
	//, Admin admin, UtenteRegistrato utenteR
	public Risposta(String id,Domanda domanda, String contenuto, Utente utente) {
		this.idRisposta = id;
		this.domanda=domanda;
		this.contenuto=contenuto;
		this.utente = utente;
	}
	
	public String getIdRisposta() {
		return idRisposta;
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
	
	public Utente getUtente() {
		return utente;
	}
	
	public Domanda getDomanda() {
		return domanda;
	}
	
//	public UtenteRegistrato getUtenteRegistrato() {
//		return utenteR;
//	}
//	
//	public Admin getAdmin() {
//		return admin;
//	}

}
