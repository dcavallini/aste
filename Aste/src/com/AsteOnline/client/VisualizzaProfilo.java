package com.AsteOnline.client;

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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class VisualizzaProfilo extends Composite {

	private static VisualizzaProfiloUiBinder uiBinder = GWT.create(VisualizzaProfiloUiBinder.class);

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	interface VisualizzaProfiloUiBinder extends UiBinder<Widget, VisualizzaProfilo> {
	}

	@UiField InputElement username;
	
	public VisualizzaProfilo() {
		initWidget(uiBinder.createAndBindUi(this));
		Button salva = new Button("Salva");
		RootPanel.get().add(salva);
		
		salva.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if(username.getValue().toString().trim().isEmpty()) {
					Window.alert("Devi riempire il campo ");
				} else {
				
					// TODO Auto-generated method stub
					greetingService.visualizzaDatiUtente(username.getValue().toString().trim(), new AsyncCallback<Utente>() {
	
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Errore nel server");
						}
	
						@Override
						public void onSuccess(Utente result) {
						
							if(result.getUsername()== null || result.getNome()==null ) {
								Window.alert("Username inesistente");
							} else {
//								Window.alert(result.getUsername() + " "+ result.getNome());
								
								Label w = new Label();
								w.getElement().setInnerHTML(
										"Username : " + result.getUsername() + "<br>" + 
										"Nome : " + result.getNome() + "<br>" +
										"Cognome : " + result.getCognome() + "<br>" +
										"Telefono : " + result.getCell() + "<br>" +
										"Indirizzo email : " + result.getEmail() + "<br>" +
										"Codice fiscale : " + result.getCod_fiscale() + "<br>" +
										"Indirizzo domicilio : " + result.getIndirizzo() + "<br>" +
										 "<br><br>"); //mancano i campi facoltativi
								
//								AsteOnline aste = new AsteOnline();
//								//ricreo la pagina 
//								aste.apriPaginaProfilo();
								RootPanel.get().add(w);
							}
						}
						
					});
				}
			}
			
		});
	}

}