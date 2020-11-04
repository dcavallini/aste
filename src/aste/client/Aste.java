package aste.client;

//import aste.shared.FieldVerifier;
//import aste.shared.Utente;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Aste implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
public void onModuleLoad() {		
		
		//i metodi devono rimanere vuoti
		greetingService.start(new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		homeInterface();
		
	}
	
	private void signInInterface() {
		
		clearRootPanel();
		
		Label lblUsername = new Label();
		lblUsername.setText("Inserisci il tuo username : ");
		
		Label lblNome = new Label();
		lblNome.setText("Inserisci il tuo nome : ");
		
		Label lblCognome = new Label();
		lblCognome.setText("Inserisci il tuo cognome : ");
		
		Label lblTelefono = new Label();
		lblTelefono.setText("Inserisci il tuo telefono : ");
		
		Label lblPsw = new Label();
		lblPsw.setText("Inserisci la password per l'accesso : ");
		
		Label lblEmail = new Label();
		lblEmail.setText("Inserisci la tua email : ");
		
		Label lblCf = new Label();
		lblCf.setText("Inserisci il tuo codice fiscale : ");
		
		Label lblIndirizzo = new Label();
		lblIndirizzo.setText("Inserisci il tuo indirizzo di residenza : ");
		
		Label lblSesso = new Label();
		lblSesso.setText("Inserisci il tuo sesso : ");
		
		Label lblDataNascita = new Label();
		lblDataNascita.setText("Inserisci la tua data di nascita :");
		
		Label lblLuogoNascita = new Label();
		lblLuogoNascita.setText("Inserisci il tuo luogo di nascita : ");
		
		
		TextBox txtUsername = new TextBox();
		TextBox txtNome = new TextBox();
		TextBox txtCognome = new TextBox();
		TextBox txtTelefono = new TextBox();
		TextBox txtPsw = new TextBox();
		TextBox txtEmail = new TextBox();
		TextBox txtCf = new TextBox();
		TextBox txtIndirizzo = new TextBox();
		TextBox txtSesso = new TextBox();
		TextBox txtDataNascita = new TextBox();
		TextBox txtLuogoNascita = new TextBox();
		
		Button btnRegistrati = new Button();
		btnRegistrati.setText("Registrati");
		
		Button btnIndietro = new Button();
		btnIndietro.setText("Indietro");
		

		RootPanel.get("1-1").add(lblUsername);
		RootPanel.get("1-2").add(txtUsername);
		
		RootPanel.get("2-1").add(lblNome);
		RootPanel.get("2-2").add(txtNome);
		
		RootPanel.get("3-1").add(lblCognome);
		RootPanel.get("3-2").add(txtCognome);
		
		RootPanel.get("4-1").add(lblTelefono);
		RootPanel.get("4-2").add(txtTelefono);
		
		RootPanel.get("5-1").add(lblPsw);
		RootPanel.get("5-2").add(txtPsw);
		
		RootPanel.get("6-1").add(lblEmail);
		RootPanel.get("6-2").add(txtEmail);
		
		RootPanel.get("7-1").add(lblCf);
		RootPanel.get("7-2").add(txtCf);
		
		RootPanel.get("8-1").add(lblIndirizzo);
		RootPanel.get("8-2").add(txtIndirizzo);
		
		RootPanel.get("9-1").add(lblSesso);
		RootPanel.get("9-2").add(txtSesso);
		
		RootPanel.get("10-1").add(lblDataNascita);
		RootPanel.get("10-2").add(txtDataNascita);
		
		RootPanel.get("11-1").add(lblLuogoNascita);
		RootPanel.get("11-2").add(txtLuogoNascita);
		
		RootPanel.get("12-1").add(btnRegistrati);
		RootPanel.get("12-2").add(btnIndietro);
		
		
		btnIndietro.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		          
		    	  homeInterface();
		    	  
		        }
		      });
		
		btnRegistrati.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
	    	  	  final DialogBox dialogBox = new DialogBox();
	    	      dialogBox.setText("Remote Procedure Call");
	    	      dialogBox.setAnimationEnabled(true);
	    	      final Button closeButton = new Button("Close");
	    	      // We can set the id of a widget by accessing its Element
	    	      closeButton.getElement().setId("closeButton");
	    	      final Label textToServerLabel = new Label();
	    	      final HTML serverResponseLabel = new HTML();
	    	      VerticalPanel dialogVPanel = new VerticalPanel();
	    	      dialogVPanel.addStyleName("dialogVPanel");
	    	      dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
	    	      dialogVPanel.add(textToServerLabel);
	    	      dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
	    	      dialogVPanel.add(serverResponseLabel);
	    	      dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
	    	      dialogVPanel.add(closeButton);
	    	      dialogBox.setWidget(dialogVPanel);
	    	      
	    	      closeButton.addClickHandler(new ClickHandler() {
	    	          public void onClick(ClickEvent event) {
	    	            dialogBox.hide();
	    	          }
	    	        });

		    	  
