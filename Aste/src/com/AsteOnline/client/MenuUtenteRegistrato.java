package com.AsteOnline.client;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class MenuUtenteRegistrato extends Composite implements HasText {

	public Utente globalUtente = new Utente();
	
	String username = null;
	
	private static MenuUtenteRegistratoUiBinder uiBinder = GWT.create(MenuUtenteRegistratoUiBinder.class);

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	
	interface MenuUtenteRegistratoUiBinder extends UiBinder<Widget, MenuUtenteRegistrato> {
	}

	public MenuUtenteRegistrato(Utente utente) {
		initWidget(uiBinder.createAndBindUi(this));
				
		
		globalUtente=utente;
		
		History.newItem("MenuUtente");

		username = globalUtente.getUsername();

		hyperLinks();

		History.addValueChangeHandler(new ValueChangeHandler<String> () {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				String tokenAttuale = event.getValue(); //prendo il token attuale
				if(tokenAttuale.equals("datiUtente")) {
					apriPaginaVisualizzaDatiUtente(globalUtente.getUsername());
				}else if(tokenAttuale.equals("MenuUtente")) {
					apriMenuUtenteRegistrato();
				}else if(tokenAttuale.equals("vendiOggetto")) {
					apriPaginaVenditaOggetto();
				}else if(tokenAttuale.equals("visualizzaOggetti")) {
					apriVisualizzaOggetti();
				} else if(tokenAttuale.equals("amministrazione")) {
					apriAmministrazione();
				} else if(tokenAttuale.equals("home")){
					tornaHomePage();
				}else {
					String idOggetto = tokenAttuale.trim();
					greetingService.infoOggetto(idOggetto, new AsyncCallback<Oggetto>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Non sono riuscito a prendere le informazioni dell'oggetto");
						}

						@Override
						public void onSuccess(Oggetto result) {
							apriContainer(result, globalUtente);

						}
						
					});
					
				}
			}

		});
	}

	public void apriPaginaVenditaOggetto() {
		RootPanel.get().clear();
		VendiOggetto vo = new VendiOggetto(globalUtente); //strin username inside per capire chi lo vende
		RootPanel.get().add(vo);
	}
	
	public void apriPaginaVisualizzaDatiUtente(String username) {
		RootPanel.get().clear();
		VisualizzaProfilo vp = new VisualizzaProfilo(username);
		RootPanel.get().add(vp);
	}
	
	public void apriMenuUtenteRegistrato() {
		RootPanel.get().clear();
		hyperLinks();
		//RootPanel.get().add(aste);
	}
	
	public void apriVisualizzaOggetti() {
		RootPanel.get().clear();
		VisualizzaOggetti vo = new VisualizzaOggetti(globalUtente);
		RootPanel.get().add(vo);
	}
	
	public void apriAmministrazione() {
		RootPanel.get().clear();
		Amministrazione amministrazione = new Amministrazione();
		RootPanel.get().add(amministrazione);
	}
	
	public void apriContainer(Oggetto oggetto, Utente utente) {
		RootPanel.get().clear();
		ContainerOggetti container = new ContainerOggetti(oggetto, utente);
		RootPanel.get().add(container);
	}
	
	public void tornaHomePage() {
		
		Window.Location.reload();
		
	}


	private void hyperLinks() {
		//definisci qui la tua homepage		
		Hyperlink logOut = new Hyperlink("Logout", "home");
		RootPanel.get().add(logOut);
		
		Hyperlink amministrazioneAdmin = new Hyperlink("Amministrazione", "amministrazione");

		
		if(username.equals("admin")) {
			RootPanel.get().add(amministrazioneAdmin);
			RootPanel.get().add(logOut);
		} else {
			Hyperlink datiUtente = new Hyperlink("Visualizza i dati di un utente", "datiUtente");
			RootPanel.get().add(datiUtente);

			Hyperlink vendiOggetto = new Hyperlink("Metti un oggetto in vendita", "vendiOggetto");
			RootPanel.get().add(vendiOggetto);

			Hyperlink visualizzaOggetti = new Hyperlink("Visualizza gli oggetti messi in vendita", "visualizzaOggetti");
			RootPanel.get().add(visualizzaOggetti);
			
			RootPanel.get().add(logOut);

		}
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub

	}
}
