package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Risposta;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class MostraDomanda extends Composite {

	private static MostraDomandaUiBinder uiBinder = GWT.create(MostraDomandaUiBinder.class);

	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	Utente utenteGlobale = new Utente();
	Domanda domanda = new Domanda();
	
	interface MostraDomandaUiBinder extends UiBinder<Widget, MostraDomanda> {
	}

	@UiField ParagraphElement question, answer, user;
	@UiField InputElement risposta;
	@UiField Button inviaRisposta;
	
	public MostraDomanda() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public MostraDomanda(Domanda domandaPar, Utente utente) {
		
		utenteGlobale = utente;
		domanda = domandaPar;
		
		initWidget(uiBinder.createAndBindUi(this));
		question.setInnerText("Domanda: "+ domanda.getContenuto() );
		user.setInnerText("Fatta dall'utente: "+ domanda.getUtente().getUsername());
		
		inviaRisposta.addClickHandler(new ClickHandler () {

			@Override
			public void onClick(ClickEvent event) {
				greetingService.inviaRisposta(domanda, risposta.getValue().toString().trim(), utenteGlobale, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Impossibile inviare la risposta");
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Risposta inviata");
						MenuUtenteRegistrato menu = new MenuUtenteRegistrato(utenteGlobale);
						menu.apriContainer(domanda.getOggetto(), utenteGlobale);
					}
					
					
				});
			}
			
		});
		
		//visualizzo le risposte per le domande inserite
		greetingService.viewRisposta(domanda.getIdDomanda(), new AsyncCallback<ArrayList<Risposta>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Non e&grave possibile visualizzare le risposte");
			}

			@Override
			public void onSuccess(ArrayList<Risposta> risposte) {
				for(int j=0; j<risposte.size(); j++) {

					answer.setInnerText("Risposta: " + risposte.get(j).getContenuto());

				}
			}

			
		});
	}

}
