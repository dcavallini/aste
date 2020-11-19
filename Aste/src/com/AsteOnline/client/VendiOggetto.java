package com.AsteOnline.client;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class VendiOggetto extends Composite {

	public String usernameVenditore;
	public Utente venditore = new Utente();
	
	private static VendiOggettoUiBinder uiBinder = GWT.create(VendiOggettoUiBinder.class);

	interface VendiOggettoUiBinder extends UiBinder<Widget, VendiOggetto> {
	}

	@UiField InputElement nome, descrizione, prezzoBase;
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);
	
	public VendiOggetto() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public VendiOggetto(String username) {
		initWidget(uiBinder.createAndBindUi(this));
		
		usernameVenditore=username;
		
		Button invia = new Button("Salva");
		RootPanel.get().add(invia);
		
		invia.addClickHandler(new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
				greetingService.infoUtente(usernameVenditore, new AsyncCallback<Utente> () {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Non trovo informazioni sul venditore");
					}

					@Override
					public void onSuccess(Utente result) {
						// TODO Auto-generated method stub
						venditore=result; //prendo tutto il venditore 
					}
					
				});
				
				greetingService.vendiOggetto(venditore, nome.getValue().toString().trim(), descrizione.getValue().toString().trim(), Double.parseDouble(prezzoBase.getValue()), new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Errore nel server"); //in GreetingServiceImpl
					
				}

				@Override
				public void onSuccess(String result) {
					Window.alert(result);
					
					//if(result.getNome()==null || result.getDescrizione()==null || String.valueOf(result.getPrezzoBase())==null) {
						//Window.alert("Oggetto inesistente");
					//} else {
						//al posto dell'alert mostra l'oggetto
					//refresh 
					//AsteOnline aste=new AsteOnline();
					//aste.apriPaginaSignIn();
				//}
			}
				
		});
	//}
}
		
	});
		
}
}
