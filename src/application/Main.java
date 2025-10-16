package application;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class Main extends Application {
	
	// CONSTANTES QUE JE PEUX CONTRÔLER ICI
	Color couleurTitre = Color.BLUEVIOLET;
	Color couleurArrierePlan = Color.BLANCHEDALMOND;
	Color couleurArrierePlanTableView = Color.web("#FCF9EA");
	
	
	// Variables de logique et fonctionnement
	String cheminFichierRecettes = "dbRecettes/recettes.txt";
	BufferedReader bReader;
	String ligne;
	String titreFenetre;
	
//	ArrayList<Recette> lstRecette = new ArrayList<Recette>();
	ObservableList<Recette> lstRecette = FXCollections.observableArrayList();
	Stage stage;
	Scene scene;
	
	// *****VARIABLES DE STRUCTURE********//
	// ------------MENU-------------------//
	
	private VBox vboxMenu;
	
	private Button btnMenuAjouter;
	private Button btnMenuModifier;
	private Button btnMenuChoisir;
	private Button btnMenuSupprimer;
	
	// ------------LISTE DE RECETTES --------------//
	
	private VBox vboxListeRecettes;
	
	private HBox hboxPiedListeRecettes;
	
	private Label lblListeRecettes;
	//private Rectangle lstRecettes;
	
	private Button btnSelectRecette;
	private Button btnRetourMenu;
	
	private TableView<Recette> tblRecettes;
	private TableColumn<Recette, String> tblColNom = new TableColumn<Recette, String>("Nom");
	private TableColumn<Recette, String> tblColDuree = new TableColumn<Recette, String>("Durée (minutes)");
	private TableColumn<Recette,String> tblColType = new TableColumn<Recette, String>("Type de plat");
	// -------------RECETTE CHOISIE ---------------//
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.stage = primaryStage;
			Pane root = new Pane();
			scene = new Scene(root);
			System.out.println(scene.getRoot());
			genererComposantsMenu();
			configurerComposantsMenu();
			assemblerComposantsMenu();


			
			primaryStage.setScene(scene);
			primaryStage.setTitle(titreFenetre);
			primaryStage.sizeToScene();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    // ********************************* 1 ***********************************

    // ******************************** MENU ***********************************
	
	
	private void genererComposantsMenu() {
		vboxMenu = new VBox();
		vboxMenu.setBackground(Background.fill(couleurArrierePlan));
		
		btnMenuAjouter = new Button("Ajouter recette");
		
		btnMenuModifier = new Button("Modifier recette");
		
		btnMenuChoisir = new Button("Choisir recette");
		
		btnMenuSupprimer = new Button("Supprimer une recette");
		
	}
	
	private void configurerComposantsMenu() {
		titreFenetre = "ReciPICK - Que souhaitez vous faire ?";
		
		scene.setRoot(vboxMenu);
		vboxMenu.setSpacing(50);
		vboxMenu.setAlignment(Pos.CENTER);
		vboxMenu.setPadding(new Insets(50));
		
		VBox.setVgrow(btnMenuAjouter, Priority.ALWAYS);
		btnMenuAjouter.setPrefSize(180, 30);
		
		VBox.setVgrow(btnMenuModifier, Priority.ALWAYS);
		btnMenuModifier.setPrefSize(180, 30);
		
		
		VBox.setVgrow(btnMenuChoisir, Priority.ALWAYS);
		btnMenuChoisir.setPrefSize(180, 30);
		
		
		VBox.setVgrow(btnMenuSupprimer, Priority.ALWAYS);
		btnMenuSupprimer.setPrefSize(180, 30);
		
		
		btnMenuChoisir.setOnAction(event ->{
			genererComposantsListeRecettes();
			configurerComposantsListeRecettes();
			assemblerComposantsListeRecettes();
		});
		
	}
	
	private void assemblerComposantsMenu() {
		vboxMenu.getChildren().addAll(btnMenuAjouter,btnMenuModifier,btnMenuChoisir,btnMenuSupprimer);
		
		stage.sizeToScene();
		stage.centerOnScreen();
	}
	
    // ******************************** ***** 2 ***** ***********************************

    // ******************************** LISTE RECETTES ***********************************
	
	private void genererComposantsListeRecettes() {
		
		vboxListeRecettes = new VBox();
		vboxListeRecettes.setBackground(Background.fill(couleurArrierePlan));
		hboxPiedListeRecettes = new HBox();
		
		lblListeRecettes = new Label("RECETTES DISPONIBLES");
		
		tblRecettes = new TableView<Recette>();
		tblRecettes.setBackground(Background.fill(Color.AQUA));
		tblRecettes.setColumnResizePolicy(tblRecettes.CONSTRAINED_RESIZE_POLICY);
		
		btnSelectRecette = new Button("C'est parti !");
		btnRetourMenu = new Button("Retour au menu");
		
		try {
			listeRecettes();
		}catch(ExceptionsRecettes e) {
			System.out.println("Erreur dans la liste de recettes");
		}
	}
	
	private void configurerComposantsListeRecettes() {
		titreFenetre = "ReciPICK - Choissisez votre recette";

		scene.setRoot(vboxListeRecettes);

		
		vboxListeRecettes.setAlignment(Pos.CENTER);
		
		
		vboxListeRecettes.setMargin(lblListeRecettes, new Insets(20));
		lblListeRecettes.setPadding(new Insets(10));
		lblListeRecettes.setPrefHeight(30);
		lblListeRecettes.setFont(Font.font("Arial",FontWeight.BOLD, 20.0));
		lblListeRecettes.setTextFill(couleurTitre);
		
		//Configuration du tableau
		
		vboxListeRecettes.setVgrow(tblRecettes, Priority.ALWAYS);
		vboxListeRecettes.setPadding(new Insets(10));
		
		// Population des colonnes:
		tblColNom.setCellValueFactory(new PropertyValueFactory<Recette,String>("nom")); 
		tblColDuree.setCellValueFactory(new PropertyValueFactory<Recette,String>("tempsPreparation"));
		tblColType.setCellValueFactory(new PropertyValueFactory<Recette,String>("typePlat"));
		
		tblRecettes.setItems(lstRecette);
		
		
		// Boutons dans le HBox 
		hboxPiedListeRecettes.setSpacing(20);
		hboxPiedListeRecettes.setAlignment(Pos.CENTER);
		
		
		hboxPiedListeRecettes.setMargin(btnSelectRecette, new Insets(30,0,20,50));
		HBox.setHgrow(btnSelectRecette, Priority.ALWAYS);
		btnSelectRecette.setPrefSize(180, 30);
		btnSelectRecette.setMaxWidth(200);

		
		hboxPiedListeRecettes.setMargin(btnRetourMenu, new Insets(30,50,20,0));
		HBox.setHgrow(btnRetourMenu, Priority.ALWAYS);
		btnRetourMenu.setPrefSize(180, 30);
		btnRetourMenu.setMaxWidth(200);
		btnRetourMenu.setOnAction(event->{
			genererComposantsMenu();
			configurerComposantsMenu();
			assemblerComposantsMenu();
			lstRecette.clear();
		});
		
		
		

	}
	
	private void assemblerComposantsListeRecettes() {
		hboxPiedListeRecettes.getChildren().addAll(btnSelectRecette,btnRetourMenu);
		tblRecettes.getColumns().setAll(tblColNom,tblColDuree,tblColType);
		vboxListeRecettes.getChildren().addAll(lblListeRecettes,tblRecettes,hboxPiedListeRecettes);
		stage.sizeToScene();
		stage.centerOnScreen();
		
	}
	
	
	//Méthode utilisée pour accèder à la liste de recettes dbRecettes.txt et obtenir ses valeurs
	private void listeRecettes() throws ExceptionsRecettes{
		try {
			FileReader fReader = new FileReader(cheminFichierRecettes);
			bReader = new BufferedReader(fReader);
			bReader.readLine(); // UTILISÉ POUR LAISSER PASSER UNE LIGNE, CAR LA PREMIÈRE LIGNE DÉCRIT LES CHAMPS
			while(( ligne = bReader.readLine()) != null) {
				String[] elements = ligne.split(";");
				String nom = elements[0];
				String temps = elements[1];
				String typePlat = elements[2];
				String[] ingredients = elements[3].split(",");
				String[] instructions = elements[4].split(",");
				
				// Intégration des recettes du "recettes.txt" dans mon ArrayList de recettes
				lstRecette.add(new Recette(nom,temps,typePlat,ingredients,instructions));
			}
			System.out.println(lstRecette.size()+" recettes dans l'inventaire");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur de fichier non trouvé");
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
