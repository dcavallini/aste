package com.AsteOnline.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Amministrazione extends Composite {

	private static AmministrazioneUiBinder uiBinder = GWT.create(AmministrazioneUiBinder.class);

	interface AmministrazioneUiBinder extends UiBinder<Widget, Amministrazione> {
	}

	public Amministrazione() {
	
		
		initWidget(uiBinder.createAndBindUi(this));
		
		History.newItem("MenuAdmin");

		hyperLinks();

		History.addValueChangeHandler(new ValueChangeHandler<String> () {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				// TODO Auto-generated method stub
				String tokenAttuale = event.getValue(); //prendo il token attuale
				if(tokenAttuale.equals("sottoCategoria")) {
					aggiungiSottoCategoria();
				} else if(tokenAttuale.equals("categoria")) {
					aggiungiCategoria();
				}
				else if(tokenAttuale.equals("delOggetto")) {
					eliminaOggetto();
				}else if(tokenAttuale.equals("delOf")) {
					eliminaOfferta();
				}else if(tokenAttuale.equals("delCat")) {
					eliminaCategoria();
				}else if(tokenAttuale.equals("rinCat")) {
					rinominaCategoria();
				}else if(tokenAttuale.equals("delDom")) {
					eliminaDomanda();
				}
			}

		});
	}

	public void aggiungiSottoCategoria() {
		RootPanel.get().clear();
		AggiungiSottoCategoria add = new AggiungiSottoCategoria(); 
		RootPanel.get().add(add);
	}

	public void aggiungiCategoria() {
		RootPanel.get().clear();
		AggiungiCategoria add = new AggiungiCategoria();
		RootPanel.get().add(add);
	} 
	
	public void eliminaOggetto() {
		RootPanel.get().clear();
		EliminaOggetto add = new EliminaOggetto();
		RootPanel.get().add(add);
	} 
	
	public void eliminaOfferta() {
		RootPanel.get().clear();
		EliminaOfferta elof = new EliminaOfferta();
		RootPanel.get().add(elof);
	} 
	
	public void eliminaCategoria() {
		RootPanel.get().clear();
		EliminaCategoria elcat = new EliminaCategoria();
		RootPanel.get().add(elcat);
	} 
	
	public void rinominaCategoria() {
		RootPanel.get().clear();
		RinominaCategoria rincat = new RinominaCategoria();
		RootPanel.get().add(rincat);
	} 
	
	public void eliminaDomanda() {
		RootPanel.get().clear();
		EliminaDomanda delDom = new EliminaDomanda();
		RootPanel.get().add(delDom);
	}

	private void hyperLinks() {
		//definisci qui la tua homepage
		Hyperlink sottoCategoria = new Hyperlink("Aggiungi sotto categoria", "sottoCategoria");
		RootPanel.get().add(sottoCategoria);	
		
		Hyperlink categoria = new Hyperlink("Aggiungi categoria", "categoria");
		RootPanel.get().add(categoria);
		
		Hyperlink delOggetto = new Hyperlink("Elimina oggetto", "delOggetto");
		RootPanel.get().add(delOggetto);
		
		Hyperlink delOff = new Hyperlink("Elimina offerta", "delOf");
		RootPanel.get().add(delOggetto);
		
		Hyperlink delCat = new Hyperlink("Elimina categoria", "delCat");
		RootPanel.get().add(delCat);
		
		Hyperlink rinCat = new Hyperlink("Elimina offerta", "rinCat");
		RootPanel.get().add(rinCat);
		
		Hyperlink delDom = new Hyperlink("Elimina domanda", "delDom");
		RootPanel.get().add(delDom);
	}
	
	

}
