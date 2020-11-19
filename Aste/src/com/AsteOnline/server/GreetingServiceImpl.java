package com.AsteOnline.server;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import com.AsteOnline.client.GreetingService;
import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.FieldVerifier;
import com.AsteOnline.shared.Offerta;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	//creo db per l'utente
	private DB getDBUtenti() {
		ServletContext context = this.getServletContext();
		synchronized (context) {
			DB databaseUtenti = (DB) context.getAttribute("databaseUtenti");
			if (databaseUtenti == null) {
			databaseUtenti = DBMaker.newFileDB(new File("databaseUtenti")).closeOnJvmShutdown().make();
			context.setAttribute("databaseUtenti", databaseUtenti);
			}
		return databaseUtenti;
		}
	}
	
	//creo db per l'oggetto
	private DB getDBOggetti() {
			ServletContext context = this.getServletContext();
			synchronized (context) {
				DB databaseOggetti = (DB) context.getAttribute("databaseOggetti");
				if (databaseOggetti == null) {
					databaseOggetti = DBMaker.newFileDB(new File("databaseOggetti")).closeOnJvmShutdown().make();
				context.setAttribute("databaseOggetti", databaseOggetti);
				}
			return databaseOggetti;
			}
		}
	
	//creo db per l'offerta
		private DB getDBOfferta() {
				ServletContext context = this.getServletContext();
				synchronized (context) {
					DB databaseOfferta = (DB) context.getAttribute("databaseOfferta");
					if (databaseOfferta == null) {
						databaseOfferta = DBMaker.newFileDB(new File("databaseOfferta")).closeOnJvmShutdown().make();
					context.setAttribute("databaseOfferta", databaseOfferta);					
					}
				return databaseOfferta;
				}
			}
		
		//creo db per la domanda
				private DB getDBDomanda() {
						ServletContext context = this.getServletContext();
						synchronized (context) {
							DB databaseDomanda = (DB) context.getAttribute("databaseDomanda");
							if (databaseDomanda == null) {
								databaseDomanda = DBMaker.newFileDB(new File("databaseDomanda")).closeOnJvmShutdown().make();
							context.setAttribute("databaseDomanda", databaseDomanda);					
							}
						return databaseDomanda;
						}
					}
	
	public String newUser() throws IllegalArgumentException{ //String username, String nome, String cognome, String cell, String password, String email, String cf, String indirizzo

		
		return "";
	}
	
	
	public Utente visualizzaDatiUtente(String username) throws IllegalArgumentException{
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		Utente u = new Utente();
		for(String i: key) {
			if(mappaUtente.get(i).getUsername().contentEquals(username)) {
				u = new Utente(mappaUtente.get(i).getUsername().trim(), mappaUtente.get(i).getNome().trim(), mappaUtente.get(i).getCognome().trim(), mappaUtente.get(i).getCell().trim(),mappaUtente.get(i).getPassword().trim(), mappaUtente.get(i).getEmail().trim(),  mappaUtente.get(i).getCod_fiscale().trim(),  mappaUtente.get(i).getIndirizzo().trim());
			}
		}
		return u;
	}
	
	
	public String vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase) throws IllegalArgumentException{
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		//Oggetto oggetto = new Oggetto();
		
		String result = "";
		String idOggetto = UUID.randomUUID().toString();
		
		Oggetto oggetto = new Oggetto(venditore, idOggetto, nome, descrizione, prezzoBase);
		boolean response = true;
		/*for(String i: key ) {
			if(mappaOggetto.get(i).getIdOggetto().contentEquals()) {
				result = "Oggetto gi&agrave; messo in vendita";
				response = false;
				break;
			}
		}
		
		if(response) {
		*/
			mappaOggetto.put(idOggetto, oggetto);
			dbOggetti.commit();
			result = "Oggetto pubblicato con successo";
		//}
		
		return result;
	}
	
	public ArrayList<Oggetto> visualzzaOggetti(){
		
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		
		ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();
		
		for(String i: key ) {
	
				oggetti.add(new Oggetto(mappaOggetto.get(i).getUtente(),
										mappaOggetto.get(i).getIdOggetto(),
										mappaOggetto.get(i).getNome(),
										mappaOggetto.get(i).getDescrizione(),
										mappaOggetto.get(i).getPrezzoBase()));
										
				

		}
		if(oggetti.size() == 0) {
			return null;
		}

		
		return oggetti;
		
	}
	
	public boolean inviaDomanda(Oggetto oggetto, Utente utente, String domanda) {
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> key = mappaDomanda.keySet();
		
		String idDomanda = UUID.randomUUID().toString();//crea id a random

		Domanda domandaPerOggetto = new Domanda(idDomanda, oggetto, utente, domanda);
		
		mappaDomanda.put(idDomanda, domandaPerOggetto);
		dbDomanda.commit();
		return true;
	}
	
	public Utente infoUtente(String username) {
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		Utente utente = new Utente();
		for(String i:key) {
			if(mappaUtente.get(i).getUsername().equals(username.trim())) {
				utente = new Utente(mappaUtente.get(i).getUsername(), mappaUtente.get(i).getNome(), mappaUtente.get(i).getCognome(), mappaUtente.get(i).getCell(), mappaUtente.get(i).getPassword(), mappaUtente.get(i).getEmail(), mappaUtente.get(i).getCod_fiscale(), mappaUtente.get(i).getIndirizzo());
			}
		}
		return utente;
	}
	
	public boolean inserisciOfferta(Oggetto oggetto, Utente utente, double importo) {
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> key = mappaOfferta.keySet();
		
		String idOfferta = UUID.randomUUID().toString();//crea id a random
		Offerta offerta = new Offerta(oggetto, utente, importo);
		boolean response = true; 
		
		
		if(response) {
			mappaOfferta.put(idOfferta, offerta); //all'id offerta gli associamo tutto l'oggetto offerta
		}
		
		dbOfferta.commit();
		return response;
		
	}
	
	
	
	
	
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	@Override
	public String addUser(String username, String nome, String cognome, String cell, String password, String email, String cf, String indirizzo) {
		// TODO Auto-generated method stub
		
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		
		String result = "";
		Utente utente = new Utente(username, nome, cognome, cell, password, email, cf, indirizzo);
		boolean response = true;
		for(String i: key ) {
			if(mappaUtente.get(i).getUsername().contentEquals(username)) {
				result = "Username gi&agrave inserito";
				response = false;
				break;
			}
		}
		if(response) {
			mappaUtente.put(username, utente);
			dbUtenti.commit();
			result = "Inserimento avvenuto con successo";
		}
		
		return result;
		
	}
	
	@Override
	public Utente richiestaAccessoUtente(String username, String password) {
		
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		Utente utente = new Utente();
		boolean response = false;
		for(String i: key ) {
			if(mappaUtente.get(i).getUsername().equals(username) && mappaUtente.get(i).getPassword().equals(password)) {
				System.out.println("Sono entrato nel ciclo interno");
				utente = new Utente(mappaUtente.get(i).getUsername().trim(), mappaUtente.get(i).getNome().trim(), mappaUtente.get(i).getCognome().trim(), mappaUtente.get(i).getCell().trim(),mappaUtente.get(i).getPassword().trim(), mappaUtente.get(i).getEmail().trim(),  mappaUtente.get(i).getCod_fiscale().trim(),  mappaUtente.get(i).getIndirizzo().trim());
				response = true;
			}
		}
		if(!response) {
			return null;		
		}
		
		return utente;
		
	}
}
