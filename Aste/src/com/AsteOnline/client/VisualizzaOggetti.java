package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class VisualizzaOggetti extends Composite {
	
	public Utente utente;
	public Oggetto oggetto = new Oggetto();
	
	
	private static VisualizzaOggettiUiBinder uiBinder = GWT.create(VisualizzaOggettiUiBinder.class);

	interface VisualizzaOggettiUiBinder extends UiBinder<Widget, VisualizzaOggetti> {
	}

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	//	@UiField
	//	TableColElement nome, descrizione, prezzo, dataScadenza, categoria;


	public VisualizzaOggetti() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	public VisualizzaOggetti(String username) {
		initWidget(uiBinder.createAndBindUi(this));
		
		greetingService.infoUtente(username,new AsyncCallback<Utente>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert("Impossibile prendere informazioni sull'utente");
				
			}

			@Override
			public void onSuccess(Utente result) {
				// TODO Auto-generated method stub
				utente = result;
				
			}
			
		});

		//String div = "<div id=\"tabella\"></div>";


		greetingService.visualzzaOggetti(new AsyncCallback<ArrayList<Oggetto>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore nel server"); //in GreetingServiceImpl

			}

			//livello base, bisogna capire cosa succede quando ci sono gli oggetti,
			//mancano ancora delle cose per creare intermanete l'oggetto
			//da guardare come mettere i bottoni per fare un'offerta e rifare bene il costruttore per oggetto, cambiare anche 
			//il metodo della messa in vendita
			@Override
			public void onSuccess(ArrayList<Oggetto> result) {
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
						final Label spazio = new Label();
						final Label doppioSpazio = new Label();
						final Label prezzoAttuale = new Label();
					
						
						final Button domanda = new Button();
						domanda.setText("Domanda");
						final TextBox txtDomanda = new TextBox();

						
						Button offri = new Button();
						offri.setText("Offri");
						final TextBox txtOfferta = new TextBox();
						
						w.getElement().setInnerHTML("Nome : " + result.get(n).getNome() + "<br>" + 
								"Descrizione : " + result.get(n).getDescrizione() + "<br>" +
								"Prezzo : " + result.get(n).getPrezzoBase() + 
								"Venditore"+(result.get(n).getUtente()).getNome()+"<br><br>");
						
						prezzoAttuale.getElement().setInnerHTML("prezzo attuale: "+result.get(n).getPrezzoBase());
						
						spazio.getElement().setInnerHTML("<br>");
						doppioSpazio.getElement().setInnerHTML("<br><br>");

						oggetto = result.get(n);
						domanda.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {								
								//if(utente.getUsername())
								greetingService.inviaDomanda(oggetto, utente, txtDomanda.getText(), new AsyncCallback<Boolean>() {

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Impossibile inviare la domanda");
											
										}

										@Override
										public void onSuccess(Boolean result) {
											if(result) {
												final Label labelDomanda = new Label();

												labelDomanda.getElement().setInnerHTML(txtDomanda.getText());
												RootPanel.get().add(labelDomanda);

												Window.alert(utente.getUsername()+" "+oggetto.getUtente().getUsername());

											}
										}
									
										
									});
						
							}

						});
						
						offri.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								Window.alert("Offro per l'oggetto : " + oggetto.getNome());
								
								
								String prezzoDaConfrontare = prezzoAttuale.getText();
								double prezzoEffettivo = Double.parseDouble(prezzoDaConfrontare.substring(16));
								
								if(Double.parseDouble(txtOfferta.getValue())>prezzoEffettivo) {
									
								
								greetingService.inserisciOfferta(oggetto, utente, Double.parseDouble(txtOfferta.getValue()), new AsyncCallback<Boolean>() {
									@Override
									public void onFailure(Throwable caught) {
										// TODO Auto-generated method stub
										Window.alert("Errore nell'inserimento dell'offerta");
									}

									@Override
									public void onSuccess(Boolean result) {
										// TODO Auto-generated method stub
										
										if(result) {
											Window.alert("Offerta inserita");
											prezzoAttuale.getElement().setInnerHTML("prezzo attuale: " +txtOfferta.getValue());
											//MenuUtenteRegistrato mur = new MenuUtenteRegistrato(utente.getUsername());
											//mur.apriVisualizzaOggetti();
											
										} else {
											Window.alert("Offerta non inserita");
										}
										
									}
									
								});
							}
								else {
									Window.alert("Devi inserire un prezzo più alto del prezzo attuale");
								}
							}
							
						});


						RootPanel.get().add(w);
						RootPanel.get().add(domanda);
						RootPanel.get().add(txtDomanda);
						RootPanel.get().add(spazio);
						RootPanel.get().add(offri);
						RootPanel.get().add(txtOfferta);
						RootPanel.get().add(doppioSpazio);
						RootPanel.get().add(prezzoAttuale);
						
					}
				}

				
			}

		});


	}

}
