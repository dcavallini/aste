package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Risposta;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class VisualizzaRisposta extends Composite implements HasText {

	private static VisualizzaRispostaUiBinder uiBinder = GWT.create(VisualizzaRispostaUiBinder.class);

	public Risposta risposta = new Risposta();

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	interface VisualizzaRispostaUiBinder extends UiBinder<Widget, VisualizzaRisposta> {
	}

	public VisualizzaRisposta() {
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
//					for(int n=0; n<result.size(); n++) {
//						final Label w = new Label();
//						final Label spazio = new Label();
//						final Label doppioSpazio = new Label();
//						final Label prezzoAttuale = new Label();
//
//						w.getElement().setInnerHTML("Nome : " + result.get(n).getTitolo() + "<br>" +
//								"Descrizione : " + result.get(n).getContenuto() + "<br>" +
//								"Oggetto : " + result.get(n).getOggetto().getNome() + "<br>");
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
