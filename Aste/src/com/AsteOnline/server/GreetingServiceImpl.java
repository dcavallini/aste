package com.AsteOnline.server;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;

import com.AsteOnline.client.GreetingService;
import com.AsteOnline.shared.Asta;
import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.FieldVerifier;
import com.AsteOnline.shared.Offerta;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Risposta;
import com.AsteOnline.shared.Utente;
import com.AsteOnline.shared.Utente.Admin;
import com.AsteOnline.shared.Utente.UtenteRegistrato;
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
				Utente utente = new Utente();
				//creo l'admin
				Utente admin = utente.new Admin("admin", "admin");
				HTreeMap <String, Utente> mappaUtente=databaseUtenti.getHashMap("databaseUtenti");
				mappaUtente.put("admin", admin);
				databaseUtenti.commit();
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
	
	private DB getDBRisposta() {
		ServletContext context = this.getServletContext();
		synchronized (context) {
			DB databaseRisposta = (DB) context.getAttribute("databaseRisposta");
			if (databaseRisposta == null) {
				databaseRisposta = DBMaker.newFileDB(new File("databaseRisposta")).closeOnJvmShutdown().make();
				context.setAttribute("databaseRisposta", databaseRisposta);					
			}
			return databaseRisposta;
		}
	}

	private DB getDBCategorie() {
		ServletContext context = this.getServletContext();
		synchronized (context) {
			DB databaseCategorie = (DB) context.getAttribute("databaseCategorie");
			if (databaseCategorie == null) {
				databaseCategorie = DBMaker.newFileDB(new File("databaseCategorie")).closeOnJvmShutdown().make();
				context.setAttribute("databaseCategorie", databaseCategorie);					
			}
			return databaseCategorie;
		}
	}

	private DB getDBAsta() {
		ServletContext context = this.getServletContext();
		synchronized (context) {
			DB databaseAsta = (DB) context.getAttribute("databaseAsta");
			if (databaseAsta == null) {
				databaseAsta = DBMaker.newFileDB(new File("databaseAsta")).closeOnJvmShutdown().make();
				context.setAttribute("databaseAsta", databaseAsta);
			}
			return databaseAsta;
		}
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


	public Oggetto vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase, String scadenza, String categoria) throws IllegalArgumentException{
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		String idOggetto = UUID.randomUUID().toString();
		String idAsta = UUID.randomUUID().toString();


		Oggetto oggetto = new Oggetto(venditore, idOggetto, nome, descrizione, prezzoBase, categoria);
		Asta asta = new Asta(venditore, oggetto, scadenza, false); //sbaglio che metti sempre false
		boolean response = true;
		mappaOggetto.put(idOggetto, oggetto);
		mappaAsta.put(idAsta, asta);
		dbAsta.commit();
		dbOggetti.commit();
		return oggetto;
	}

	public ArrayList<Oggetto> visualzzaOggetti(){

		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();

		ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();

		for(String i: key ) {
			
				
//				Date scadenzaData = new Date();
//				String dataScadenza="";
//				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
//				String oggi = formatter.format(new Date());
//				Date today= new Date();
//				
//				try {
//					for(String j:keyAsta) {
//						//la data dentro all'asta è null probabilmente non viene popoplata
//						if(mappaAsta.get(j).getOggetto().getIdOggetto().equals(mappaOggetto.get(i).getIdOggetto())) {
//							dataScadenza = mappaAsta.get(j).getScadenza();
//						}
//					}
//					scadenzaData = formatter.parse(dataScadenza);
//					today = formatter.parse(oggi);
//				} catch(ParseException e) {
//				}
				
//				if(scadenzaData.before(today)) {
//					
//				}
//				else {
			//System.out.println(mappaOggetto.get(i).getNome());
					oggetti.add(new Oggetto(mappaOggetto.get(i).getUtente(),
							mappaOggetto.get(i).getIdOggetto(),
							mappaOggetto.get(i).getNome(),
							mappaOggetto.get(i).getDescrizione(),
							mappaOggetto.get(i).getPrezzoBase(),
							mappaOggetto.get(i).getCategoria().getCategoria()));
				//}

		}
		
		//System.out.println(oggetti.size() + "");
		
		if(oggetti.size() == 0) {
			return null;
		}


		return oggetti;

	}

	
	
	public Oggetto infoOggetto(String idOggetto) {
		DB dbOggetto = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetti=dbOggetto.getHashMap("databaseOggetti");
		
		return mappaOggetti.get(idOggetto);
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

	public boolean login(String username, String password) {

		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		
		boolean loggato = false;
		for (String i:key) {
			if(mappaUtente.get(i).getUsername().contentEquals(username)) {
				if(mappaUtente.get(i).getPassword().contentEquals(password)) {
					loggato = true;
				}
			}
		}
		return loggato;
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
				//System.out.println("Sono entrato nel ciclo interno");
				utente = new Utente(mappaUtente.get(i).getUsername().trim(), mappaUtente.get(i).getNome().trim(), mappaUtente.get(i).getCognome().trim(), mappaUtente.get(i).getCell().trim(),mappaUtente.get(i).getPassword().trim(), mappaUtente.get(i).getEmail().trim(),  mappaUtente.get(i).getCod_fiscale().trim(),  mappaUtente.get(i).getIndirizzo().trim());
				response = true;
			}
		}
		if(!response) {
			return null;		
		}

		return utente;

	}

	@Override
	public ArrayList<Categoria> inizializzazioneCategorie() {

		//da capire come fare a gestire le sottocategorie

		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> key = mappaCategorie.keySet();

		ArrayList<Categoria> categorie = new ArrayList<Categoria>();
		//String idCategoria = UUID.randomUUID().toString();
		Categoria c = new Categoria();

		for(String i : key) {
			c = new Categoria(mappaCategorie.get(i).getCategoria(), mappaCategorie.get(i).getId(), mappaCategorie.get(i).getProfondita());
			c.setSottoCategorie(mappaCategorie.get(i).getSottoCategorie());

			categorie.add(c);
			
			
			//commentato, può servire per futuro debuggin
//			if(c.getCategoria().equals("Abbigliamento")) {
//				System.out.println("Size delle sottoCategorie di abbigliamento : " + c.getSizeSottoCategorie());
//			}

		}

		if(categorie.size() == 0) {
			Categoria abbigliamento = new Categoria();
			Categoria casa = new Categoria();
			Categoria giardinaggio= new Categoria();
			Categoria sport = new Categoria();
			Categoria elettronica = new Categoria();
			
			abbigliamento = new Categoria("Abbigliamento",UUID.randomUUID().toString(), 0);
			casa = new Categoria("Casa",UUID.randomUUID().toString(), 0);
			giardinaggio = new Categoria("Giardinaggio",UUID.randomUUID().toString(), 0);
			sport = new Categoria("Sport",UUID.randomUUID().toString(), 0);
			elettronica = new Categoria("Elettronica",UUID.randomUUID().toString(), 0);


			categorie.add(abbigliamento);
			categorie.add(casa);
			categorie.add(giardinaggio);
			categorie.add(sport);
			categorie.add(elettronica);	
			
			mappaCategorie.put("abbigliamento", abbigliamento);
			mappaCategorie.put("casa", casa);
			mappaCategorie.put("giardinaggio", giardinaggio);
			mappaCategorie.put("sport", sport);
			mappaCategorie.put("elettronica", elettronica);

		}
		
		
		dbCategorie.commit();
		return categorie;

	}
	

	@Override
	public ArrayList<Oggetto> filtroPerCategoria(Categoria c) {

		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();

		ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();

		if(c == null) {
			oggetti = visualzzaOggetti();
			
			
		}
		else {
		
			ArrayList<Categoria> categorie = new ArrayList<Categoria>();
			
			categorie = tutteCategorie(c, new ArrayList<Categoria>(), 0);
			
			
			for(String i : key) {
				for(int j = 0; j < categorie.size(); j++) {
					
					if(mappaOggetto.get(i).getCategoria().getCategoria().equals(categorie.get(j).getCategoria())) {
						oggetti.add(new Oggetto(mappaOggetto.get(i).getUtente(),
								mappaOggetto.get(i).getIdOggetto(),
								mappaOggetto.get(i).getNome(),
								mappaOggetto.get(i).getDescrizione(),
								mappaOggetto.get(i).getPrezzoBase(),
								mappaOggetto.get(i).getCategoria().getCategoria()));
					}
				}
			}

		}

		return oggetti;	
	}
	
	private ArrayList<Categoria> tutteCategorie(Categoria c, ArrayList<Categoria> categoriaESottoCategoria, int i){

		categoriaESottoCategoria.add(c);

		if(c.getSizeSottoCategorie() == 0){

			return categoriaESottoCategoria;

		}


		if(c.getSottoCategorie().get(i).getSizeSottoCategorie() > 0){

			return tutteCategorie(c.getSottoCategorie().get(i), categoriaESottoCategoria, 0);
		}

		return tutteCategorie(c.getSottoCategorie().get(i), categoriaESottoCategoria, i++);

	}

	@Override
	public ArrayList<Oggetto> oggettiVenduti(Utente utente) {
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		ArrayList<Oggetto> listaOggetti = new ArrayList<Oggetto>();
		for(String i:key) {
			Oggetto o = new Oggetto(utente,
					mappaOggetto.get(i).getIdOggetto(),
					mappaOggetto.get(i).getNome(),
					mappaOggetto.get(i).getDescrizione(),
					mappaOggetto.get(i).getPrezzoBase(),
					mappaOggetto.get(i).getCategoria().getCategoria());

			listaOggetti.add(o);
		}
		return listaOggetti;
	}
	
	
	public boolean addSottoCategoria(Categoria nomeCategoriaMadre, String nomeCategoriaFiglia, int profondita) {
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> key = mappaCategorie.keySet();

		String idCategoria = UUID.randomUUID().toString();
		Categoria c = new Categoria();
		boolean result = false;

		
		for(String i : key) {
			//System.out.println(mappaCategorie.get(i).getCategoria()+ " " + nomeCategoriaMadre);
			if(mappaCategorie.get(i).getCategoria().equals(nomeCategoriaMadre.getCategoria())) {
				
				c = new Categoria(nomeCategoriaFiglia, idCategoria, profondita++);
				mappaCategorie.put(nomeCategoriaFiglia, c);
				
				
				mappaCategorie.get(i).addSottoCategoria(new Categoria(nomeCategoriaFiglia, idCategoria, profondita++));
				
				result = true;
				
				dbCategorie.commit();
				
			} 
		}
		return result;				
	}
	
	
	
	public boolean addCategoria(String nomeCategoria) {
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> key = mappaCategorie.keySet();

		String idCategoria = UUID.randomUUID().toString();
		Categoria c = new Categoria();
		boolean result = false;

		
		for(String i : key) {
				
				c = new Categoria(nomeCategoria, idCategoria, 0);
				mappaCategorie.put(nomeCategoria, c);
							
				result = true;
				
			
		}
		dbCategorie.commit();
		return result;				
	}
	
	

	@Override
	public String statoOggetto(Oggetto oggetto) {
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyOggetto = mappaOggetto.keySet();
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOfferta = mappaOfferta.keySet();
		DB dbAste = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAste.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		double importo = -1;
		String vincitore = null;
		Date scadenzaData = new Date();
		String dataScadenza="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		String oggi = formatter.format(new Date());
		Date today= new Date();
		try {
			for(String i:keyAsta) {
				//la data dentro all'asta è null probabilmente non viene popoplata
				if(mappaAsta.get(i).getOggetto().equals(oggetto)) {
					dataScadenza = mappaAsta.get(i).getScadenza();
				}
			}
			scadenzaData = formatter.parse(dataScadenza);
			today = formatter.parse(oggi);
		} catch(ParseException e) {
		}
		if(scadenzaData.before(today)) {
			// l'oggetto non è più in vendita
			if(!mappaOfferta.isEmpty()) {
				for(String i:keyOfferta) {
					if(mappaOfferta.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto()) && mappaOfferta.get(i).getImporto()>importo) {
						importo= mappaOfferta.get(i).getImporto();
						vincitore=mappaOfferta.get(i).getUtente().getUsername();
					}
				}
			}
			if(importo == -1) {
				return "Asta conclusa e oggetto non acquistato" ;
			} else {
				return "Asta conclusa e oggetto aggiudicato da :" + vincitore+ " al prezzo di " + importo ;
			}
		} else {
			//asta ancora non chiusa
			if(!mappaOfferta.isEmpty()) {
				for(String i:keyOfferta) {
					if(mappaOfferta.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto()) && mappaOfferta.get(i).getImporto()>importo) {
						importo= mappaOfferta.get(i).getImporto();
						vincitore=mappaOfferta.get(i).getUtente().getUsername();
					}
				}
			}
			if(importo == -1) {
				return "Asta in corso" ;
			} else {
				return "Asta in corso con offerente attuale: " + vincitore + " al prezzo di "+ importo;
			}
		}
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

	
	
	
	@Override
	public ArrayList<Domanda> viewDomanda(String idOggetto) {
		DB dbDomande = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomande=dbDomande.getHashMap("databaseDomanda");
		Set<String> key = mappaDomande.keySet();

		ArrayList<Domanda> domande = new ArrayList<Domanda>();

		for(String i: key ) {
			System.out.println("Sono dentro al for");
			if(mappaDomande.get(i).getOggetto().getIdOggetto().equals(idOggetto)) {
				System.out.println("Sono dentro all'if");
				domande.add(new Domanda(mappaDomande.get(i).getIdDomanda(),
						mappaDomande.get(i).getOggetto(),
						mappaDomande.get(i).getUtente(),
						mappaDomande.get(i).getContenuto()));
				
			}
			
		}
		
		return domande;
	}

	
	public void inviaRisposta(Domanda domanda, String contenuto) {
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> key = mappaRisposte.keySet();

		Risposta risposta = new Risposta(domanda, contenuto);
		
		mappaRisposte.put(domanda.getIdDomanda(), risposta);
		dbRisposte.commit();
		
	}
	
	
	@Override
	public ArrayList<Risposta> viewRisposta(String idDomanda) {
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> key = mappaRisposte.keySet();

		ArrayList<Risposta> risposte = new ArrayList<Risposta>();

		for(String i: key ) {
			if(mappaRisposte.get(i).getDomanda().getIdDomanda().equals(idDomanda.trim())) {
				risposte.add(mappaRisposte.get(i));
				}
		}

		return risposte;
	}

	
	@Override
	public boolean eliminaOggetto(Oggetto oggetto) {
		
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		
		for(String i : keyO) {
			if(mappaOggetto.get(i).getIdOggetto().equals(oggetto.getIdOggetto())) {
				

				mappaOggetto.remove(i);
				
			}
		}
		
		for(String i : keyR) {
			if(mappaRisposte.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaRisposte.remove(i);
				
			}
		}
		
		for(String i : keyD) {
			if(mappaDomanda.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaDomanda.remove(i);
				
			}
		}
		
		for(String i : keyOff) {
			if(mappaOfferta.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaOfferta.remove(i);
				
			}
		}
		
		for(String i : keyAsta) {
			if(mappaAsta.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaAsta.remove(i);
				
			}
		}

		dbOggetti.commit();
		dbRisposte.commit();
		dbDomanda.commit();
		dbOfferta.commit();
		dbAsta.commit();
		return true;
		
	}

	@Override
	public ArrayList<Offerta> viewOfferte() {
		
		ArrayList<Offerta> offerte = new ArrayList<Offerta>();
		
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();

		for(String i: keyOff ) {
			offerte.add(new Offerta(mappaOfferta.get(i).getOggetto(), 
									mappaOfferta.get(i).getUtente(),
									mappaOfferta.get(i).getImporto()));



		}
		if(offerte.size() == 0) {
			return null;
		}


		
		
		return offerte;
		
	}

	@Override
	public boolean eliminaOfferta(Offerta offerta) {
		
		ArrayList<Offerta> offerte = new ArrayList<Offerta>();
		
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		Oggetto o = new Oggetto();
		double importo = 0;
		double nuovoImportoMassimo = -2;

		for(String i: keyOff ) {
			
			if(offerta.getImporto() == mappaOfferta.get(i).getImporto() && 
				offerta.getOggetto().getIdOggetto().equals(mappaOfferta.get(i).getOggetto().getIdOggetto())) {
				
				mappaOfferta.remove(i);
				importo = offerta.getImporto();
				
				dbOfferta.commit();
				
				
				for(String j: keyOff ) {
					
					if(offerta.getOggetto().getIdOggetto().equals(mappaOfferta.get(i).getOggetto().getIdOggetto())) {
						
						if(mappaOfferta.get(i).getImporto() > nuovoImportoMassimo) {
							
							nuovoImportoMassimo = mappaOfferta.get(i).getImporto();
							
						}
						
						
					}


				}
				
			}
		}
		
		if(nuovoImportoMassimo == -2) {
			nuovoImportoMassimo = 100;
		}
		
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		for(String i : keyO) {
			if(mappaOggetto.get(i).getIdOggetto().equals(offerta.getOggetto().getIdOggetto()) &&
				mappaOggetto.get(i).getPrezzoBase() == importo) {
				

				mappaOggetto.get(i).setPrezzoBase(nuovoImportoMassimo); 
				
			}
		}
		
		dbOfferta.commit();
		dbOggetti.commit();
		
		return true;
	}

	@Override
	public boolean eliminaCategoria(Categoria categoria) {
		
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> keyCat = mappaCategorie.keySet();
		
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		if(categoria.getSottoCategorie().size() != 0) { //o null da capire
			
			for(String i : keyCat){
				
				if(mappaCategorie.get(i).getSottoCategorie().size() > 0) {
					
					for(int j = 0; j < mappaCategorie.get(i).getSottoCategorie().size(); j++) {
						
						if(mappaCategorie.get(i).getSottoCategorie().get(j).getId().equals(categoria.getId())) {
							
							
							mappaCategorie.get(i).getSottoCategorie().remove(j);
							
							mappaCategorie.get(i).getSottoCategorie().addAll(categoria.getSottoCategorie());
						}
						
					}
					
				}
				
			}

			
		} 
		
		for(String i : keyCat) {
			
			if(mappaCategorie.get(i).getId().equals(categoria.getId())) {
				
				mappaCategorie.remove(i);
				
			}
			
		}
		
		for(String i : keyO) {
			if(mappaOggetto.get(i).getCategoria().getCategoria().equals(categoria.getCategoria())) {
				

				mappaOggetto.remove(i);
				
			}
		}
		
		for(String i : keyR) {
			if(mappaRisposte.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaRisposte.remove(i);
				
			}
		}
		
		for(String i : keyD) {
			if(mappaDomanda.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaDomanda.remove(i);
				
			}
		}
		
		for(String i : keyOff) {
			if(mappaOfferta.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaOfferta.remove(i);
				
			}
		}
		
		for(String i : keyAsta) {
			if(mappaAsta.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaAsta.remove(i);
				
			}
		}
		
		dbCategorie.commit();
		dbOggetti.commit();
		dbRisposte.commit();
		dbDomanda.commit();
		dbOfferta.commit();
		dbAsta.commit();
		
		
		return true;
	}

	@Override
	public boolean rinominaCategoria(String vecchioNome, String nuovoNome, int profondita) {
		
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> keyCat = mappaCategorie.keySet();
		
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		Categoria categoria = new Categoria();
		
		for(String i : keyCat){
			
			if(mappaCategorie.get(i).getCategoria().equals(vecchioNome)) {
				
				categoria = mappaCategorie.get(i);
				
				mappaCategorie.get(i).setCategoria(nuovoNome);
				
			}
			
		}
		
			
		for(String i : keyCat){
			
			if(mappaCategorie.get(i).getSottoCategorie().size() > 0) {
				
				for(int j = 0; j < mappaCategorie.get(i).getSottoCategorie().size(); j++) {
					
					if(mappaCategorie.get(i).getSottoCategorie().get(j).getId().equals(categoria.getId())) {
						
						
						mappaCategorie.get(i).getSottoCategorie().set(j, categoria);
					}
					
				}
				
			}
			
		}
		
		
		for(String i : keyCat) {
			
			if(mappaCategorie.get(i).getId().equals(categoria.getId())) {
				
				mappaCategorie.get(i).setCategoria(nuovoNome);
				
			}
			
		}
		
		for(String i : keyO) {
			if(mappaOggetto.get(i).getCategoria().getCategoria().equals(categoria.getCategoria())) {
				

				mappaOggetto.get(i).getCategoria().setCategoria(nuovoNome);
				
			}
		}
		
		for(String i : keyR) {
			if(mappaRisposte.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaRisposte.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				
			}
		}
		
		for(String i : keyD) {
			if(mappaDomanda.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaDomanda.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				
			}
		}
		
		for(String i : keyOff) {
			if(mappaOfferta.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaOfferta.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				
			}
		}
		
		for(String i : keyAsta) {
			if(mappaAsta.get(i).getOggetto().getCategoria().getCategoria().equals(categoria.getCategoria())) {
				
				mappaAsta.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				
			}
		}
		
		dbCategorie.commit();
		dbOggetti.commit();
		dbRisposte.commit();
		dbDomanda.commit();
		dbOfferta.commit();
		dbAsta.commit();
		
		return true;
		
	}

	@Override
	public boolean eliminaDomanda(Domanda domanda) {//errore nella risposta vuole un id
		
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		for(String i : keyD) {
			
			if(mappaDomanda.get(i).getIdDomanda().equals(domanda.getIdDomanda())) {
				
				mappaDomanda.remove(i);
				
			}
			
		}
		
		for(String i : keyR) {
			
			//qua elimino tutte le risposte presenti per un oggetto ma io voglio eliminarne solo una singola
			//ho bisogno che la domanda abbia un id
			
			if(mappaRisposte.get(i).getOggetto().getIdOggetto().equals(domanda.getOggetto().getIdOggetto())) {
				
				mappaRisposte.remove(i);
				
			}
			
		}
		
		dbRisposte.commit();
		dbDomanda.commit();
		
		return true;
	}

	@Override
	public boolean eliminaRisposta(Risposta risposta) {
		
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		
		for(String i : keyR) {
			
			if(mappaRisposte.get(i).getIdRisposta().equals(risposta.getIdRisposta())) {
				
				mappaRisposte.remove(i);
				
			}
			
		}
		
		dbRisposte.commit();
		
		return true;
	}
	
	

}
