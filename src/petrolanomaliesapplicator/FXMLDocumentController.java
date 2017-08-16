/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import petrolanomaliesapplicator.datatypes.FileHandler;
import petrolanomaliesapplicator.datatypes.TankMeasure;
import petrolanomaliesapplicator.leakage.TankLeakageApplicator;

/**
 *
 * @author Przemek
 */
public class FXMLDocumentController implements Initializable {
    
    Collection<TankMeasure> tankMeasures;
    
    @FXML private FileChooser fileChooser;
    @FXML private Label tankMeasuresFileLabel;
    
    @FXML private CheckBox constantTankLeakageApplicator;
    @FXML private TextField constantTankLeakageValue;
    @FXML private TextField constantTankLeakageDateStart;
    @FXML private TextField constantTankLeakageDateEnd;
    
    
    @FXML
    private void handleLoadTankMeasures(ActionEvent event) {
        Node node = (Node) event.getSource();
        File file = fileChooser.showOpenDialog(node.getScene().getWindow());
        if(file != null) {
            tankMeasuresFileLabel.setText("File: " + file.getName());
            tankMeasures = FileHandler.loadTankMeasures(file.getPath());
        }
    }
    
    
    @FXML
    private void handleStartApplication(ActionEvent event) {
        
        if(constantTankLeakageApplicator.selectedProperty().getValue() == true) {
            applyConstantTankLeakage();
        }
        

    }
    
    private void applyConstantTankLeakage() {
        Integer tankId = 1;

        Double constantLeakage = 10.0;
        if(constantTankLeakageValue.getText() != null && !constantTankLeakageValue.getText().isEmpty()) {
            constantLeakage = Double.parseDouble(constantTankLeakageValue.getText());
            System.out.println(constantLeakage);
        }
        
        LocalDateTime startDate = LocalDateTime.parse(constantTankLeakageDateStart.getText());
        LocalDateTime endDate = LocalDateTime.parse(constantTankLeakageDateEnd.getText());

        Collection<TankMeasure> editedTankMeasures = TankLeakageApplicator.applyConstantLeakage(
                tankMeasures,
                tankId, 
                startDate, 
                endDate, 
                constantLeakage);
        
        editedTankMeasures.forEach((TankMeasure tankMeasure) -> {
            if (tankMeasure.getTankId() == 1) {
                System.out.println(tankMeasure);
            }
        });
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
    }    
    
}
