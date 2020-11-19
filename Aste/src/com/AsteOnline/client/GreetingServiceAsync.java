package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	
void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	//void inserisciUtente(String username, String nome, String cognome, String cell, String password, String email, String cf, String indirizzo, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void visualizzaDatiUtente(String username, AsyncCallback<Utente> callback) throws IllegalArgumentException;
	
	void vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase, AsyncCallback<String> callback);

	void visualzzaOggetti(AsyncCallback<ArrayList<Oggetto>> asyncCallback);

	//void newUser(AsyncCallback<String> callback);
	
	void addUser(String username, String nome, String cognome, String cell, String password, String email, String cf,
			String indirizzo, AsyncCallback<String> callback);

	void richiestaAccessoUtente(String username, String password,
			AsyncCallback<Utente> asyncCallback);
	
	void inserisciOfferta(Oggetto oggetto, Utente utente, double importo, AsyncCallback<Boolean> asyncCallback);
	
	void infoUtente(String username, AsyncCallback<Utente> asyncCallback);
	
	void inviaDomanda(Oggetto oggetto, Utente utente, String domanda, AsyncCallback<Boolean> asyncCallback);
}
