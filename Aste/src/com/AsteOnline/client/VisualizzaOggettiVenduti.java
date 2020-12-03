package com.AsteOnline.client;

import com.AsteOnline.shared.Oggetto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class VisualizzaOggettiVenduti extends Composite {

	private static VisualizzaOggettiVendutiUiBinder uiBinder = GWT.create(VisualizzaOggettiVendutiUiBinder.class);

	@UiField DivElement container;

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);

	interface VisualizzaOggettiVendutiUiBinder extends UiBinder<Widget, VisualizzaOggettiVenduti> {
	}

	public VisualizzaOggettiVenduti() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	public VisualizzaOggettiVenduti(Oggetto oggetto) {
		initWidget(uiBinder.createAndBindUi(this));
		
		final Element nomeOggetto= DOM.createLabel();
		
		nomeOggetto.setInnerHTML("<p>"+oggetto.getNome()+"</p>");
		
		final Element statoAsta = DOM.createLabel();
		
		//controllo che l'asta non sia scaduta
		greetingService.statoOggetto(oggetto, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("Non sono riuscito a prendere lo stato dell'oggetto");
			}

			@Override
			public void onSuccess(String result) {
				statoAsta.setInnerHTML("<p>"+ result+ "</p>");
			}
		});
		container.appendChild(nomeOggetto);
		container.appendChild(statoAsta);
		
	}


}