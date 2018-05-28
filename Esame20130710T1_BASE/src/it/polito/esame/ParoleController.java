/**
 * Sample Skeleton for 'paroleT1.fxml' Controller Class
 */
package it.polito.esame;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.esame.bean.Parola;
import it.polito.esame.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class ParoleController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="theRoot"
    private BorderPane theRoot; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="x1"
    private Color x1; // Value injected by FXMLLoader

    @FXML // fx:id="txtParolaIniziale"
    private TextField txtParolaIniziale; // Value injected by FXMLLoader

    @FXML // fx:id="txtParolaFinale"
    private TextField txtParolaFinale; // Value injected by FXMLLoader

    @FXML // fx:id="btnCerca"
    private Button btnCerca; // Value injected by FXMLLoader

    @FXML // fx:id="txtLunghezza"
    private TextField txtLunghezza; // Value injected by FXMLLoader

    @FXML // fx:id="btnCarica"
    private Button btnCarica; // Value injected by FXMLLoader
    
    Model model = new Model() ;
    
    @FXML
    void handleCarica(ActionEvent event) {
    	try {
    	int size = Integer.parseInt(this.txtLunghezza.getText());
    	model.createGraph(size);
    	int numWords = model.getNumberOfWords();
    	int numCC = model.getNumberOfCC();
    	String maxWord = model.getMaxWord();
    	
    	this.txtResult.setText("Il numero di parole trovato è "+numWords+"\n");
    	this.txtResult.appendText("Il numero di componenti connesse è: "+numCC+"\n");
    	this.txtResult.appendText("La parola con più componenti connesse è: "+maxWord+"\n");
    	} catch (NumberFormatException e) {
    		this.txtResult.setText("Inserisci un numero valido sotto forma di intero positivo\n");
    	}
    }

    @FXML
    void handleCerca(ActionEvent event) {
    	String iniziale = this.txtParolaIniziale.getText();
    	String finale = this.txtParolaFinale.getText();
    	try {
    		int size = Integer.parseInt(this.txtLunghezza.getText());
    		if(iniziale.length() != size || finale.length() != size)
    			throw new IllegalArgumentException("Le parole inserite non sono della lunghezza giusta\n");
    		if(size == 0)
    			throw new RuntimeException();
    		
    		List<Parola> path = model.getPath(iniziale, finale);
    		
    		this.txtResult.setText("");
    		for(Parola p : path) {
    			this.txtResult.appendText(p.getNome()+"\n");
    		}
    		
    	} catch (NumberFormatException e) {
    		this.txtResult.setText("Inserisci un numero valido sotto forma di intero positivo\n");
    	} catch (IllegalArgumentException e) {
    		this.txtResult.setText("Le parole inserite non sono della lunghezza giusta\n");
    	} catch (RuntimeException e) {
    		this.txtResult.setText("Specifica la lunghezza della parola\n");
    	} catch (Exception e) {
    		this.txtResult.setText("Le parole inserite potrebbero non essere presenti nel dizionario...\n");
    	}
    	
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert theRoot != null : "fx:id=\"theRoot\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtParolaIniziale != null : "fx:id=\"txtParolaIniziale\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtParolaFinale != null : "fx:id=\"txtParolaFinale\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert btnCerca != null : "fx:id=\"btnCerca\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert txtLunghezza != null : "fx:id=\"txtLunghezza\" was not injected: check your FXML file 'paroleT1.fxml'.";
        assert btnCarica != null : "fx:id=\"btnCarica\" was not injected: check your FXML file 'paroleT1.fxml'.";

    }

	public void setModel(Model m) {
		// TODO Auto-generated method stub
		this.model = m ;
	}
}
