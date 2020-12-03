package com.AsteOnline.client;

import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite implements HasText {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	Utente utente = new Utente();
	
	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	@UiField InputElement username, password;
	@UiField Button login;
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);
	
	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
		
		login.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//inserisco username e password per accedere al sito
				greetingService.login(username.getValue().toString(), password.getValue().toString(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Impossibile effettuare il logIn");
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							if(username.getValue().toString().equals("admin")) {
								Utente u = new Utente();
								Utente admin = u.new Admin("admin", "admin");
								Window.alert("LogIn eseguito con successo");
								//Window.alert("Benvenuto "+username.getValue().toString());
								
								RootPanel.get().clear();
								
								MenuUtenteRegistrato m = new MenuUtenteRegistrato(admin);
								RootPanel.get().add(m);
							} else {
								//confronto i dati inseriti per il login se esistono nel db
								greetingService.infoUtente(username.getValue().toString(), new AsyncCallback<Utente>() {
									
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Dati utente non trovati");
									}

									@Override
									public void onSuccess(Utente result) {
										Window.alert("LogIn eseguito con successo");
										utente = result;
										RootPanel.get().clear();
										MenuUtenteRegistrato m = new MenuUtenteRegistrato(utente);
										RootPanel.get().add(m);
										


									}

								});
							}
						} else {
							Window.alert("Utente non esistente");
						}
					}
					
				});
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
