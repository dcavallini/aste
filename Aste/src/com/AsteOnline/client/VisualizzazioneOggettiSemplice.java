package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;

import com.AsteOnline.shared.Oggetto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class VisualizzazioneOggettiSemplice extends Composite implements HasText {

	private static VisualizzazioneOggettiSempliceUiBinder uiBinder = GWT
			.create(VisualizzazioneOggettiSempliceUiBinder.class);
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	interface VisualizzazioneOggettiSempliceUiBinder extends UiBinder<Widget, VisualizzazioneOggettiSemplice> {
	}

	public VisualizzazioneOggettiSemplice() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//mostro gli oggetti per utenti non registrati
		greetingService.visualzzaOggetti(new AsyncCallback<ArrayList<Oggetto>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore nel server"); //in GreetingServiceImpl

			}

			@Override
			public void onSuccess(ArrayList<Oggetto> result) {
				
				if(result == null) {
					Window.alert("Nessun oggetto esistente");
				}
				else {
					for(int n=0; n<result.size(); n++) {
						
						final Label w = new Label();
						//mostro solo nome, descrizione e prezzo
						w.getElement().setInnerHTML("Nome : " + result.get(n).getNome() + "<br>" + 
								"Descrizione : " + result.get(n).getDescrizione() + "<br>" +
								"Prezzo : " + result.get(n).getPrezzoBase() + "<br><br>");
				
						RootPanel.get().add(w);
						
					}
				}

				
			}

		});

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
