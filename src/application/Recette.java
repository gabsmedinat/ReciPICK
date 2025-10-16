package application;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Recette {
	
		private SimpleStringProperty nom;      				    			// Nom de la recette
		private SimpleStringProperty tempsPreparation;						// Temps de préparation
		private SimpleStringProperty typePlat;								// Type de plat (Déjeuner, dessert, Dinner, etc) 
		private ObservableList<String> ingredients = FXCollections.observableArrayList();    // Liste d'ingrédients
		private ObservableList<String> directions = FXCollections.observableArrayList();		// Liste contenant les directrices de préparation
																			// Format NoInstruction:Instruction
		
	public Recette(String nom, String duree, String type, String[] ingredients, String[] directions) {
		this.nom = new SimpleStringProperty(nom);
		this.tempsPreparation = new SimpleStringProperty(duree);
		this.typePlat = new SimpleStringProperty(type);
		for(String ingredient : ingredients) {
			this.ingredients.add(ingredient);
		}
		for(String instruction : directions) {
			this.directions.add(instruction);		}
	}

	
	
	
	public static void main(String[] args) {
		// Nom de la recette
		String nom = "Pâtes carbonara";

		// Temps de préparation en minutes
		String temps = "25";

		// Type de repas
		String type = "déjeuner";

		// Ingrédients
		String[] ingredients = {
		    "150g pâtes",
		    "50g pancetta",
		    "1 oeuf",
		    "30g parmesan",
		    "sel",
		    "poivre"
		};

		// Instructions / Préparation
		String[] instructions = {
		    "Cuire les pâtes dans l'eau salée",
		    "Couper la pancetta et la faire revenir dans une poêle",
		    "Battre l'oeuf avec le parmesan dans un bol",
		    "Égoutter les pâtes en gardant un peu d'eau de cuisson",
		    "Mélanger les pâtes avec la pancetta hors du feu",
		    "Ajouter le mélange oeuf-parmesan et un peu d'eau de cuisson pour créer la sauce",
		    "Assaisonner avec sel et poivre"
		};
		
		Recette pateCarbonara = new Recette(nom,temps,type,ingredients,instructions);
		Iterator<String> itPates = pateCarbonara.getDirections().iterator();
		
		while(itPates.hasNext()) {
			System.out.println(itPates.next());
		}
	}


	//GETTERS ET SETTERS fx

	public final SimpleStringProperty nomProperty() {
		return this.nom;
	}
	




	public final String getNom() {
		return this.nomProperty().get();
	}
	




	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	




	public final SimpleStringProperty tempsPreparationProperty() {
		return this.tempsPreparation;
	}
	




	public final String getTempsPreparation() {
		return this.tempsPreparationProperty().get();
	}
	




	public final void setTempsPreparation(final String tempsPreparation) {
		this.tempsPreparationProperty().set(tempsPreparation);
	}
	




	public final SimpleStringProperty typePlatProperty() {
		return this.typePlat;
	}
	




	public final String getTypePlat() {
		return this.typePlatProperty().get();
	}
	




	public final void setTypePlat(final String typePlat) {
		this.typePlatProperty().set(typePlat);
	}
	
	
	public ObservableList<String> getDirections() {
		return directions;
	}
	
	public ObservableList<String> getIngredients(){
		return ingredients;
	}

}
