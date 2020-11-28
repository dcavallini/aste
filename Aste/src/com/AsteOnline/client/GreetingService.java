package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Offerta;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Risposta;
import com.AsteOnline.shared.Utente;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	
	Utente visualizzaDatiUtente(String username) throws IllegalArgumentException;

	ArrayList<Oggetto> visualzzaOggetti() throws IllegalArgumentException;

	String addUser(String username, String nome, String cognome, String cell, String password, String email, String cf, String indirizzo);

	Utente richiestaAccessoUtente(String username, String password);
	
	boolean inserisciOfferta(Oggetto oggetto, Utente utente, double importo);
	
	Utente infoUtente(String username);
	
	boolean inviaDomanda(Oggetto oggetto, Utente utente, String domanda);

	ArrayList<Categoria> inizializzazioneCategorie() throws IllegalArgumentException;
	
	ArrayList<Oggetto> filtroPerCategoria(Categoria c);
    
	ArrayList<Oggetto> oggettiVenduti(Utente utente);
	
	Oggetto vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase, String scadenza, String categoria);

	String statoOggetto(Oggetto oggetto);

	ArrayList<Domanda> viewDomanda(String idOggetto);
	
	ArrayList<Risposta> viewRisposta(String idDomanda);
	
	void inviaRisposta(Domanda domanda, String contenuto);
	
	ArrayList<Offerta> viewOfferte();
	
	boolean login (String username, String password);
	
	boolean addSottoCategoria(Categoria nomeCategoriaMadre, String nomeCategoriaFiglia, int profondita);
	
	boolean addCategoria(String nomeCategoriaMadre);

	boolean eliminaOggetto(Oggetto oggetto);
	
	boolean eliminaOfferta(Offerta offerta);
	
	boolean rinominaCategoria(String vecchioNome, String nuovoNome, int profondita);

	boolean eliminaCategoria(Categoria categoria);

	boolean eliminaDomanda(Domanda domanda);

	boolean eliminaRisposta(Risposta risposta);
	
	Oggetto infoOggetto(String idOggetto);
	
}
