package com.AsteOnline.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ButtonElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.Widget;

public class signIn extends Composite {

	private static signInUiBinder uiBinder = GWT.create(signInUiBinder.class);

	interface signInUiBinder extends UiBinder<Widget, signIn> {
	}

	@UiField InputElement username, nome, cognome, cell, password, email, indirizzo, cf, sesso, dataNascita, luogoNascita;
	//@UiField SubmitButton invia;
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);
	
	//per lavorare sui dati lavora qui dentro
	public signIn() {
		initWidget(uiBinder.createAndBindUi(this));
		Button invia = new Button("Salva");
		RootPanel.get().add(invia);
		
		invia.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
			
				//tutti i controlli per i parametri, se vanno a buon fine chiami il metodo sotto
				
				//quando clicco prendo i valori inseriti 
				greetingService.addUser(username.getValue().toString(), nome.getValue().toString(), cognome.getValue().toString(),cell.getValue().toString(), password.getValue().toString(), email.getValue().toString(), cf.getValue().toString(), indirizzo.getValue().toString(),new AsyncCallback<String>() { //

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Errore nel server"); //in GreetingServiceImpl
						
					}

					@Override
					public void onSuccess(String result) {
						Window.alert(result);
						//refresh 
						AsteOnline aste=new AsteOnline();
						aste.apriPaginaSignIn();
					}
					
				});
			}
			
		});
		
	}

}
