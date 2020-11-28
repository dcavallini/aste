package com.AsteOnline.shared;

import java.io.Serializable;

public class Utente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	String username, nome, cognome, cell, password, email, cod_fiscale, indirizzo, sesso, dataNascita, luogoNascita;
	//Oggetto oggetto = new Oggetto();
	
	public Utente() {
		
	}
/*
	public Utente(Oggetto oggetto, String username, String nome, String cognome, String cell, String password, String email, String cod_fiscale, String indirizzo){
		this.oggetto=oggetto;
		this.username=username;
		this.nome=nome;
		this.cognome=cognome;
		this.cell=cell;
		this.password=password;
		this.email=email;
		this.cod_fiscale=cod_fiscale;
		this.indirizzo=indirizzo;
	}
	*/
	
	
	public Utente(String username, String nome, String cognome, String cell, String password, String email, String cod_fiscale, String indirizzo){
	
		this.username=username;
		this.nome=nome;
		this.cognome=cognome;
		this.cell=cell;
		this.password=password;
		this.email=email;
		this.cod_fiscale=cod_fiscale;
		this.indirizzo=indirizzo;
	}
	
	/*
	public Oggetto getOggetto() {
		return oggetto;
	}
	*/
	
	public String getUsername() {
		return username;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public String getCell() {
		return cell;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getCod_fiscale() {
		return cod_fiscale;
	}
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	
	public class Admin extends Utente{
		public Admin(String username, String password) {
			this.username=username; 
			this.password=password;
		}

		public Admin() {
			// TODO Auto-generated constructor stub
		}
	}
	
	public class UtenteRegistrato extends Utente{
		public UtenteRegistrato() {
			
		}
		public UtenteRegistrato(String username, String password) {
			this.username=username;
			this.password=password;
		}
	}
	
public class UtenteNonRegistrato extends Utente{
		public UtenteNonRegistrato() {
			
		}
	}
	
}
