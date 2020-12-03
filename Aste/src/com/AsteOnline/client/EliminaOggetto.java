package com.AsteOnline.client;

import java.util.ArrayList;

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
import com.google.gwt.user.client.ui.Widget;

public class EliminaOggetto extends Composite implements HasText {

	private static EliminaOggettoUiBinder uiBinder = GWT.create(EliminaOggettoUiBinder.class);

	interface EliminaOggettoUiBinder extends UiBinder<Widget, EliminaOggetto> {
	}
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	public EliminaOggetto() {
		initWidget(uiBinder.createAndBindUi(this));
		//visualizzo gli oggetti in vendita
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
						final Button b = new Button();
						b.setText("Elimina");
						
						final Oggetto o = result.get(n);
						//mostro le informazioni relative agli oggetti
						w.getElement().setInnerHTML("Nome : " + result.get(n).getNome() + "<br>" + 
								"Descrizione : " + result.get(n).getDescrizione() + "<br>" +
								"Prezzo : " + result.get(n).getPrezzoBase() + "<br><br>");
						
						b.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								//elimino l'oggetto dal db 
								greetingService.eliminaOggetto(o, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Impossibile eliminare l'oggetto");
									}

									@Override
									public void onSuccess(Boolean result) {
										
										Window.alert("Oggetto eliminato con successo");
										
										RootPanel.get().clear();
										
										EliminaOggetto el = new EliminaOggetto();
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
