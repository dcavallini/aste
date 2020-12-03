package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Risposta;
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
import com.google.gwt.user.client.ui.Widget;

public class EliminaRisposta extends Composite implements HasText {

	private static EliminaRispostaUiBinder uiBinder = GWT.create(EliminaRispostaUiBinder.class);

	interface EliminaRispostaUiBinder extends UiBinder<Widget, EliminaRisposta> {
	}
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	public EliminaRisposta() {
		initWidget(uiBinder.createAndBindUi(this));
		//visualizzo tutte le risposte del db
		greetingService.visualizzaTutteRisposte(new AsyncCallback<ArrayList<Risposta>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore nel server"); //in GreetingServiceImpl

			}
			@Override
			public void onSuccess(ArrayList<Risposta> result) {
		
				if(result == null) {
					Window.alert("Nessuna risposta esistente");
				}
				else {
					
					for(int n=0; n<result.size(); n++) {
						
						
						final Label w = new Label();
						final Button b = new Button();
						b.setText("Elimina");
						
						final Risposta r = result.get(n);
						
						w.getElement().setInnerHTML("Contenuto : " + result.get(n).getContenuto() + "<br>" +
								"Domanda : " + result.get(n).getDomanda().getContenuto() + "<br>");
						
						RootPanel.get().add(w);
						RootPanel.get().add(b);
						
						b.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								//elimino le risposte
								greetingService.eliminaRisposta(r, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Impossibile eliminare la risposta");
									}

									@Override
									public void onSuccess(Boolean result) {
										
										Window.alert("Risposta eliminata con successo");
										
										RootPanel.get().clear();
										
										EliminaRisposta el = new EliminaRisposta();
										RootPanel.get().add(el);
										
									}
									
								});
								
							}
							
						});
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
