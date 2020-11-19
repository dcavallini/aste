package com.AsteOnline.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.AsteOnline.shared.Utente.Admin;
import com.AsteOnline.shared.Utente.UtenteRegistrato;

public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String nome;
	ArrayList<String> sottocategoria;
	UtenteRegistrato utenteR;
	Admin admin;
	Oggetto oggetto;
	
	public Categoria() {
		
	}
	
	public Categoria(UtenteRegistrato utenteR, Admin admin, Oggetto oggetto, String nome, ArrayList<String> sottocategoria) {
		this.utenteR=utenteR;
		this.admin = admin;
		this.oggetto = oggetto;
		this.nome=nome;
		this.sottocategoria=sottocategoria;
	}
	
	public UtenteRegistrato getUtenteRegistrato() {
		return utenteR;
	}
	
	public Admin getAdmin() {
		return admin;
	}
	
	public Oggetto getOggetto() {
		return oggetto;
	}
	
	public String getNome() {
		return nome;
	}
	
	public ArrayList<String> getSottocategoria(){
		return sottocategoria;
	}

}
