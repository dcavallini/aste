package com.AsteOnline.client;

import com.AsteOnline.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AsteOnline implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
public void onModuleLoad() {
		
		History.newItem("Homepage");
		
		hyperLinks();
		
		History.addValueChangeHandler(new ValueChangeHandler<String> () {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				String tokenAttuale = event.getValue(); //prendo il token attuale
				if(tokenAttuale.equals("registrazione")) {
					apriPaginaSignIn();
				}else if(tokenAttuale.equals("login")) {
					//apriPaginaProfilo();
					apriPaginaLogin();
					
				}else if(tokenAttuale.equals("Homepage")) {
					apriHomePage();
					
//				}else if(tokenAttuale.equals("vendiOggetto")) {
//					apriPaginaVenditaOggetto();
				}else if(tokenAttuale.equals("visualizzaOggettiSemplice")) {
					apriVisualizzaOggetti();
				}
			}
			
		});
		
	}
	
	public void hyperLinks() {
		//definisci qui la tua homepage
		Hyperlink registrazione = new Hyperlink("Registrazione", "registrazione");
		RootPanel.get().add(registrazione);

		Hyperlink profilo = new Hyperlink("Login utente", "login");
		RootPanel.get().add(profilo);

//		Hyperlink vendiOggetto = new Hyperlink("Metti un oggetto in vendita", "vendiOggetto");
//		RootPanel.get().add(vendiOggetto);

		Hyperlink visualizzaOggetti = new Hyperlink("Visualizza gli oggetti messi in vendita", "visualizzaOggettiSemplice");
		RootPanel.get().add(visualizzaOggetti);

	}
	
	public void apriPaginaSignIn() {
		RootPanel.get().clear();
		signIn si = new signIn();
		RootPanel.get().add(si);
		
	}
	
	public void apriHomePage() {
		RootPanel.get().clear();
		hyperLinks();
		//RootPanel.get().add(aste);
		}
	
	public void apriPaginaLogin() {
		RootPanel.get().clear();
		Login lg = new Login();
		RootPanel.get().add(lg);
	}
	

	
	public void apriVisualizzaOggetti() {
		RootPanel.get().clear();
		VisualizzazioneOggettiSemplice vo = new VisualizzazioneOggettiSemplice();
		RootPanel.get().add(vo);
	}
	
	
}
