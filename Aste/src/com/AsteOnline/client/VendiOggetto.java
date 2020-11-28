package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Asta;
import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class VendiOggetto extends Composite {

	public Utente usernameVenditore;
	public Utente venditore = new Utente();
	Oggetto oggetto = new Oggetto();

	private static VendiOggettoUiBinder uiBinder = GWT.create(VendiOggettoUiBinder.class);

	interface VendiOggettoUiBinder extends UiBinder<Widget, VendiOggetto> {
	}

	@UiField InputElement nome, descrizione, prezzoBase, dataScadenza;
	@UiField ValueListBox<String> categoria;

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	public VendiOggetto() {
		
	}

	public VendiOggetto(Utente utente) {
		initWidget(uiBinder.createAndBindUi(this));

		final ArrayList<String> nomiCategorie = new ArrayList<String>();

		final ArrayList<Categoria> totCategorie = new ArrayList<Categoria>();

		greetingService.inizializzazioneCategorie(new AsyncCallback<ArrayList<Categoria>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Categoria> result) {
				// TODO Auto-generated method stub


				for(int i = 0; i < result.size(); i++) {
					String tmp = result.get(i).getCategoria();
					nomiCategorie.add(tmp);
					totCategorie.add(result.get(i));
				}


				categoria.setAcceptableValues(nomiCategorie);
			}
		});





		usernameVenditore=utente;

		Button invia = new Button("Salva");
		RootPanel.get().add(invia);

		invia.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

//				//Window.alert(dataScadenza.getValue().toString());
//				greetingService.infoUtente(usernameVenditore.getUsername(), new AsyncCallback<Utente> () {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						Window.alert("Non trovo informazioni sul venditore");
//					}
//
//					@Override
//					public void onSuccess(Utente result) {
//						// TODO Auto-generated method stub
//						venditore=result; //prendo tutto il venditore 
//						
//					}
//
//				});
				
				//Window.alert(usernameVenditore.getUsername());

				greetingService.vendiOggetto(usernameVenditore, nome.getValue().toString().trim(), descrizione.getValue().toString().trim(), Double.parseDouble(prezzoBase.getValue()), dataScadenza.getValue().toString(), categoria.getValue().toString(), new AsyncCallback<Oggetto>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Errore nel server"); //in GreetingServiceImpl

					}

					@Override
					public void onSuccess(Oggetto result) {
						Window.alert("Oggetto venduto correttamente");

//						oggetto = result;
//
//						Asta asta = new Asta(venditore, oggetto, dataScadenza.getValue().toString(), true);

					}

				});
				//}

			}

		});

	}

}
