package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="compagnieMinimo"
    private TextField compagnieMinimo; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoDestinazione"
    private ComboBox<Airport> cmbBoxAeroportoDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessione"
    private Button btnConnessione; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	
    	txtResult.clear();
    	cmbBoxAeroportoPartenza.getItems().clear();
    	cmbBoxAeroportoDestinazione.getItems().clear();
    	int compagnie = 0;
    	try {
    		compagnie = Integer.parseInt(compagnieMinimo.getText());
    	} catch (NumberFormatException e) {
    		txtResult.clear();
        	txtResult.appendText("Inserisci un valore numerico!\n");
        	return ;
    	}
    	model.creaGrafo(compagnie);
    	txtResult.appendText("Grafo creato!\n");
    	txtResult.appendText("# Vertici: " + model.vertexNumber() + "\n");
    	txtResult.appendText("# Archi: " + model.edgeNumber() + "\n");

    	cmbBoxAeroportoPartenza.getItems().addAll(model.getVertici());
    	cmbBoxAeroportoDestinazione.getItems().addAll(model.getVertici());
    }

    @FXML
    void doTestConnessione(ActionEvent event) {

    	txtResult.clear();
    	Airport partenza = cmbBoxAeroportoPartenza.getValue();
    	if(partenza == null) {
    		txtResult.appendText("Selezionare un aereoporto di partenza!\n");
    		return;
    	}
    	Airport arrivo = cmbBoxAeroportoDestinazione.getValue();
    	if(arrivo == null) {
    		txtResult.appendText("Selezionare un aereoporto di arrivo!\n");
    		return;
    	}
    	List<Airport> percorso = new ArrayList<>(model.percorso(partenza, arrivo));
    	if(percorso == null) {
    		txtResult.appendText("Non c'è un percorso tra glli aereoporti selezionati\n");
    		return;
    	}
    	txtResult.appendText("Percorso tra gli aereoporti selezionati: \n");
    	for(Airport a: percorso) {
    		txtResult.appendText(a.toString() + "\n");
    		
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert compagnieMinimo != null : "fx:id=\"compagnieMinimo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbBoxAeroportoDestinazione != null : "fx:id=\"cmbBoxAeroportoDestinazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessione != null : "fx:id=\"btnConnessione\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