//		    	  greetingService.signIn(new Utente(
//		    			  
//		    			  txtUsername.getText(),
//		    			  txtNome.getText(),
//		    			  txtCognome.getText(),
//		    			  Integer.parseInt(txtTelefono.getText()),
//		    			  txtPsw.getText(),
//		    			  txtEmail.getText(),
//		    			  txtCf.getText(),
//		    			  txtIndirizzo.getText(),
//		    			  txtSesso.getText(),
//		    			  txtDataNascita.getText(),
//		    			  txtLuogoNascita.getText()
//		    			  
//		    			  ), new AsyncCallback<String>() {
//	
//					@Override
//					public void onFailure(Throwable caught) {
//						// TODO Auto-generated method stub
//						dialogBox.setText("Remote Procedure Call - Failure");
//			            serverResponseLabel.addStyleName("serverResponseLabelError");
//			            serverResponseLabel.setHTML(SERVER_ERROR);
//			            dialogBox.center();
//			            closeButton.setFocus(true);
//						
//					}
//	
//					@Override
//					public void onSuccess(String result) {
//						// TODO Auto-generated method stub
//						dialogBox.setText("Remote Procedure Call");
//			            serverResponseLabel.removeStyleName("serverResponseLabelError");
//			            serverResponseLabel.setHTML(result);
//			            dialogBox.center();
//			            closeButton.setFocus(true);
//					}
//		    		  
//		    	  });
//		    	  
		        }
		      });
		
	}
	
	private void homeInterface() {
		
		clearRootPanel();
		
		Button btnRegistrati = new Button();
		btnRegistrati.setText("Registrati");
		
		Button btnLogin = new Button();
		btnLogin.setText("Login");
		
		Button btnVisualizza = new Button();
		btnVisualizza.setText("Visualizza gli oggetti in vendita");
		
		
		RootPanel.get("1-1").add(btnRegistrati);
		RootPanel.get("2-1").add(btnLogin);
		RootPanel.get("3-1").add(btnVisualizza);
		
		
		btnRegistrati.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {

		    	  signInInterface();
		    	  
		        }
		      });
		
		btnLogin.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		          
		    	  loginInterface();
		    	  
		        }
		      });
		
		btnVisualizza.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  //da scrivere interfaccia per visualizzazione oggetti
		    	  //chiamata al server per chiedere tutti gli oggetti in asta atm
		    	  
		    	  visualizzaOggettiInVenditaInterface();
		    	  
		        }
		      });
		
	}
	
	private void loginInterface() {
		
		clearRootPanel();
		
		Label lblUsername = new Label();
		lblUsername.setText("Inserisci il tuo username : ");
		
		Label lblPsw = new Label();
		lblPsw.setText("Inserisci la tua password : ");
		
		
		TextBox txtUsername = new TextBox();
		
		TextBox txtPsw = new TextBox();
		
		
		Button btnLogin = new Button();
		btnLogin.setText("Login");
		
		Button btnIndietro = new Button();
		btnIndietro.setText("Indietro");
		
		
		RootPanel.get("1-1").add(lblUsername);
		RootPanel.get("1-2").add(txtUsername);
		
		RootPanel.get("2-1").add(lblPsw);
		RootPanel.get("2-2").add(txtPsw);
		
		RootPanel.get("3-1").add(btnLogin);
		RootPanel.get("3-2").add(btnIndietro);
		
		
		btnIndietro.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {

		    	  homeInterface();
		    	  
		        }
		      });
		
		btnLogin.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  if(txtUsername.getText().isEmpty() || txtPsw.getText().isEmpty()) {
		    		  
		    		  //do un errore che bisogna compilare correttamente i campi
		    		  
		    	  }else {
		    		  
		    		  String input = txtUsername.getText() + ";" + txtPsw.getText();
		    		  
		    		  menuInterface();
		    		  
			    	  //chiamo il server per il metodo di login
//		    		  greetingService.login(input,new AsyncCallback<Boolean>() {
//
//						@Override
//						public void onFailure(Throwable caught) {
//							// TODO Auto-generated method stub
//							
//						}
//
//						@Override
//						public void onSuccess(Boolean result) {
//							// TODO Auto-generated method stub
//							if(result) {
//								
//					    		  menuInterface();
//								
//							}
//							
//						}
//		    			  
//		    		  });
		    	  }
		    	  
		        }
		      });
		
	}
	
	private void menuInterface() {
		
		clearRootPanel();
		
		
		Button btnVisualizzaProfilo = new Button();
		btnVisualizzaProfilo.setText("Visualizza un profilo");
		
		Button btnVendi = new Button();
		btnVendi.setText("Metti un oggetto in vendita");
		
		//forse bottone per gestione categorie
		
		Button btnOffri = new Button();
		btnOffri.setText("Fai un'offerta per un oggetto");
		
		Button btnDomanda = new Button();
		btnDomanda.setText("Fai una domanda per un oggetto messo in vendita");
		
		Button btnRisposta = new Button();
		btnRisposta.setText("Rispondi a una domanda per un oggetto messo in vendita");
		
		
		Button btnVisualizza = new Button();
		btnVisualizza.setText("Visualizza gli oggetti messi in vendita");
		
		Button btnIndietro = new Button();
		btnIndietro.setText("Indietro");
		
		
		RootPanel.get("1-1").add(btnVisualizzaProfilo);
		
		RootPanel.get("2-1").add(btnVendi);
		
		RootPanel.get("3-1").add(btnOffri);
		
		RootPanel.get("4-1").add(btnDomanda);
		
		RootPanel.get("5-1").add(btnVisualizza);
		
		RootPanel.get("13-1").add(btnRisposta);
		
		RootPanel.get("7-1").add(btnIndietro);
		
		
		btnVisualizzaProfilo.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	 //da scrivere interfaccia per la visualizzazione del profilo
		    	 //basta chiedere username e vengono visualizzati i dati
		    	  
		    	  visualizzaProfiloInterface();
		    	  
		        }
		      });

		
		
		btnIndietro.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	 homeInterface();
		    	  
		        }
		      });
		
		
		btnVendi.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	 //da scrivere interfaccia per vendita
		    	 //id oggetto e viene messo in vendita
		    	 //dovrebbe anche essere messa la categoria, il prezzo di vendita e anche la durata dell'asta
		    	  
		    	  vendiInterface();
		    	  
		        }
		      });
		
		
		btnOffri.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  //da scrivere interfaccia per offerta
		    	  
		    	  offriInterface();
		    	  
		        }
		      });
		
		
		btnDomanda.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  //da scrivere interfaccia per domanda
		    	  //id oggetto e viene inviata una domanda con il relativo testo
		    	  
		    	  domandaInterface();
		    	  
		        }
		      });
		
		btnRisposta.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  //da scrivere interfaccia per domanda
		    	  //id oggetto e viene inviata una domanda con il relativo testo
		    	  
		    	  rispostaInterface();
		    	  
		        }
		      });
		
		
		
		btnVisualizza.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  
		    	  //da scrivere interfaccia per visualizzazione oggetti
		    	  //chiamata al server per chiedere tutti gli oggetti in asta atm
		    	  
		    	  visualizzaOggettiInVenditaInterface();
		    	  
		        }
		      });
	
		
	}
	
	private void visualizzaProfiloInterface() {
		
		clearRootPanel();
		
		
		Label lblUsername = new Label();
		lblUsername.setText("Inserisci l'username della persona che vuoi cercare : ");
		
		TextBox txtUsername = new TextBox();
		
		Button btnCerca = new Button();
		btnCerca.setText("Cerca");
		
		Button btnIndietro = new Button();
		btnIndietro.setText("Indietro");
		
		
		RootPanel.get("1-1").add(lblUsername);
		RootPanel.get("1-2").add(txtUsername);
		
		RootPanel.get("3-1").add(btnCerca);
		RootPanel.get("3-2").add(btnIndietro);
		
		
		btnCerca.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				clearRootPanel();
				
				
				//da creare l'interfaccia o con una label o con qualcosa di simile
				//da mettere anche il bottone indietro per permettere di tornare la menu
				
			}
		});
		
		btnIndietro.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				menuInterface();
				
			}
		});
		
	}
	
	private void vendiInterface() {
		
		clearRootPanel();
		
		
		Label lblNome = new Label();
		lblNome.setText("Nome oggetto : ");
		
		TextBox txtNome = new TextBox();
		
		
		Label lblDescrizione = new Label();
		lblDescrizione.setText("Descrizione : ");
		
		TextBox txtDescrizione = new TextBox();
		
		
		Label lblPrezzo = new Label();
		lblPrezzo.setText("Prezzo base di vendita : ");
		
		TextBox txtPrezzo = new TextBox();
		
		
		Label lblScadenza = new Label();
		lblScadenza.setText("Data di fine dell'asta : ");
		
		TextBox txtScadenza = new TextBox();
		
		
		Label lblCategoria = new Label();
		lblCategoria.setText("Categoria : ");
		
		//da fare una richiesta al db per le categorie che ci sono 
		ListBox lstCategoria = new ListBox();
		lstCategoria.addItem("prova 1");
		lstCategoria.addItem("prova 2");
		lstCategoria.addItem("prova 3");
		lstCategoria.addItem("prova 4");
		
		
		Button btnVendi = new Button();
		btnVendi.setText("Metti in vendita");
		
		Button btnIndietro = new Button();
		btnIndietro.setText("Indietro");
		
		
		RootPanel.get("1-1").add(lblNome);
		RootPanel.get("1-2").add(txtNome);

		RootPanel.get("2-1").add(lblDescrizione);
		RootPanel.get("2-2").add(txtDescrizione);
		
		RootPanel.get("3-1").add(lblPrezzo);
		RootPanel.get("3-2").add(txtPrezzo);
		
		RootPanel.get("4-1").add(lblScadenza);
		RootPanel.get("4-2").add(txtScadenza);
		
		RootPanel.get("5-1").add(lblCategoria);
		RootPanel.get("5-2").add(lstCategoria);
		
		RootPanel.get("7-1").add(btnVendi);
		RootPanel.get("7-2").add(btnIndietro);
		
		
		btnVendi.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				//chiamare il server per mettere in vendita l'oggetto
				
			}
		});
		
		btnIndietro.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				menuInterface();
				
			}
		});
		
	}
	
	private void offriInterface() {
		
		clearRootPanel();
		
		
		//non ho capito cosa devo fare
		//non se lo devo fare un'interfaccia a parte oppure devo fare qualcosa che sia tipo dinamico
		
	}
	
	private void domandaInterface() {
		
		clearRootPanel();
		
		
		Label lblNome = new Label();
		lblNome.setText("Inserisci il nome dell'oggetto al quale fare una domanda : ");
		
		TextBox txtNome = new TextBox();
		
		
		Label lblDomanda = new Label();
		lblDomanda.setText("Domanda : ");
		
		TextBox txtDomanda = new TextBox();
		
		
		Button btnInvia = new Button();
		btnInvia.setText("Invia");
		
		Button btnIndietro = new Button();
		btnIndietro.setText("Indietro");
		
		
		btnInvia.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				//chiamare il server per scrivere la domanda sul db
				
			}
		});
	}
		
		private void rispostaInterface() {
			
			clearRootPanel();
			
			
			Label lblNome = new Label();
			lblNome.setText("Inserisci il nome dell'oggetto per il quale è stata posta una domanda: ");
			
			TextBox txtNome = new TextBox();
			
			
			Label lblRisposta = new Label();
			lblRisposta.setText("Risposta : ");
			
			TextBox txtRisposta = new TextBox();
			
			
			Button btnInvia = new Button();
			btnInvia.setText("Invia");
			
			Button btnIndietro = new Button();
			btnIndietro.setText("Indietro");
			
			
			btnInvia.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					
					//chiamare il server per scrivere la domanda sul db
					
				}
			});
		
		
		btnIndietro.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				menuInterface();
				
			}
		});
		
	}
	
	private void visualizzaOggettiInVenditaInterface() {
		
		clearRootPanel();
		
	}
	
	private void clearRootPanel() {

		RootPanel.get("1-1").clear();
		RootPanel.get("1-2").clear();
		
		RootPanel.get("2-1").clear();
		RootPanel.get("2-2").clear();
		
		RootPanel.get("3-1").clear();
		RootPanel.get("3-2").clear();
		
		RootPanel.get("4-1").clear();
		RootPanel.get("4-2").clear();
		
		RootPanel.get("5-1").clear();
		RootPanel.get("5-2").clear();
		
		RootPanel.get("6-1").clear();
		RootPanel.get("6-2").clear();
		
		RootPanel.get("7-1").clear();
		RootPanel.get("7-2").clear();
		
		RootPanel.get("8-1").clear();
		RootPanel.get("8-2").clear();
		
		RootPanel.get("9-1").clear();
		RootPanel.get("9-2").clear();
		
		RootPanel.get("10-1").clear();
		RootPanel.get("10-2").clear();
		
		RootPanel.get("11-1").clear();
		RootPanel.get("11-2").clear();
		
		RootPanel.get("12-1").clear();
		RootPanel.get("12-2").clear();
		
		RootPanel.get("13-1").clear();

	}
}
