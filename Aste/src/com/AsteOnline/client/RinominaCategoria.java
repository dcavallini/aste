package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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

public class RinominaCategoria extends Composite implements HasText {

	private static RinominaCategoriaUiBinder uiBinder = GWT.create(RinominaCategoriaUiBinder.class);

	interface RinominaCategoriaUiBinder extends UiBinder<Widget, RinominaCategoria> {
	}

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	
	public RinominaCategoria() {
		initWidget(uiBinder.createAndBindUi(this));
		
		final ValueListBox<String> categoria = new ValueListBox<String>();
		categoria.getElement().getStyle().setBackgroundColor("#f1f1f1");
		categoria.getElement().getStyle().setWidth(1255, Unit.PX);
		categoria.getElement().getStyle().setPadding(10, Unit.PX);
		
		final ArrayList<String> nomiCategorie = new ArrayList<String>();
		
		final ArrayList<Categoria> totCategorie = new ArrayList<Categoria>();
		
		//popolo la listbox con le categorie
		greetingService.inizializzazioneCategorie(new AsyncCallback<ArrayList<Categoria>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Categoria> result) {

				for(int i = 0; i < result.size(); i++) {
					String tmp = result.get(i).getCategoria();
					nomiCategorie.add(tmp);
					totCategorie.add(result.get(i));
				}
				

				categoria.setAcceptableValues(nomiCategorie);
				final Label labCat = new Label("Seleziona la categoria: ");
				RootPanel.get().add(labCat);
				RootPanel.get().add(categoria);

				
				final Label nomeCategoria = new Label();
				nomeCategoria.setText("Inserisci il nome della categoria da rinominare:");
				
				final TextBox txt_nomeCategoria = new TextBox();
				txt_nomeCategoria.getElement().setPropertyString("placeholder", "Rinomina la categoria");
				final Button invia = new Button();
				invia.setText("Aggiungi");
				
				
				
				invia.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						
						int profondita =0;
						for (int i=0; i<totCategorie.size(); i++) {
							if(categoria.getValue().toString().equals(totCategorie.get(i).getCategoria())) {
								profondita=totCategorie.get(i).getProfondita();
							}
						}
						//rinomino la categoria
						greetingService.rinominaCategoria(categoria.getValue().toString(), txt_nomeCategoria.getText().toString(), profondita, new AsyncCallback <Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Impossibile rinominare la categoria");
							}

							@Override
							public void onSuccess(Boolean result) {
								if(!result) {
									Window.alert("Impossibile rinominare la categoria");
									
								} else {
									Window.alert("Categoria rinominata con successo");
								}
							}
							
						});
					}
								
				});
				
				RootPanel.get().add(nomeCategoria);
				RootPanel.get().add(txt_nomeCategoria);
				RootPanel.get().add(invia);
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
