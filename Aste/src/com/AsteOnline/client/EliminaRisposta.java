package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Domanda;
import com.AsteOnline.shared.Risposta;
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
import com.google.gwt.user.client.ui.Widget;

public class EliminaRisposta extends Composite implements HasText {

	private static EliminaRispostaUiBinder uiBinder = GWT.create(EliminaRispostaUiBinder.class);

	interface EliminaRispostaUiBinder extends UiBinder<Widget, EliminaRisposta> {
	}
	
	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	public EliminaRisposta() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		greetingService.viewRisposta(new AsyncCallback<ArrayList<Risposta>>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				Window.alert("Errore nel server"); //in GreetingServiceImpl
//
//			}
//
//			//livello base, bisogna capire cosa succede quando ci sono gli oggetti,
//			//mancano ancora delle cose per creare intermanete l'oggetto
//			//da guardare come mettere i bottoni per fare un'offerta e rifare bene il costruttore per oggetto, cambiare anche
//			//il metodo della messa in vendita
//			@Override
//			public void onSuccess(ArrayList<Risposta> result) {
//				//
//
//				//if(result.getNome()==null || result.getDescrizione()==null || String.valueOf(result.getPrezzoBase())==null) {
//				//Window.alert("Oggetto inesistente");
//				//} else {
//				//al posto dell'alert mostra l'oggetto
//				if(result == null) {
//					Window.alert("Nessun oggetto esistente");
//				}
//				else {
//					//result.forEach((n) -> {
//					//Window.alert(n.getNome());
//					
//					for(int n=0; n<result.size(); n++) {
//						final Label w = new Label();
//						final Button b = new Button();
//						b.setText("Elimina");
//						
//						Risposta r = result.get(n);
//						
//						w.getElement().setInnerHTML("Nome : " + result.get(n).getTitolo() + "<br>" +
//								"Descrizione : " + result.get(n).getContenuto() + "<br>" +
//								"Oggetto : " + result.get(n).getOggetto().getNome() + "<br>");
//						
//						b.addClickHandler(new ClickHandler() {
//
//							@Override
//							public void onClick(ClickEvent event) {
//								// TODO Auto-generated method stub
//								
//								greetingService.eliminaRisposta(r, new AsyncCallback<Boolean>() {
//
//									@Override
//									public void onFailure(Throwable caught) {
//										// TODO Auto-generated method stub
//										
//									}
//
//									@Override
//									public void onSuccess(Boolean result) {
//										// TODO Auto-generated method stub
//										
//										Window.alert("Oggetto eliminato con successo");
//										
//										RootPanel.get().clear();
//										
//										EliminaDomanda el = new EliminaDomanda();
//										RootPanel.get().add(el);
//										
//									}
//									
//								});
//								
//							}
//							
//						});
//					}
//				}
//			}
//		});
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
