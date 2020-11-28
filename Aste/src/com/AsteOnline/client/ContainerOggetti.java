 package com.AsteOnline.client;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Risposta;
import com.AsteOnline.shared.Utente;

import java.util.ArrayList;

import com.AsteOnline.shared.Asta;
import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Offerta;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.ParagraphElement;
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

public class ContainerOggetti extends Composite {

	private static ContainerOggettiUiBinder uiBinder = GWT.create(ContainerOggettiUiBinder.class);

	interface ContainerOggettiUiBinder extends UiBinder<Widget, ContainerOggetti> {
	}
	Utente utenteGlobale = new Utente();
	Oggetto oggGlobale = new Oggetto();

	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);


	@UiField ParagraphElement nomeOggetto, descrizioneOggetto, prezzoOggetto, scadenzaOggetto, categoriaOggetto, ultimaOfferta;
	@UiField InputElement question, importo;
	//@UiField Button inviaDomanda;
	
	public ContainerOggetti() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public ContainerOggetti(Oggetto oggetto, Utente utente) {
		initWidget(uiBinder.createAndBindUi(this));
		oggGlobale = oggetto;
		utenteGlobale = utente;
		
		nomeOggetto.setInnerText("Nome: "+oggetto.getNome());
		descrizioneOggetto.setInnerText("Descrizione: "+ oggetto.getDescrizione());
		prezzoOggetto.setInnerText("Prezzo iniziale: "+oggetto.getPrezzoBase());
		//scadenzaOggetto.setInnerText("Scade in data: "+oggetto.getAsta().getScadenza());
		categoriaOggetto.setInnerText("Categoria: "+oggetto.getCategoria());
		ultimaOfferta.setInnerText(Double.toString(oggetto.getPrezzoBase()));
		
		
		final Button domanda = new Button("Invia domanda");
		RootPanel.get().add(domanda);
		domanda.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				greetingService.inviaDomanda(oggGlobale, utenteGlobale, question.getValue().toString().trim(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Non sono riuscito a inviare la domanda");
					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							Window.alert("Domanda inviata");
							MenuUtenteRegistrato menu = new MenuUtenteRegistrato(utenteGlobale);
							menu.apriContainer(oggGlobale, utenteGlobale);
						}
					}
					
				});
			}
				
		});
		
		
		greetingService.viewDomanda(oggGlobale.getIdOggetto(), new AsyncCallback<ArrayList<Domanda>>() {

			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Non sono riuscito a prendere le domande");
			}

			@Override
			public void onSuccess(ArrayList<Domanda> result) {
				for(int i=0; i<=result.size(); i++) {
					MostraDomanda md = new MostraDomanda(result.get(i), utenteGlobale);
					RootPanel.get().add(md);
				}
			}

		});
		
		
		final Button offri = new Button("Offri");
		RootPanel.get().add(offri);
	
		
		offri.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
//				if(ultimaOfferta.getInnerText().equals("Ultimo prezzo : ")) {
//					
//					
//				} else {
//					if(Double.parseDouble(prezzoOggetto.getInnerText()) < Double.parseDouble(importo.getValue())) {
//						greetingService.inserisciOfferta(oggGlobale, utenteGlobale, Double.parseDouble(importo.getValue()), new AsyncCallback<Boolean>() {
//
//							@Override
//							public void onFailure(Throwable caught) {
//								Window.alert("Non sono riuscito a inviare l'offerta");
//							}
//
//							@Override
//							public void onSuccess(Boolean result) {
//								if(result) {
//									Window.alert("Offerta inviata");
//									
//									ultimaOfferta.setInnerText(importo.getValue().toString());
//								
//								}
//							}
//						});
//					}
//				}
				
				
//				
//				if(Double.parseDouble(ultimaOfferta.getInnerText()) < Double.parseDouble(importo.getValue())) {
//
//				}
				
				if(Double.parseDouble(ultimaOfferta.getInnerText().trim()) > Double.parseDouble(importo.getValue())) {
					Window.alert("Impossibile inserire un'offerta più bassa");
				}
				else
				{
					greetingService.inserisciOfferta(oggGlobale, utenteGlobale, Double.parseDouble(importo.getValue()), new AsyncCallback<Boolean>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Non sono riuscito a inviare l'offerta");
						}

						@Override
						public void onSuccess(Boolean result) {
							if(result) {
								Window.alert("Offerta inviata");
								
								ultimaOfferta.setInnerText(importo.getValue().toString());
							
							}
						}
					});
				}
				
				
			}
				
		});
			
	}

}
