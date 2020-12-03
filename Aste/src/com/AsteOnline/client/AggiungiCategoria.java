package com.AsteOnline.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class AggiungiCategoria extends Composite implements HasText {

	private static AggiungiCategoriaUiBinder uiBinder = GWT.create(AggiungiCategoriaUiBinder.class);

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);
	
	interface AggiungiCategoriaUiBinder extends UiBinder<Widget, AggiungiCategoria> {
	}

	public AggiungiCategoria() {
		initWidget(uiBinder.createAndBindUi(this));	
		
		//metodo di controllo 
		greetingService.tmp(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub

				final Label l = new Label();
				
				l.setText("Aggiungi categoria : ");
				
				final TextBox txtNuovaCategoria = new TextBox();
				txtNuovaCategoria.getElement().setPropertyString("placeholder", "Inserisci la categoria");
				final Button invia = new Button();
				invia.setText("Inserisci");
				
				RootPanel.get().add(l);
				RootPanel.get().add(txtNuovaCategoria);
				RootPanel.get().add(invia);
				
				invia.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
										
						//metodo per aggiungere le categorie al db
						greetingService.addCategoria(txtNuovaCategoria.getValue(), new AsyncCallback <Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								//errore nel server
							}

							@Override
							public void onSuccess(Boolean result) {
								if(!result) {
									Window.alert("Impossibile aggiungere la categoria");
									
								} else {
									Window.alert("Catoria aggiunta con successo");
								}
							}
							
						});
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
