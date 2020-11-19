package com.AsteOnline.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

public class MenuUtenteRegistrato extends Composite implements HasText {

	public String globalUsername;
	
	private static MenuUtenteRegistratoUiBinder uiBinder = GWT.create(MenuUtenteRegistratoUiBinder.class);

	interface MenuUtenteRegistratoUiBinder extends UiBinder<Widget, MenuUtenteRegistrato> {
	}

	public MenuUtenteRegistrato(String username) {
		initWidget(uiBinder.createAndBindUi(this));

		globalUsername=username;
		
		History.newItem("MenuUtente");

		hyperLinks();

		History.addValueChangeHandler(new ValueChangeHandler<String> () {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				String tokenAttuale = event.getValue(); //prendo il token attuale
				if(tokenAttuale.equals("datiUtente")) {
					apriPaginaVisualizzaDatiUtente();
				}else if(tokenAttuale.equals("MenuUtente")) {
					apriMenuUtenteRegistrato();
				}else if(tokenAttuale.equals("vendiOggetto")) {
					apriPaginaVenditaOggetto(globalUsername);
				}else if(tokenAttuale.equals("visualizzaOggetti")) {
					apriVisualizzaOggetti();
				}else if(tokenAttuale.equals("visualizzaDomande")) {
					apriVisualizzaDomande();
				}else if(tokenAttuale.equals("visualizzaRisposte")) {
					apriVisualizzaRisposte();
				}
			}

		});
	}

	public void apriPaginaVenditaOggetto(String username) {
		RootPanel.get().clear();
		VendiOggetto vo = new VendiOggetto(username); //strin username inside per capire chi lo vende
		RootPanel.get().add(vo);
	}
	
	public void apriPaginaVisualizzaDatiUtente() {
		RootPanel.get().clear();
		VisualizzaProfilo vp = new VisualizzaProfilo();
		RootPanel.get().add(vp);
	}
	
	public void apriMenuUtenteRegistrato() {
		RootPanel.get().clear();
		hyperLinks();
		//RootPanel.get().add(aste);
	}
	
	public void apriVisualizzaOggetti() {
		RootPanel.get().clear();
		VisualizzaOggetti vo = new VisualizzaOggetti(globalUsername);
		RootPanel.get().add(vo);
	}
	
	public void apriVisualizzaDomande() {
		//da implementare la ui binder
	}
	
	public void apriVisualizzaRisposte() {
		//da implementare la ui binder
	}

	private void hyperLinks() {
		//definisci qui la tua homepage
		Hyperlink datiUtente = new Hyperlink("Visualizza i dati di un utente", "datiUtente");
		RootPanel.get().add(datiUtente);

		Hyperlink domande = new Hyperlink("Visualizza le domande che ti sono state fatte", "visualizzaDomande");
		RootPanel.get().add(domande);
		
		Hyperlink risposte = new Hyperlink("Visualizza le risposte che hai ricevuto", "visualizzaRisposte");
		RootPanel.get().add(risposte);

		Hyperlink vendiOggetto = new Hyperlink("Metti un oggetto in vendita", "vendiOggetto");
		RootPanel.get().add(vendiOggetto);

		Hyperlink visualizzaOggetti = new Hyperlink("Visualizza gli oggetti messi in vendita", "visualizzaOggetti");
		RootPanel.get().add(visualizzaOggetti);
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
