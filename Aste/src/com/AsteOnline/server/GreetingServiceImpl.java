package com.AsteOnline.server;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
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
	
	//creo db per la risposta
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

	//creo db per le categorie
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

	//creo db per le aste
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


	//metodo per visualizzare i dati dell'utente 
	public Utente visualizzaDatiUtente(String username) throws IllegalArgumentException{
		//richiamo il db dell'utente 
		DB dbUtenti = getDBUtenti();
		//creo la mappa
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		Utente u = new Utente();
		//ciclo la mappa
		for(String i: key) {
			//sulla base dell'username inserito mostro i dati dell'utente
			if(mappaUtente.get(i).getUsername().contentEquals(username)) {
				u = new Utente(mappaUtente.get(i).getUsername().trim(), mappaUtente.get(i).getNome().trim(), mappaUtente.get(i).getCognome().trim(), mappaUtente.get(i).getCell().trim(),mappaUtente.get(i).getPassword().trim(), mappaUtente.get(i).getEmail().trim(),  mappaUtente.get(i).getCod_fiscale().trim(),  mappaUtente.get(i).getIndirizzo().trim());
			}
		}
		return u;
	}


	//metodo per vendere un oggetto
	public Oggetto vendiOggetto(Utente venditore, String nome, String descrizione, double prezzoBase, String scadenza, String categoria) throws IllegalArgumentException{
		//definisco il db degli oggetti
		DB dbOggetti = getDBOggetti();
		//creo la mappa degli oggetti
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		//richiamo il db per l'asta
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		//definisco degli id random
		String idOggetto = UUID.randomUUID().toString();
		String idAsta = UUID.randomUUID().toString();

		
		Oggetto oggetto = new Oggetto(venditore, idOggetto, nome, descrizione, prezzoBase, categoria);
		Asta asta = new Asta(venditore, oggetto, scadenza, false); 
		
		//passo i dati dell'oggetto e dell'asta alla mappa 
		mappaOggetto.put(idOggetto, oggetto);
		mappaAsta.put(idAsta, asta);
		dbAsta.commit();
		dbOggetti.commit();
		
		return oggetto;
	}
	
	
	//metodo per visualizzare la data di scadenza di un oggetto in vendita
	public String viewScadenzaAsta(Oggetto oggetto) {
		//richiamo il db dell'asta
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		String dataScadenza=null;
		//ciclo la mappa
		for(String i:keyAsta) {
			//ottengo la data di scadenza
			if(mappaAsta.get(i).getOggetto().getNome().contentEquals(oggetto.getNome())) {
				dataScadenza = mappaAsta.get(i).getScadenza();
			}
		}
		return dataScadenza;
	}
	

	//metodo per visualizzare gli oggetti e tutte le sue caratteristiche
	public ArrayList<Oggetto> visualzzaOggetti(){
		//richiamo il db degli oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		//richiamo il db delle aste
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();

		ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();

		//ciclo la mappa degli oggetti
		for(String i: key ) {
			//ciclo la mappa dell'asta
			for(String j : keyAsta) {
				if(mappaAsta.get(j).getOggetto().getIdOggetto().equals(mappaOggetto.get(i).getIdOggetto())) {
					Date scadenzaData = new Date();
					String dataScadenza="";
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					
					String oggi=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					
					
					Date today= new Date();
					try {
						dataScadenza = mappaAsta.get(j).getScadenza();
						//converto la data di scadenza in formato Date()
						scadenzaData = formatter.parse(dataScadenza);
						//converto la data di oggi in formato stringa
						today = formatter.parse(oggi);

					} catch(ParseException e) {
					}
					if(scadenzaData.before(today)) {
						//l'oggetto non e' piu' in vendita
					} else {
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

		if(oggetti.size() == 0) {
			return null;
		}


		return oggetti;

	}

	
	//metodo per ottenere le informazioni di un oggetto sulla base del suo id
	public Oggetto infoOggetto(String idOggetto) {
		DB dbOggetto = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetti=dbOggetto.getHashMap("databaseOggetti");
		
		return mappaOggetti.get(idOggetto);
	}
		
	
	//metodo per ottenere le informazioni dell'utente sulla base del suo username
	public Utente infoUtente(String username) {
		//richiamo il db degli utenti
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		Utente utente = new Utente();
		//ciclo la mappa 
		for(String i:key) {
			if(mappaUtente.get(i).getUsername().equals(username.trim())) {
				//gli passo tutte le informazioni inserite nel db
				utente = new Utente(mappaUtente.get(i).getUsername(), mappaUtente.get(i).getNome(), 
						mappaUtente.get(i).getCognome(), mappaUtente.get(i).getCell(), 
						mappaUtente.get(i).getPassword(), mappaUtente.get(i).getEmail(), 
						mappaUtente.get(i).getCod_fiscale(), mappaUtente.get(i).getIndirizzo(),
						mappaUtente.get(i).getSesso(), mappaUtente.get(i).getDataNascita(),
						mappaUtente.get(i).getLuogoNascita());
			}
		}
		return utente;
	}

	
	//metodo per inserire un'offerta ad un oggetto messo in vendita
	public boolean inserisciOfferta(Oggetto oggetto, Utente utente, double importo) {
		//richiamo il db delle offerte
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

	//metodo per aggiungere un utente
	@Override
	public String addUser(String username, String nome, String cognome, String cell, String password, 
			String email, String cf, String indirizzo, String sesso, String dataNascita, String luogoNascita) {
		//richiamo il db dell'utente
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();

		String result = "";
		//inserisco i dati inseriti nel db
		Utente utente = new Utente(username, nome, cognome, cell, password, email, cf, indirizzo, sesso, dataNascita, luogoNascita);
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

	
	//metodo per accedere al sito
	public boolean login(String username, String password) {
		//Richiamo il db dell'utente
		DB dbUtenti = getDBUtenti();
		HTreeMap <String, Utente> mappaUtente=dbUtenti.getHashMap("databaseUtenti");
		Set<String> key = mappaUtente.keySet();
		
		boolean loggato = false;
		//ciclo la mappa
		for (String i:key) {
			//se l'username e la password esistono accedo al sito
			if(mappaUtente.get(i).getUsername().contentEquals(username)) {
				if(mappaUtente.get(i).getPassword().contentEquals(password)) {
					loggato = true;
				}
			}
		}
		return loggato;
	}
	

	
	//definisco le categorie base
	@Override
	public ArrayList<Categoria> inizializzazioneCategorie() {
			//richiamo il db delle categorie
				DB dbCategorie = getDBCategorie();
				HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
				Set<String> key = mappaCategorie.keySet();

				
				ArrayList<Categoria> categorie = new ArrayList<Categoria>();

				Categoria c = new Categoria();

				for(String i : key) {
					
					categorie.add(mappaCategorie.get(i));

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
					elettronica = new Categoria("Elettronica",UUID.randomUUID().toString(), 0);;

					//aggiungo le categorie base al db
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
					
					dbCategorie.commit();

				}
				return categorie;

	}
	
	//metodo per filtrare gli oggetti per categoria
	@Override
	public ArrayList<Oggetto> filtroPerCategoria(Categoria c) {
		//richiamo il db degli oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		//richiamo il db delle aste
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();

		ArrayList<Oggetto> oggetti = new ArrayList<Oggetto>();

		if(c == null) {
			oggetti = visualzzaOggetti();
		}
		else {
			//creo un arraylist a cui passo tutte le categorie madri/figlie/nipoti
			ArrayList<Categoria> tutteCategorieNelDB = new ArrayList<Categoria>();
			//richiamo il db delle categorie
			DB dbCategorie = getDBCategorie();
			HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
			Set<String> keyCat = mappaCategorie.keySet();
			
			//ciclo la mappa
			for(String i : keyCat) {
				tutteCategorieNelDB.add(mappaCategorie.get(i));
			}
		
			ArrayList<Categoria> categorie = new ArrayList<Categoria>();
			
			categorie = tutteCategorie(c, tutteCategorieNelDB);
					
			
			for(String i: key ) {
				for(String j : keyAsta) {
					if(mappaAsta.get(j).getOggetto().getIdOggetto().equals(mappaOggetto.get(i).getIdOggetto())) {
						Date scadenzaData = new Date();
						String dataScadenza="";
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						String oggi = formatter.format(new Date());
						Date today= new Date();
						try {
							dataScadenza = mappaAsta.get(j).getScadenza();

							scadenzaData = formatter.parse(dataScadenza);
							today = formatter.parse(oggi);
						} catch(ParseException e) {
						}
						if(scadenzaData.before(today)) {
							// l'oggetto non e' piu' in vendita
						} else {
							for(int k = 0; k < categorie.size(); k++) {
								
								if(mappaOggetto.get(i).getCategoria().getCategoria().equals(categorie.get(k).getCategoria())) {
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

				}
			}

		}

		return oggetti;	
	}
	
	
	//metodo per ottenere tutte le categorie e sottocategorie
	private ArrayList<Categoria> tutteCategorie(Categoria c, ArrayList<Categoria> categorie){
	      
	      ArrayList<Categoria> cats = new ArrayList<Categoria>();
	  
	    Queue<Categoria> q = new LinkedList<>();
	    q.add(c);  
	    while (!q.isEmpty()) 
	    { 
	        int n = q.size(); 
	  
	        while (n > 0) 
	        { 
	            
	            Categoria p = q.peek(); 
	            q.remove(); 
	            	            
	            cats.add(p);
	            for (int i = 0; i < p.getSizeSottoCategorie(); i++) 
	                for(int j = 0; j < categorie.size(); j++){
	                    if(categorie.get(j).getCategoria().equals(p.getSottoCategorie().get(i).getCategoria())){

	                    	q.add(p.getSottoCategorie().get(i));
	                    	
	                        if(categorie.get(j).getSizeSottoCategorie() > 0) {
		                        q.addAll(categorie.get(j).getSottoCategorie());
	                        }
	                    }
	                }
	            n--; 
	        } 
	           
	    } 
	    
	    return cats;
	      
	  }

	
	//metodo per prendere gli oggetti venduti
	@Override
	public ArrayList<Oggetto> oggettiVenduti(Utente utente) {
		//richiamo il db degli oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> key = mappaOggetto.keySet();
		
		ArrayList<Oggetto> listaOggetti = new ArrayList<Oggetto>();
		//ciclo la mappa
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
	
	//metodo per aggiungere le sotto categorie al db
	public boolean addSottoCategoria(Categoria nomeCategoriaMadre, String nomeCategoriaFiglia, int profondita) {
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> key = mappaCategorie.keySet();

		String idCategoria = UUID.randomUUID().toString();
		Categoria madre = new Categoria();
		boolean result = false;

		ArrayList<Categoria> sottocategorie = new ArrayList<Categoria>();
		
		//ciclo la mappa
		for(String i : key) {

			if(mappaCategorie.get(i).getCategoria().equals(nomeCategoriaMadre.getCategoria())) {
				//se esistono delle sottocategorie le aggiungo alla mappa
				if(mappaCategorie.get(i).getSizeSottoCategorie() > 0) {
					sottocategorie = mappaCategorie.get(i).getSottoCategorie();
				}
				
				madre = new Categoria(mappaCategorie.get(i).getCategoria(), mappaCategorie.get(i).getId(),
						mappaCategorie.get(i).getProfondita());
				
				mappaCategorie.remove(i);
				
			} 
		}
		
		Categoria nuovaSottoCategoria = new Categoria(nomeCategoriaFiglia, idCategoria, profondita+1);
		
		sottocategorie.add(nuovaSottoCategoria);

		madre.setSottoCategorie(sottocategorie);
		
		mappaCategorie.put(madre.getCategoria(), madre);
		mappaCategorie.put(nuovaSottoCategoria.getCategoria(), nuovaSottoCategoria);
		
		result = true;
		
		dbCategorie.commit();
		
		return result;				
	}
	
	
	//metodo per aggiungere una categoria al db
	public boolean addCategoria(String nomeCategoria) {
		//richiamo il db delle categorie
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
	
	
	//metodo per visualizzare lo stato dell'asta di un oggetto
	@Override
	public String statoOggetto(Oggetto oggetto) {
		//richiamo il db dell'oggetto
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyOggetto = mappaOggetto.keySet();
		//richiamo il db delle offerte
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOfferta = mappaOfferta.keySet();
		//richiamo il db delle aste
		DB dbAste = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAste.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		double importo = -1;
		
		String vincitore = null;
		
		Date scadenzaData = new Date();
		
		String dataScadenza="";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		String oggi = formatter.format(new Date());
		
		Date today= new Date();
		
		try {//controllo che la data non sia scaduta
			for(String i:keyAsta) {
				if(mappaAsta.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
					dataScadenza = mappaAsta.get(i).getScadenza();
				}
			}
			scadenzaData = formatter.parse(dataScadenza);
			today = formatter.parse(oggi);
		} catch(ParseException e) {
		}
		if(scadenzaData.before(today)) {
			
			// l'oggetto non e' piu' in vendita
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

	
	
	//metodo per controllare l'invio di una domanda
	public boolean inviaDomanda(Oggetto oggetto, Utente utente, String domanda) {
		//richiamo il db delle domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> key = mappaDomanda.keySet();

		String idDomanda = UUID.randomUUID().toString();//crea id a random

		Domanda domandaPerOggetto = new Domanda(idDomanda, oggetto, utente, domanda);

		mappaDomanda.put(idDomanda, domandaPerOggetto);
		dbDomanda.commit();
		return true;
	}

	
	
	//metodo per visualizzare le domande
	@Override
	public ArrayList<Domanda> viewDomanda(String idOggetto) {
		DB dbDomande = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomande=dbDomande.getHashMap("databaseDomanda");
		Set<String> key = mappaDomande.keySet();

		ArrayList<Domanda> domande = new ArrayList<Domanda>();

		//ciclo la mappa
		for(String i: key ) {
			
			if(mappaDomande.get(i).getOggetto().getIdOggetto().equals(idOggetto)) {

				domande.add(new Domanda(mappaDomande.get(i).getIdDomanda(),
						mappaDomande.get(i).getOggetto(),
						mappaDomande.get(i).getUtente(),
						mappaDomande.get(i).getContenuto()));
				
			}
			
		}
		
		return domande;
	}


	//metodo per controllare l'invio di una risposta
	public void inviaRisposta(Domanda domanda, String contenuto, Utente utente) {
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> key = mappaRisposte.keySet();

		String idRisposta = UUID.randomUUID().toString();//crea id a random
		
		Risposta risposta = new Risposta(idRisposta, domanda, contenuto, utente);
		
		mappaRisposte.put(domanda.getIdDomanda(), risposta);
		dbRisposte.commit();
		
	}
	
	//metodo per visualizzare le risposte
	@Override
	public ArrayList<Risposta> viewRisposta(String idDomanda) {
		//richiamo il db delle risposte
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
	
	//metodo per visualizzare tutte le risposte
	public ArrayList<Risposta> visualizzaTutteRisposte(){
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> key = mappaRisposte.keySet();

		ArrayList<Risposta> risposte = new ArrayList<Risposta>();

		for(String i: key ) {
				risposte.add(mappaRisposte.get(i));
		}
		
		if(risposte.size() == 0) {
			return null;
		}

		return risposte;
		
	}


	//metodo per eliminare gli oggetti
	@Override
	public boolean eliminaOggetto(Oggetto oggetto) {
		//richiamo il db degli oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		//richiamo il db delle domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		//richiamo il db delle offerte
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		//richiamo il db delle aste
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		//ciclo la mappa dell'oggetto ed elimino l'oggetto selezionato
		for(String i : keyO) {
			if(mappaOggetto.get(i).getIdOggetto().equals(oggetto.getIdOggetto())) {

				mappaOggetto.remove(i);
				
			}
		}
		
		//ciclo la mappa delle risposte per eliminare le risposte relative all'oggetto eliminato
		for(String i : keyR) {
			if(mappaRisposte.get(i).getDomanda().getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaRisposte.remove(i);
				
			}
		}
		
		//ciclo la mappa delle domande per eliminare le domande relative all'oggetto eliminato
		for(String i : keyD) {
			if(mappaDomanda.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaDomanda.remove(i);
				
			}
		}
		
		//ciclo la mappa delle offerte per eliminare le offerte relative all'oggetto eliminato
		for(String i : keyOff) {
			if(mappaOfferta.get(i).getOggetto().getIdOggetto().equals(oggetto.getIdOggetto())) {
				
				mappaOfferta.remove(i);
				
			}
		}
		
		//ciclo la mappa delle aste per eliminare l'asta relativa all'oggetto eliminato
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

	
	//metodo per visualizzare le offerte inserite
	@Override
	public ArrayList<Offerta> viewOfferte() {
		
		ArrayList<Offerta> offerte = new ArrayList<Offerta>();
		//richiamo la mappa delle offerte
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();

		for(String i: keyOff ) {
			offerte.add(mappaOfferta.get(i));
		}
		
		if(offerte.size() == 0) {
			return null;
		}		
		
		return offerte;
		
	}

	//metodo per eliminare le offerte per un oggetto
	@Override
	public boolean eliminaOfferta(Offerta offerta) {
		
		ArrayList<Offerta> offerte = new ArrayList<Offerta>();
		
		//richiamo il db delle offerte
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		Oggetto o = new Oggetto();
		double importo = 0;
		double nuovoImportoMassimo = -2;

		//ciclo la mappa delle offerte
		for(String i: keyOff ) {
			
			//rimuovo l'ultima offerta inserita
			if(offerta.getImporto() == mappaOfferta.get(i).getImporto() && 
				offerta.getOggetto().getIdOggetto().equals(mappaOfferta.get(i).getOggetto().getIdOggetto())) {
				
				mappaOfferta.remove(i);
				importo = offerta.getImporto();
				
				dbOfferta.commit();
				
				//aggiorno il db passando l'ultima offerta più alta inserita 
				for(String j: keyOff ) {
					
					if(offerta.getOggetto().getNome().equals(mappaOfferta.get(i).getOggetto().getNome())) {
						
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
		
		//richiamo il db degli oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		//aggiorno il prezzo dell'oggetto
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

	
	//metodo per eliminare le categorie
	@Override
	public boolean eliminaCategoria(Categoria categoria) {
		
		ArrayList<Categoria> tutteCategorieNelDB = new ArrayList<Categoria>();
		
		//richiamo il db delle categorie
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> keyCat = mappaCategorie.keySet();
		
		//ciclo la mappa e aggiungo alla lista le categorie
		for(String i : keyCat) {
			tutteCategorieNelDB.add(mappaCategorie.get(i));
		}
	
		ArrayList<Categoria> categorie = new ArrayList<Categoria>();
		
		categorie = tutteCategorie(categoria, tutteCategorieNelDB);
		
		
		for(String i : keyCat) {
			
			for(int j = 0; j < categorie.size(); j++) {
				
				if(mappaCategorie.get(i).getCategoria().equals(categorie.get(j).getCategoria())) {
					mappaCategorie.remove(i);
					dbCategorie.commit();
				}
			}
		}
		
		
		//elimino da oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		for(String i : keyO) {
			for(int j = 0; j < categorie.size(); j++) {
								
				if(mappaOggetto.get(i).getCategoria().getCategoria().equals(categorie.get(j).getCategoria())) {
					mappaOggetto.remove(i);
					dbOggetti.commit();
				}
			}
		}
		
		//elimino da aste
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		for(String i : keyAsta) {
			for(int j = 0; j < categorie.size(); j++) {
				if(mappaAsta.get(i).getOggetto().getCategoria().getCategoria().equals(categorie.get(j).getCategoria())) {
					mappaAsta.remove(i);
					dbAsta.commit();
				}
			}
		}
		
		//elimino le offerte
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		for(String i : keyOff) {
			for(int j = 0; j < categorie.size(); j++) {
				if(mappaOfferta.get(i).getOggetto().getCategoria().getCategoria().equals(categorie.get(j).getCategoria())) {
					mappaOfferta.remove(i);
					dbOfferta.commit();
				}
			}
		}
		
		//elimino le domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		for(String i : keyD) {
			for(int j = 0; j < categorie.size(); j++) {
				if(mappaDomanda.get(i).getOggetto().getCategoria().getCategoria().equals(categorie.get(j).getCategoria())) {
					mappaDomanda.remove(i);
					dbDomanda.commit();
				}
			}
		}
		
		//elimino le risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		for(String i : keyR) {
			for(int j = 0; j < categorie.size(); j++) {
				
				if(mappaRisposte.get(i).getDomanda().getOggetto().getCategoria().getCategoria().equals(categorie.get(j).getCategoria())) {
					mappaRisposte.remove(i);
					dbRisposte.commit();
				}
			}
		}
		
		return true;	
	}
	


	//metodo per rinominare una categoria
	@Override
	public boolean rinominaCategoria(String vecchioNome, String nuovoNome, int profondita) {
		
		//richiamo il db degli oggetti
		DB dbOggetti = getDBOggetti();
		HTreeMap <String, Oggetto> mappaOggetto=dbOggetti.getHashMap("databaseOggetti");
		Set<String> keyO = mappaOggetto.keySet();
		
		//richiamo il db delle categorie
		DB dbCategorie = getDBCategorie();
		HTreeMap <String, Categoria> mappaCategorie = dbCategorie.getHashMap("databaseCategorie");
		Set<String> keyCat = mappaCategorie.keySet();
		
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		//richiamo il db delle domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		//richiamo il db delle offerte
		DB dbOfferta = getDBOfferta();
		HTreeMap <String, Offerta> mappaOfferta=dbOfferta.getHashMap("databaseOfferta");
		Set<String> keyOff = mappaOfferta.keySet();
		
		//richiamo il db delle aste
		DB dbAsta = getDBAsta();
		HTreeMap <String, Asta> mappaAsta=dbAsta.getHashMap("databaseAsta");
		Set<String> keyAsta = mappaAsta.keySet();
		
		Categoria categoria = new Categoria();
		
		for(String i : keyCat){
			//aggiorno il db passando il nuovo nome della categoria
			if(mappaCategorie.get(i).getCategoria().equals(vecchioNome)) {
				
				categoria = mappaCategorie.get(i);
				
				mappaCategorie.get(i).setCategoria(nuovoNome); 
				dbCategorie.commit();
				
			}
			
		}
		
		for(String i : keyCat){
			//se esistono delle sottocategorie aggiorno il nome della categoria madre corrispondente
			if(mappaCategorie.get(i).getSizeSottoCategorie() > 0) {
				
				for(int j = 0; j < mappaCategorie.get(i).getSottoCategorie().size(); j++) {
					
					if(mappaCategorie.get(i).getSottoCategorie().get(j).getId().equals(categoria.getId())) {
						
						
						mappaCategorie.get(i).getSottoCategorie().set(j, categoria);
						dbCategorie.commit();
					}
					
				}
				
			}
			
		}
		
		//aggiorno il nome della categoria degli oggetti relativi alla stessa
		for(String i : keyO) {
			if(mappaOggetto.get(i).getCategoria().getCategoria().equals(vecchioNome)) {
				
				mappaOggetto.get(i).getCategoria().setCategoria(nuovoNome);
				dbOggetti.commit();
				
			}
		}
		/*
		for(String i : keyR) {
			System.out.println(mappaRisposte.get(i).getOggetto().getCategoria().getCategoria()+ " "+ vecchioNome);
			if(mappaRisposte.get(i).getOggetto().getCategoria().getCategoria().equals(vecchioNome)) {
				
				mappaRisposte.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				dbRisposte.commit();
				
			}
		}
		
		for(String i : keyD) {
			if(mappaDomanda.get(i).getOggetto().getCategoria().getCategoria().equals(vecchioNome)) {
				
				mappaDomanda.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				dbDomanda.commit();
				
			}
		}
		
		*/
		
		//aggiorno l'offerta relativa all'oggetto a cui ho settato il nome della categoria
		for(String i : keyOff) {
			if(mappaOfferta.get(i).getOggetto().getCategoria().getCategoria().equals(vecchioNome)) {
				
				mappaOfferta.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				dbOfferta.commit();
				
			}
		}
		
		//aggiorno l'asta
		for(String i : keyAsta) {
			if(mappaAsta.get(i).getOggetto().getCategoria().getCategoria().equals(vecchioNome)) {
				
				mappaAsta.get(i).getOggetto().getCategoria().setCategoria(nuovoNome);
				dbAsta.commit();
			}
		}
		

		
		return true;
		
	}

	
	//metodo per eliminare le domande 
	@Override
	public boolean eliminaDomanda(Domanda domanda) {
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		//richiamo il db delle domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> keyD = mappaDomanda.keySet();
		
		//elimino la domanda
		for(String i : keyD) {
			
			if(mappaDomanda.get(i).getIdDomanda().equals(domanda.getIdDomanda())) {
				
				mappaDomanda.remove(i);
				
			}
			
		}
		
		//elimino la risposta relativa alla domanda eliminata
		for(String i : keyR) {
			
			if(mappaRisposte.get(i).getDomanda().getIdDomanda().equals(domanda.getIdDomanda())) {
				
				mappaRisposte.remove(i);
				
			}
			
		}
		
		dbRisposte.commit();
		dbDomanda.commit();
		
		return true;
	}

	
	//metodo per eliminare le risposte
	@Override
	public boolean eliminaRisposta(Risposta risposta) {
		
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		//elimino la risposta dal db
		for(String i : keyR) {
			
			if(mappaRisposte.get(i).getIdRisposta().equals(risposta.getIdRisposta())) {
				
				mappaRisposte.remove(i);
				
			}
			
		}
		
		dbRisposte.commit();
		
		return true;
	}
	
	
	//metodo per visualizzare le domande dal metodo di eliminazione delle domande
	@Override
	public ArrayList<Domanda> viewDomandaEliminazione() {
		//richiamo il db delle domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> key = mappaDomanda.keySet();
		
		ArrayList<Domanda> domande = new ArrayList<Domanda>();
		
		for(String i : key) {
			domande.add(new Domanda(mappaDomanda.get(i).getIdDomanda(), 
									mappaDomanda.get(i).getOggetto(),
									mappaDomanda.get(i).getUtente(),
									mappaDomanda.get(i).getContenuto()));
			
		}
		
		return domande;
	}
	
	
	//metodo per visualizzare le domande effettuate ad un utente
	public ArrayList<Domanda> viewDomandaPerUtente(String utente){
		//richiamo il db delle domande
		DB dbDomanda = getDBDomanda();
		HTreeMap <String, Domanda> mappaDomanda=dbDomanda.getHashMap("databaseDomanda");
		Set<String> key = mappaDomanda.keySet();
		
		ArrayList<Domanda> domande = new ArrayList<Domanda>();
		
		for(String i : key) {
			
			if(mappaDomanda.get(i).getUtente().getUsername().equals(utente)) {
				domande.add(new Domanda(mappaDomanda.get(i).getIdDomanda(), 
						mappaDomanda.get(i).getOggetto(),
						mappaDomanda.get(i).getUtente(),
						mappaDomanda.get(i).getContenuto()));
			}
			
		}
		
		if(domande.size() == 0) {
			return null;
		}
		
		return domande;
		
	}
	
	
	//metodo per visualizzare le risposte dell'utente
	public ArrayList<Risposta> viewRispostaPerUtente(String utente){
		//richiamo il db delle risposte
		DB dbRisposte = getDBRisposta();
		HTreeMap <String, Risposta> mappaRisposte=dbRisposte.getHashMap("databaseRisposta");
		Set<String> keyR = mappaRisposte.keySet();
		
		ArrayList<Risposta> risposte = new ArrayList<Risposta>();
		
		
		for(String i : keyR) {
			
			if(mappaRisposte.get(i).getUtente().getUsername().equals(utente)) {
				
				
				risposte.add(mappaRisposte.get(i));
			}
			
		}
		
		if(risposte.size() == 0) {
			return null;
		}
		
		return risposte;
		
	}
	
	public String tmp() {
		
		
		return "";
	}
	

}
