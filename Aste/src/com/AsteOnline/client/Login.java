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

	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	@UiField InputElement username, password;
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);
	
	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Button login = new Button("Invia");
		RootPanel.get().add(login);
		
		login.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
					greetingService.richiestaAccessoUtente(username.getValue().toString(), password.getValue().toString(), new AsyncCallback<Utente>() {
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Errore nel server"); //in GreetingServiceImpl
						
					}

					@Override
					public void onSuccess(Utente result) {
						
						if(result == null) {
							
							Window.alert("Nessuna combinazione username - password trovata");
							
						}else {
							Window.alert("LogIn eseguito con successo");
							Window.alert("Benvenuto "+result.getUsername());
							
							RootPanel.get().clear();
							
							MenuUtenteRegistrato m = new MenuUtenteRegistrato(result.getUsername());
							RootPanel.get().add(m);
							
						} // qui va istanziato il menu per l'utente loggato e gli va passato l'username
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
