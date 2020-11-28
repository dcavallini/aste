package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class EliminaCategoria extends Composite implements HasText {

	private static EliminaCategoriaUiBinder uiBinder = GWT.create(EliminaCategoriaUiBinder.class);
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	interface EliminaCategoriaUiBinder extends UiBinder<Widget, EliminaCategoria> {
	}

	public EliminaCategoria() {
		initWidget(uiBinder.createAndBindUi(this));
		
		final ValueListBox<String> categoria = new ValueListBox<String>();

		final ArrayList<String> nomiCategorie = new ArrayList<String>();
		
		final ArrayList<Categoria> totCategorie = new ArrayList<Categoria>();

		greetingService.inizializzazioneCategorie(new AsyncCallback<ArrayList<Categoria>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Categoria> result) {
				// TODO Auto-generated method stub


				for(int i = 0; i < result.size(); i++) {
					String tmp = result.get(i).getCategoria();
					nomiCategorie.add(tmp);
					totCategorie.add(result.get(i));
				}
				

				categoria.setAcceptableValues(nomiCategorie);
			}
		});

		RootPanel.get().add(categoria);
		
		final Button invia = new Button();
		invia.setText("Elimina");
		
		
		
		invia.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				Categoria c = new Categoria();
				for(int i = 0; i < totCategorie.size(); i++) {
					if(totCategorie.get(i).getCategoria().equals(categoria.getValue())) {
						c = totCategorie.get(i);
					}	
				}
				
				greetingService.eliminaCategoria(c, new AsyncCallback <Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(Boolean result) {
						Window.alert("Categoria eliminata con successo");
					}
					
				});
			}
						
		});
		
		RootPanel.get().add(invia);
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
