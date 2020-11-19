package com.AsteOnline.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class Domanda extends Composite implements HasText {

	private static DomandaUiBinder uiBinder = GWT.create(DomandaUiBinder.class);

	interface DomandaUiBinder extends UiBinder<Widget, Domanda> {
	}

	public Domanda(String nomeOggetto, String domanda) {
		initWidget(uiBinder.createAndBindUi(this));
		
		Window.alert("Ho cambiato classe");
		Window.alert(nomeOggetto + "Domanda : " + domanda);
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
