package com.AsteOnline;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;

class ControlloOggetto {

	@Test
	void test() {
		Utente utente = new Utente("stefania", "stefania", "rizzo", "3333333", "stefania", "aaaaaa@gmail.com", "jchsdufhe", "via", "F", "data", "luogo");
		String id="jsahdw";
		String nome = "pc";
		String descrizione = "nuovo";
		double prezzoBase= 350.99;
		String categoria = "Elettronica";
		
		Oggetto oggetto = new Oggetto(utente, id, nome, descrizione, prezzoBase, categoria);
		String output1= oggetto.getUtente().getUsername();
		assertEquals(utente.getUsername(), output1);
		assertEquals(id, oggetto.getIdOggetto());
		assertEquals(prezzoBase, oggetto.getPrezzoBase());
	}

}
