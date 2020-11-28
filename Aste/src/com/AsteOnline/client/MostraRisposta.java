package com.AsteOnline.client;

import com.AsteOnline.shared.Risposta;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ParagraphElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MostraRisposta extends Composite {

	private static MostraRispostaUiBinder uiBinder = GWT.create(MostraRispostaUiBinder.class);

	interface MostraRispostaUiBinder extends UiBinder<Widget, MostraRisposta> {
	}

	@UiField ParagraphElement risposta;

	
	public MostraRisposta(Risposta answer) {
		initWidget(uiBinder.createAndBindUi(this));
		risposta.setInnerText(answer.getContenuto());
	}
	
	
	

}
