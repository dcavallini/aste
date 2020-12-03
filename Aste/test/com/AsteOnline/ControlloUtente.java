package com.AsteOnline;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.AsteOnline.shared.Utente;

class ControlloUtente {

	@Test
	void test() {
		String username="stefania";
		String nome="stefania";
		String cognome="rizzo";
		String numero="33333";
		String password="stefania";
		String email = "stefania@gmail.com";
		String cf="jdshsrf";
		String indirizzo="via bruno";
		String sesso ="F";
		String dataNascita="";
		String luogoNascita="";
		
		Utente utente = new Utente(username, nome,cognome, numero, password, email , cf, indirizzo, sesso, dataNascita,luogoNascita);
		assertEquals(utente.getUsername(), username);
		assertEquals(utente.getNome(), nome);
		assertEquals(utente.getCell(), numero);

	}

}
