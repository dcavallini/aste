package com.AsteOnline.client;

import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;

import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Risposta;

public class VisualizzaProfilo extends Composite {
	
	Utente utenteLoggato = new Utente();
	String nomeUtente = "";

	private static VisualizzaProfiloUiBinder uiBinder = GWT.create(VisualizzaProfiloUiBinder.class);

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	interface VisualizzaProfiloUiBinder extends UiBinder<Widget, VisualizzaProfilo> {
	}
	
	@UiField ParagraphElement user, nome, cognome, email, cell, indirizzo, cf, 
	sesso, luogoNascita, dataNascita, oggettiVenduti, statoAsta;
	
	public VisualizzaProfilo(String username) {
        initWidget(uiBinder.createAndBindUi(this));
        
        nomeUtente = username;
        
        //prendo tutte le informazioni dell'utente sulla base dell'username inserito
        greetingService.infoUtente(username, new AsyncCallback<Utente>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Non e&grave possibile visualizzare i dati dell'utente");
            }

            @Override
            public void onSuccess(Utente result) {
                
                if(result.getUsername()== null || result.getNome()==null ) {
                    Window.alert("Username inesistente");
                } else {
                	//se esiste
                    utenteLoggato=result;
                    user.setInnerText("Username: "+result.getUsername());
                    nome.setInnerText("Nome: "+result.getNome());
                    cognome.setInnerText("Cognome: "+result.getCognome());
                    cell.setInnerText("Telefono: "+result.getCell());
                    email.setInnerText("Email: "+result.getEmail());
                    cf.setInnerText("Codice fiscale: "+result.getCod_fiscale());
                    indirizzo.setInnerText("Indirizzo: "+result.getIndirizzo());
                    sesso.setInnerText("Sesso: " + result.getSesso());
                    dataNascita.setInnerText("Data di nascita: " + result.getDataNascita());
                    luogoNascita.setInnerText("Luogo di nascita: " + result.getLuogoNascita());

                }
            }
            
        });
        //mostro gli oggetti venduti
        greetingService.oggettiVenduti(utenteLoggato, new AsyncCallback<ArrayList<Oggetto>>() {

            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Non e&grave stato possibile prelevare gli oggetti");
            }

            @Override
            public void onSuccess(ArrayList<Oggetto> result) {
            	
                for(int i=0; i<result.size(); i++) {
                    VisualizzaOggettiVenduti visualizzaOggettiVenduti = new VisualizzaOggettiVenduti(result.get(i)); 
                    RootPanel.get().add(visualizzaOggettiVenduti);
                }
            }
            
        });
        
        //mostro le domande effettuate all'utente
        greetingService.viewDomandaPerUtente(nomeUtente, new AsyncCallback<ArrayList<Domanda>>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(ArrayList<Domanda> result) {
				if(result == null) {
					final Label l = new Label();
					
					l.setText("Non ci sono domande effettuate da questo utente");
					
					RootPanel.get().add(l);
				} else {
					final Label l = new Label();
					
					l.setText("Domande: ");
					
					RootPanel.get().add(l);
					
					for(int i = 0; i < result.size(); i++) {
						Label d = new Label();
						d.getElement().setInnerHTML("Contenuto: " + result.get(i).getContenuto() + "<br>" + 
								"Oggetto Relativo: " + result.get(i).getOggetto().getNome() + "<br><br>");
						
						RootPanel.get().add(d);
					}
					
				}
			}
        	
        });
        
        //mostro le risposte effettuate dall'utente
        greetingService.viewRispostaPerUtente(nomeUtente, new AsyncCallback<ArrayList<Risposta>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore");
			}

			@Override
			public void onSuccess(ArrayList<Risposta> result) {
				
				if(result == null) {
					final Label l = new Label();
					
					l.setText("Non ci sono risposte effettuate da questo utente");
					
					RootPanel.get().add(l);
				} else {
					
					Window.alert("Ci sono delle rispote");
					
					final Label l = new Label();
					
					l.setText("Risposte: ");
					
					RootPanel.get().add(l);
					
					for(int i = 0; i < result.size(); i++) {
						Window.alert(result.get(i).getContenuto());
						Label d = new Label();
						d.getElement().setInnerHTML("Contenuto: " + result.get(i).getContenuto() + "<br>" + 
								"Domanda relativa: " + result.get(i).getDomanda().getContenuto() + "<br><br>");
						
						RootPanel.get().add(d);
					}
				}
				
			}
        	
        });
    }

}
