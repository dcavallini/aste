package com.AsteOnline.client;

import java.util.ArrayList;

import com.AsteOnline.shared.Categoria;
import com.AsteOnline.shared.Oggetto;
import com.AsteOnline.shared.Utente;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.TableColElement;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;

public class VisualizzaOggetti extends Composite {

	public Utente utente;
	public Oggetto oggetto = new Oggetto();


	private static VisualizzaOggettiUiBinder uiBinder = GWT.create(VisualizzaOggettiUiBinder.class);

	interface VisualizzaOggettiUiBinder extends UiBinder<Widget, VisualizzaOggetti> {
	}

	private final GreetingServiceAsync greetingService=GWT.create(GreetingService.class);


	public VisualizzaOggetti() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	public VisualizzaOggetti(Utente utenteParametro) {
		initWidget(uiBinder.createAndBindUi(this));

		final ValueListBox<String> categoria = new ValueListBox<String>();

		final Label labCat = new Label("Definisci la categoria: ");
		RootPanel.get().add(labCat);
		categoria.getElement().getStyle().setBackgroundColor("#f1f1f1");
		categoria.getElement().getStyle().setWidth(1255, Unit.PX);
		categoria.getElement().getStyle().setPadding(10, Unit.PX);
		
		final ArrayList<String> nomiCategorie = new ArrayList<String>();
		
		final ArrayList<Categoria> tutteCategorie = new ArrayList<Categoria>();

		utente = utenteParametro;
		
		final HorizontalPanel hp = new HorizontalPanel();

		//popolo la listbox con le categorie
		greetingService.inizializzazioneCategorie(new AsyncCallback<ArrayList<Categoria>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.toString());
			}

			@Override
			public void onSuccess(ArrayList<Categoria> result) {

				for(int i = 0; i < result.size(); i++) {
					String tmp = result.get(i).getCategoria();
					tutteCategorie.add(result.get(i));
					nomiCategorie.add(tmp);
				}


				categoria.setAcceptableValues(nomiCategorie);
			}
		});

		RootPanel.get().add(categoria);

		//mostro gli oggetti in vendita e le relative info
		greetingService.visualzzaOggetti(new AsyncCallback<ArrayList<Oggetto>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Errore nel server"); //in GreetingServiceImpl

			}

			@Override
			public void onSuccess(ArrayList<Oggetto> result) {
		
				if(result == null) {
					Window.alert("Nessun oggetto esistente");
				}
				else {

					for(int n=0; n<result.size(); n++) {
						Hyperlink hl = new Hyperlink(result.get(n).getNome(), result.get(n).getIdOggetto());
						RootPanel.get().add(hl);
					}
				}


			}

		});

		categoria.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				
				Categoria c = new Categoria();
				
				for(int i = 0; i < tutteCategorie.size(); i++){
					
					if(tutteCategorie.get(i).getCategoria().equals(categoria.getValue())) {
						c = tutteCategorie.get(i);
					}
					
				}
				
				if(categoria.getValue() == null) {
					c = null;
				}

				greetingService.filtroPerCategoria(c, new AsyncCallback<ArrayList<Oggetto>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Impossibile effettuare l'operazione");
					}

					@Override
					public void onSuccess(ArrayList<Oggetto> result) {
						
						RootPanel.get().clear();

						if(result.size() == 0) {

							RootPanel.get().add(categoria);

							Window.alert("Non ci sono oggetti corrispondeti per la categoria");
						} else {
							RootPanel.get().clear();

							RootPanel.get().add(categoria);

							final String s = categoria.getValue();
							
							
							categoria.setValue(s);
							
							if(s == null) {
								
								categoria.setValue(null);
								
							}
							
							for(int n=0; n<result.size(); n++) {
							
								Hyperlink hl = new Hyperlink(result.get(n).getNome(), result.get(n).getIdOggetto());
								RootPanel.get().add(hl);
								
							}
							
						}

					}

				});

			}

		});


	}

}
