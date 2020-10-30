package aste.server;

import aste.client.GreetingService;
import aste.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mycompany.shared.Utente;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import aste.client.GreetingService;
import aste.shared.*;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	
	public static final String DB_UTENTI = "utenti.db";
	public static final String DB_CATEGORIE = "categorie.db";
	public static final String SIMPLE_MAP = "mappa";
	
	public String start() {
		
		DB db = DBMaker.fileDB(DB_CATEGORIE).make();
		
		HTreeMap<Integer, String> map = db.hashMap(SIMPLE_MAP).keySerializer(Serializer.INTEGER)
				.valueSerializer(Serializer.STRING).createOrOpen();
		
		map.put(0, "Abbigliamento");
		map.put(1, "Casa");
		map.put(2, "Elettronica");
		map.put(3, "Giardinaggio");
		map.put(4, "Sport");
		
		db.close();
		
		return "";
	}
	
	public boolean login(String input) {

		DB db = DBMaker.fileDB(DB_UTENTI).make();
		
		HTreeMap<String, String> map = db.hashMap(SIMPLE_MAP).keySerializer(Serializer.STRING)
				.valueSerializer(Serializer.STRING).createOrOpen();
		
		if (map.containsKey(input)) {
			db.close();
			return true;
		} else {
			db.close();
			return false;
		}
	}
	
	
	//non so se va fatto void oppure no, da capire
	public String signIn(Utente input) {
		
		DB db = DBMaker.fileDB(DB_UTENTI).make();
		
		HTreeMap<String, String> map = db.hashMap(SIMPLE_MAP).keySerializer(Serializer.STRING)
				.valueSerializer(Serializer.STRING).createOrOpen();
		
		if (map.containsKey(input.datiLogin())) {
//			System.out.println(input + " contenuto inviato " + map.get(input) + " volte!");
//			map.put(input.toString(), map.get(input) + 1);
			db.close();
			return "Utente già esistente";
		} else {
//			System.out.println(input + " non presente nel DB! Aggiungo!");
			map.put(input.datiLogin(), input.toString());
			db.close();
			return "Utente inserito con successo";
			//return 
		}
		
	}
	
	public String datiUtente(String username) {
		
		DB db = DBMaker.fileDB(DB_UTENTI).make();
		
		HTreeMap<String, String> map = db.hashMap(SIMPLE_MAP).keySerializer(Serializer.STRING)
				.valueSerializer(Serializer.STRING).createOrOpen();
		
		//da guardare perchè come chiave vengono messi sia l'username che la psw
		//se non va basta usare solo l'username come chiave e fare un get della psw ogni volta che ci si logga
		
		if(map.containsKey(username)) {  
			
			String tmp = map.get(username);
			
			String[] valori = tmp.split(";");
			
			String datiUtente = valori[0] + ";" + valori[1] + ";" + valori[2] + ";" + valori[3] + ";" +
						valori[5] + ";" + valori[6] + ";" + valori[7] + ";" + valori[8] + ";" + valori[9]
								+ ";" + valori[10] + ";" + valori[11];
			
			db.close();
			
			return datiUtente;
			
		}
		else {
			db.close();
			return "Utente non trovato";
		}
	}
	
	//per il momento le cateogie sono string poi andrà cambiato il tipo
	public ArrayList<String> getCategorie() {
		
		//da vedere come funziona
		ArrayList<String> categorie = new ArrayList<String>();
		
		DB db = DBMaker.fileDB(DB_CATEGORIE).make();
		
		HTreeMap<Integer, String> map = db.hashMap(SIMPLE_MAP).keySerializer(Serializer.INTEGER)
				.valueSerializer(Serializer.STRING).createOrOpen();
		
		categorie = (ArrayList<String>) db.getAll();
		
		db.close();	
		
		return categorie;
		
	}

	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
//	private String escapeHtml(String html) {
//		if (html == null) {
//			return null;
//		}
//		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//	}eAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
//	}
//
//
//	@Override
//	public String greetServer(String name) throws IllegalArgumentException {
//		// TODO Auto-generated method stub
//		return null;
//	}
}