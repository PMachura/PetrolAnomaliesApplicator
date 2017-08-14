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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import petrolanomaliesapplicator.datatypes.FileHandler;
import petrolanomaliesapplicator.datatypes.TankMeasure;
import petrolanomaliesapplicator.leakage.ConstantTankLeakageApplicator;

/**
 *
 * @author Przemek
 */
public class FXMLDocumentController implements Initializable {
    
    Collection<TankMeasure> tankMeasures;
    
    @FXML private Label tankMeasuresLabel;
    @FXML private Label tankMeasuresFileLabel;
    @FXML private Label tankLabel;
    @FXML private Label constantTankLeakage;
    @FXML private Label dateStartLabel;
    @FXML private Label dateEndLabel;
    @FXML private FileChooser fileChooser;
    @FXML private ChoiceBox tankChoiceBox;
    @FXML private TextField constantTankLeakageValue;
    @FXML private TextField dateStartValue;
    @FXML private TextField dateEndValue;
    
    
    @FXML
    private void handleLoadTankMeasures(ActionEvent event) {
        Node node = (Node) event.getSource();
        File file = fileChooser.showOpenDialog(node.getScene().getWindow());
        if(file != null) {
            tankMeasuresFileLabel.setText("File: " + file.getPath());
            tankMeasures = FileHandler.loadTankMeasures(file.getPath());
        }
    }
    
    @FXML
    private void handleStartApplicator(ActionEvent event) {
        Integer tankId = 1;
        if(tankChoiceBox.getValue() != null) {
            String tankIdString = (String) tankChoiceBox.getValue();
            tankId = Integer.parseInt(tankIdString);
        }
        Double constantLeakage = 10.0;
        if(constantTankLeakageValue.getText() != null && !constantTankLeakageValue.getText().isEmpty()) {
            constantLeakage = Double.parseDouble(constantTankLeakageValue.getText());
        }
        
        LocalDateTime startDate = LocalDateTime.parse(dateStartValue.getText());
        LocalDateTime endDate = LocalDateTime.parse(dateEndValue.getText());

        Collection<TankMeasure> editedTankMeasures = ConstantTankLeakageApplicator.applyConstantLeakage(
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
        tankMeasuresLabel.setText("Tank measures");
        tankMeasuresLabel.setFont(Font.font(15));
        tankLabel.setText("Tank ID");
        fileChooser = new FileChooser();
        tankChoiceBox.setItems(FXCollections.observableArrayList(
            "1", "2", "3", "4")
        );
        constantTankLeakage.setText("Constant tank leakage (l/h)");
        dateStartLabel.setText("Start date (Format: 'yyyy-mm-ddThh:mm:ss')");
        dateEndLabel.setText("End date (Format: 'yyyy-mm-ddThh:mm:ss')");
    }    
    
}
