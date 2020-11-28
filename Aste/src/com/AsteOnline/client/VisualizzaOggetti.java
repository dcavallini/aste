package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
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

	public VisualizzaOggetti(Utente utenteParametro) {
		initWidget(uiBinder.createAndBindUi(this));

		final ValueListBox<String> categoria = new ValueListBox<String>();

		final ArrayList<String> nomiCategorie = new ArrayList<String>();
		
		final ArrayList<Categoria> tutteCategorie = new ArrayList<Categoria>();

		utente = utenteParametro;
		
		final HorizontalPanel hp = new HorizontalPanel();

		
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
					tutteCategorie.add(result.get(i));
					nomiCategorie.add(tmp);
				}


				categoria.setAcceptableValues(nomiCategorie);
			}
		});

		RootPanel.get().add(categoria);


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
						Hyperlink hl = new Hyperlink(result.get(n).getNome(), result.get(n).getIdOggetto());
						RootPanel.get().add(hl);
						
						
						/*
						final Label w = new Label();
						final Label spazio = new Label();
						final Label doppioSpazio = new Label();
						final Label prezzoAttuale = new Label();


						final Button domanda = new Button();
						domanda.setText("Domanda");
						final TextBox txtDomanda = new TextBox();


						final Button offri = new Button();
						offri.setText("Offri");
						final TextBox txtOfferta = new TextBox();
						

						w.getElement().setInnerHTML("Nome : " + result.get(n).getNome() + "<br>" + 
								"Descrizione : " + result.get(n).getDescrizione() + "<br>" +
								"Categoria : " + result.get(n).getCategoria().getCategoria() + "<br>");

						prezzoAttuale.getElement().setInnerHTML("prezzo attuale: "+result.get(n).getPrezzoBase() + "<br><br>");

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
											hp.add(labelDomanda);
											
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
									Window.alert("Devi inserire un prezzo pi&ugrave; alto del prezzo attuale");
								}
							}

						});

						RootPanel.get().add(w);
						RootPanel.get().add(prezzoAttuale);
						RootPanel.get().add(domanda);
						RootPanel.get().add(txtDomanda);
						RootPanel.get().add(spazio);
						RootPanel.get().add(offri);
						RootPanel.get().add(txtOfferta);
						RootPanel.get().add(doppioSpazio);
						RootPanel.get().add(hp);
							*/
					}
				}


			}

		});

		categoria.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				
				Categoria c = new Categoria();
				
				for(int i = 0; i < tutteCategorie.size(); i++){
					
					if(tutteCategorie.get(i).getCategoria().equals(categoria.getValue())) {
						c = tutteCategorie.get(i);
					}
					
				}
				
				if(categoria.getValue() == null) {
					c = null;
				}

				greetingService.filtroPerCategoria(c, new AsyncCallback<ArrayList<Oggetto>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ArrayList<Oggetto> result) {
						// TODO Auto-generated method stub

						//Window.alert(categoria.getValue());
						RootPanel.get().clear();

						if(result.size() == 0) {

							RootPanel.get().add(categoria);

							Window.alert("Non ci sono oggetti corrispondeti per la categoria");
						} else {
							RootPanel.get().clear();

							RootPanel.get().add(categoria);

							final String s = categoria.getValue();
							
							
							categoria.setValue(s);
							
							if(s == null) {
								
								categoria.setValue(null);
								
							}
							
							for(int n=0; n<result.size(); n++) {
							
								Hyperlink hl = new Hyperlink(result.get(n).getNome(), result.get(n).getIdOggetto());
								RootPanel.get().add(hl);
								
								//if(result.get(n).getCategoria().getCategoria().equals(s) || s == null) {
							/*	
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
											"Categoria : " + result.get(n).getCategoria().getCategoria() + "<br>");

									prezzoAttuale.getElement().setInnerHTML("prezzo attuale: "+result.get(n).getPrezzoBase() + "<br><br>");

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
												Window.alert("Devi inserire un prezzo pi&ugrave; alto del prezzo attuale");
											}
										}

									});

									RootPanel.get().add(w);
									RootPanel.get().add(prezzoAttuale);
									RootPanel.get().add(domanda);
									RootPanel.get().add(txtDomanda);
									RootPanel.get().add(spazio);
									RootPanel.get().add(offri);
									RootPanel.get().add(txtOfferta);
									RootPanel.get().add(doppioSpazio);

								//}
									*/
							}
							
//							greetingService.visualzzaOggetti(new AsyncCallback<ArrayList<Oggetto>>() {
//
//								@Override
//								public void onFailure(Throwable caught) {
//									Window.alert("Errore nel server"); //in GreetingServiceImpl
//
//								}
//
//								//livello base, bisogna capire cosa succede quando ci sono gli oggetti,
//								//mancano ancora delle cose per creare intermanete l'oggetto
//								//da guardare come mettere i bottoni per fare un'offerta e rifare bene il costruttore per oggetto, cambiare anche 
//								//il metodo della messa in vendita
//								@Override
//								public void onSuccess(ArrayList<Oggetto> result) {
//									
//									RootPanel.get().clear();
//									RootPanel.get().add(categoria);
//
//									//if(result.getNome()==null || result.getDescrizione()==null || String.valueOf(result.getPrezzoBase())==null) {
//									//Window.alert("Oggetto inesistente");
//									//} else {
//									//al posto dell'alert mostra l'oggetto
//									if(result == null) {
//										Window.alert("Nessun oggetto esistente");
//									}
//									else {
//										//result.forEach((n) -> {
//										//Window.alert(n.getNome());
//
//									}
//									
//
//								}
//
//							});
						}

					}

				});

			}

		});


	}

}
