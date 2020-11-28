package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.client.VisualizzaOggetti.VisualizzaOggettiUiBinder;
import com.AsteOnline.shared.Categoria;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class AggiungiSottoCategoria extends Composite {

	private static AggiungiSottoCategoriaUiBinder uiBinder = GWT.create(AggiungiSottoCategoriaUiBinder.class);

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);


	interface AggiungiSottoCategoriaUiBinder extends UiBinder<Widget, AggiungiSottoCategoria> {
	}

	public AggiungiSottoCategoria() {
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

		
		final Label nomeCategoria = new Label();
		nomeCategoria.setText("Inserisci il nome della sottocategoria");
		
		final TextBox txt_nomeCategoria = new TextBox();
		
		final Button invia = new Button();
		invia.setText("Aggiungi");
		
		
		
		invia.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				int profondita =0;
				Categoria c = new Categoria();
				for (int i=0; i<totCategorie.size(); i++) {
					if(categoria.getValue().toString().equals(totCategorie.get(i).getCategoria())) {
						profondita=totCategorie.get(i).getProfondita();
						c = totCategorie.get(i);
					}
				}
				
				greetingService.addSottoCategoria(c, txt_nomeCategoria.getText().toString(), profondita, new AsyncCallback <Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						
					}

					@Override
					public void onSuccess(Boolean result) {
						if(!result) {
							Window.alert("Impossibile aggiungere la sotto categoria");
							
						} else {
							Window.alert("Sotto categoria aggiunta con successo");
						}
					}
					
				});
			}
						
		});
		
		RootPanel.get().add(nomeCategoria);
		RootPanel.get().add(txt_nomeCategoria);
		RootPanel.get().add(invia);

	}

}
