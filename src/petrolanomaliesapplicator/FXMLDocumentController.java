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
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class FXMLDocumentController implements Initializable {
    
    Collection<TankMeasure> tankMeasures;
    
    @FXML private FileChooser fileChooser;
    @FXML private Label tankMeasuresFileLabel;
    
    /* Constant Leakage */
    @FXML private CheckBox constantTankLeakageApplicator;
    @FXML private TextField constantTankLeakageTankId;
    @FXML private TextField constantTankLeakageValue;
    @FXML private TextField constantTankLeakageDateStart;
    @FXML private TextField constantTankLeakageDateEnd;
    
    /* Variable Leakage */
    @FXML private CheckBox variableTankLeakageApplicator;
    @FXML private TextField variableTankLeakageTankId;
    @FXML private TextField variableTankLeakagePointHeight;
    @FXML private TextField variableTankLeakageDateStart;
    @FXML private TextField variableTankLeakageDateEnd;
    
    /* Pipeline Leakage */
    @FXML private CheckBox pipelineTankLeakageApplicator;
    @FXML private TextField pipelineTankLeakageTankId;
    @FXML private TextField pipelineTankLeakageValue;
    @FXML private TextField pipelineTankLeakagePointHeight;
    @FXML private TextField pipelineTankLeakageDateStart;
    @FXML private TextField pipelineTankLeakageDateEnd;
    
    /* Probe Hang */
    @FXML private CheckBox probeHangApplicator;
    @FXML private TextField probeHangTankId;
    @FXML private TextField probeHangDateStart;
    @FXML private TextField probeHangDateEnd;
    
    /* Meter Miscalibration */
    @FXML private CheckBox meterMiscalibrationApplicator;
    @FXML private TextField meterMiscalibrationTankId;
    @FXML private TextField meterMiscalibrationGunId;
    @FXML private TextField meterMiscalibrationCoefficient;
    @FXML private TextField meterMiscalibrationDateStart;
    @FXML private TextField meterMiscalibrationDateEnd;
    
    
    
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

    }
      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
    }    
    
}
