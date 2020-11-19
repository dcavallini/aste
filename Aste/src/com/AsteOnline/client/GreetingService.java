package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	//String inserisciUtente(String username, String nome, String cognome, String cell, String password, String email, String cf, String indirizzo) throws IllegalArgumentException;
	
	Utente visualizzaDatiUtente(String username) throws IllegalArgumentException;

	String vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase); // String dataFineAsta, String venditore, String categoria,

	ArrayList<Oggetto> visualzzaOggetti() throws IllegalArgumentException;

	String addUser(String username, String nome, String cognome, String cell, String password, String email, String cf, String indirizzo);

	Utente richiestaAccessoUtente(String username, String password);
	
	boolean inserisciOfferta(Oggetto oggetto, Utente utente, double importo);
	
	Utente infoUtente(String username);
	
	boolean inviaDomanda(Oggetto oggetto, Utente utente, String domanda);
	
}
