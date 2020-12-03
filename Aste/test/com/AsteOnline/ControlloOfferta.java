package com.AsteOnline;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.AsteOnline.shared.Offerta;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;

class ControlloOfferta {

	@Test
	void test() {
	Utente utente = new Utente("stefania", "stefania", "rizzo", "3333333", "stefania", "aaaaaa@gmail.com", "jchsdufhe", "via", "F", "data", "luogo");
	Oggetto oggetto = new Oggetto(utente, "", "pc", "nuovo", 200.00, "Elettronica");
	double importo = 300;
	
	Offerta offerta = new Offerta(oggetto, utente, importo);
	String output1= offerta.getUtente().getUsername();
	assertEquals(utente.getUsername(), output1);
	String output2=offerta.getOggetto().getNome();
	assertEquals(oggetto.getNome(), output2);
	double output3=offerta.getImporto();
	assertEquals(importo, output3);
	}

}
