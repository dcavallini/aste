package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Asta;
import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Unit;
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
	@UiField Button invia;

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	public VendiOggetto() {
		
	}

	public VendiOggetto(Utente utente) {
		initWidget(uiBinder.createAndBindUi(this));

		final ArrayList<String> nomiCategorie = new ArrayList<String>();

		final ArrayList<Categoria> totCategorie = new ArrayList<Categoria>();

		//popolo la listbox con le categorie
		greetingService.inizializzazioneCategorie(new AsyncCallback<ArrayList<Categoria>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Categoria> result) {

				for(int i = 0; i < result.size(); i++) {
					String tmp = result.get(i).getCategoria();
					nomiCategorie.add(tmp);
					totCategorie.add(result.get(i));
				}

				categoria.getElement().getStyle().setBackgroundColor("#f1f1f1");
				categoria.getElement().getStyle().setWidth(1180, Unit.PX);
				categoria.getElement().getStyle().setPadding(10, Unit.PX);
				
				categoria.setAcceptableValues(nomiCategorie);
			}
		});

		usernameVenditore=utente;

		invia.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(!nome.getValue().isEmpty() &&
					!descrizione.getValue().isEmpty() &&
					!prezzoBase.getValue().isEmpty() &&
					!dataScadenza.getValue().isEmpty() &&
					categoria.getValue()!=null) {
				
					//metto l'oggetto in vendita
					greetingService.vendiOggetto(usernameVenditore, nome.getValue().toString().trim(), descrizione.getValue().toString().trim(), Double.parseDouble(prezzoBase.getValue()), dataScadenza.getValue().toString(), categoria.getValue().toString(), new AsyncCallback<Oggetto>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Impossibile mettere l'oggetto in vendita"); //in GreetingServiceImpl
	
						}
	
						@Override
						public void onSuccess(Oggetto result) {
							Window.alert("Oggetto venduto correttamente");
						}

					});
				} else {
					Window.alert("Tutti i campi sono obbligatori");
				}

			}

		});

	}

}
