package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Offerta;
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

public class EliminaOfferta extends Composite implements HasText {

	private static EliminaOffertaUiBinder uiBinder = GWT.create(EliminaOffertaUiBinder.class);

	interface EliminaOffertaUiBinder extends UiBinder<Widget, EliminaOfferta> {
	}

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);
	
	public EliminaOfferta() {
		initWidget(uiBinder.createAndBindUi(this));
		//prendo le offerte inserite
		greetingService.viewOfferte(new AsyncCallback<ArrayList<Offerta>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Impossibile visualizzare le offerte"); //in GreetingServiceImpl

			}

			@Override
			public void onSuccess(ArrayList<Offerta> result) {
				
				if(result == null) {
					Window.alert("Nessun offerta esistente");
				}
				else {
						for(int n=0; n<result.size(); n++) {
						
						
						final Label w = new Label();
						final Button b = new Button();
						b.setText("Elimina");
						
						final Offerta o = result.get(n);
						
						w.getElement().setInnerHTML("Importo : " + result.get(n).getImporto() + "<br>" + 
								"Oggetto : " + result.get(n).getOggetto().getNome() + "<br>" +
								"Utente : " + result.get(n).getUtente().getUsername() + "<br><br>");
						
						RootPanel.get().add(w);
						RootPanel.get().add(b);
						
						b.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								//elimino le offerte selezionate
								greetingService.eliminaOfferta(o, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Impossibile eliminare l'offerta");
									}

									@Override
									public void onSuccess(Boolean result) {
										// TODO Auto-generated method stub
										
										Window.alert("Offerta eliminata con successo");
										
										RootPanel.get().clear();
										
										EliminaOfferta eo = new EliminaOfferta();
										RootPanel.get().add(eo);
										
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
