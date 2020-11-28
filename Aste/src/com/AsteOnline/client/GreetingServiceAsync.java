package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Offerta;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Risposta;
import com.AsteOnline.shared.Utente;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	
	void visualizzaDatiUtente(String username, AsyncCallback<Utente> callback) throws IllegalArgumentException;
	
	void vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase, String scadenza,
			String categoria, AsyncCallback<Oggetto> callback);

	void visualzzaOggetti(AsyncCallback<ArrayList<Oggetto>> asyncCallback);
	
	void addUser(String username, String nome, String cognome, String cell, String password, String email, String cf,
			String indirizzo, AsyncCallback<String> callback);

	void richiestaAccessoUtente(String username, String password,
			AsyncCallback<Utente> asyncCallback);
	
	void inserisciOfferta(Oggetto oggetto, Utente utente, double importo, AsyncCallback<Boolean> asyncCallback);
	
	void infoUtente(String username, AsyncCallback<Utente> asyncCallback);
	
	void inviaDomanda(Oggetto oggetto, Utente utente, String domanda, AsyncCallback<Boolean> asyncCallback);

	void inizializzazioneCategorie(AsyncCallback<ArrayList<Categoria>> callback) throws IllegalArgumentException;

	void filtroPerCategoria(Categoria c, AsyncCallback<ArrayList<Oggetto>> callback);

	void oggettiVenduti(Utente utente, AsyncCallback<ArrayList<Oggetto>> callback);

	void statoOggetto(Oggetto oggetto, AsyncCallback<String> callback);

	void viewDomanda(String idOggetto, AsyncCallback<ArrayList<Domanda>> asyncCallback);

	void viewRisposta(String idDomanda, AsyncCallback<ArrayList<Risposta>> asyncCallback);
	
	void login(String username, String password, AsyncCallback <Boolean> callback);
	
	void addSottoCategoria(Categoria nomeCategoriaMadre, String nomeCategoriaFiglia, int profondita, AsyncCallback <Boolean> callback);
	
	void addCategoria(String nomeCategoriaMadre, AsyncCallback<Boolean> callback);
	
	void eliminaOggetto(Oggetto oggetto, AsyncCallback<Boolean> callback);

	void viewOfferte(AsyncCallback<ArrayList<Offerta>> callback);

	void eliminaOfferta(Offerta offerta, AsyncCallback<Boolean> callback);
	
	void eliminaCategoria(Categoria categoria, AsyncCallback<Boolean> callback);

	void rinominaCategoria(String vecchioNome, String nuovoNome, int profondita, AsyncCallback<Boolean> asyncCallback);
	
	void eliminaDomanda(Domanda domanda, AsyncCallback<Boolean> asyncCallback);
	
	void eliminaRisposta(Risposta risposta, AsyncCallback<Boolean> ascynCallback);
	
	void infoOggetto(String idOggetto, AsyncCallback<Oggetto> ascynCallback);
	
	void inviaRisposta(Domanda domanda, String contenuto, AsyncCallback<Void> callback);
	
}
