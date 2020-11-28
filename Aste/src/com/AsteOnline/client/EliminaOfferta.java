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
		
		greetingService.viewOfferte(new AsyncCallback<ArrayList<Offerta>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore nel server"); //in GreetingServiceImpl

			}

			//livello base, bisogna capire cosa succede quando ci sono gli oggetti,
			//mancano ancora delle cose per creare intermanete l'oggetto
			//da guardare come mettere i bottoni per fare un'offerta e rifare bene il costruttore per oggetto, cambiare anche 
			//il metodo della messa in vendita
			@Override
			public void onSuccess(ArrayList<Offerta> result) {
				//

				//if(result.getNome()==null || result.getDescrizione()==null || String.valueOf(result.getPrezzoBase())==null) {
				//Window.alert("Oggetto inesistente");
				//} else {
				//al posto dell'alert mostra l'oggetto
				if(result == null) {
					Window.alert("Nessun oggetto esistente");
				}
				else {
					//result.forEach((n) -> {
						//Window.alert(n.getNome());
					for(int n=0; n<result.size(); n++) {
						
						final Label w = new Label();
						final Button b = new Button();
						b.setText("Elimina");
						
						Offerta o = result.get(n);
						
						w.getElement().setInnerHTML("Importo : " + result.get(n).getImporto() + "<br>" + 
								"Oggetto : " + result.get(n).getOggetto().getNome() + "<br>" +
								"Utente : " + result.get(n).getUtente().getUsername() + "<br><br>");
						
						b.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								
								greetingService.eliminaOfferta(o, new AsyncCallback<Boolean>() {

									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										
									}

									@Override
									public void onSuccess(Boolean result) {
										// TODO Auto-generated method stub
										
										Window.alert("Oggetto eliminato con successo");
										
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
