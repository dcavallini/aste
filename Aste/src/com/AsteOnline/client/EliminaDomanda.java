package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Domanda;
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

public class EliminaDomanda extends Composite implements HasText {

	private static EliminaDomandaUiBinder uiBinder = GWT.create(EliminaDomandaUiBinder.class);

	interface EliminaDomandaUiBinder extends UiBinder<Widget, EliminaDomanda> {
	}
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	public EliminaDomanda() {
		initWidget(uiBinder.createAndBindUi(this));
		//metodo per visualizzare le domande 
		greetingService.viewDomandaEliminazione(new AsyncCallback<ArrayList<Domanda>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore nel server"); //in GreetingServiceImpl

			}

			@Override
			public void onSuccess(ArrayList<Domanda> result) {
			
				if(result == null) {
					Window.alert("Nessuna domanda esistente");
				}
				else {
					
					for(int n=0; n<result.size(); n++) {
						final Label w = new Label();
						final Button b = new Button();
						b.setText("Elimina");
						
						final Domanda d = result.get(n);
						
						w.getElement().setInnerHTML("Descrizione : " + result.get(n).getContenuto() + "<br>" +
								"Oggetto : " + result.get(n).getOggetto().getNome() + "<br>");
						
						b.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								//elimino la domanda dal db
								greetingService.eliminaDomanda(d, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Impossibile eliminare la domanda");
									}

									@Override
									public void onSuccess(Boolean result) {
										// TODO Auto-generated method stub
										
										Window.alert("Domanda eliminata con successo");
										
										RootPanel.get().clear();
										
										EliminaDomanda el = new EliminaDomanda();
										RootPanel.get().add(el);
										
									}
									
								});
								
							}
							
						});
						
						
						RootPanel.get().add(w);
						RootPanel.get().add(b);
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
