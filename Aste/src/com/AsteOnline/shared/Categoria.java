package com.AsteOnline.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.AsteOnline.shared.Utente.Admin;
import com.AsteOnline.shared.Utente.UtenteRegistrato;

public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String nome, id;
	private ArrayList<Categoria> sottocategorie;
	private int profondita;

	public Categoria() {

	}
	
	public Categoria(String nome, String id, int profondita){
		this.profondita=profondita;
		this.nome = nome;
		sottocategorie = new ArrayList<Categoria>();
		this.id = id;

	}
	
	public String getId() {
		return id;
	}

	public String getCategoria(){

		return nome;		

	}

	public void setCategoria(String nome) {
		this.nome = nome;
	}

    public int getProfondita() {
    	return profondita;
    }
    
    public void setProfondita(int profondita) {
    	this.profondita=profondita;
    }
    
	public ArrayList<Categoria> getSottoCategorie(){

		if(sottocategorie.size() == 0){
			return null;
		}
		else {
			return sottocategorie;
		}
	}

	public void setSottoCategorie(ArrayList<Categoria> sottocategorie) {

		if(sottocategorie != null) {
			this.sottocategorie.addAll(sottocategorie);
		}
	}


	public void addSottoCategoria(Categoria categoria){

		sottocategorie.add(categoria);

	}


	public void removeSottoCategoria(String categoria) {

		for(int i = 0; i < sottocategorie.size(); i++){

			if(sottocategorie.get(i).getCategoria().equals(categoria)){

				sottocategorie.remove(i);

			}

		}

	}
	
	public int getSizeSottoCategorie() {
		return sottocategorie.size();
	}
}
