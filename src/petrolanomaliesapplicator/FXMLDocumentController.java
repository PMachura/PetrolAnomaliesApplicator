/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyHandler;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantTankLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.PipelineLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ProbeHangConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.VariableTankLeakageConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.model.DataSetCollection;
import petrolanomaliesapplicator.model.DataSetFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.RefuelMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class FXMLDocumentController implements Initializable {
    
    DataSetCollection dataSetCollection;
    
    @FXML private Label saveSuccess;
    @FXML private Label loadSuccess;
    
    /* File Set */
    @FXML private ChoiceBox fileSet;
    
    /* Configurator */
    @FXML private TextField configuratorName;
    @FXML private TextField configuratorNameLoad;
    
    /* Output */
    @FXML private TextField outputDir;
    
    /* Constant Leakage */
    @FXML private Label constantTankLeakageApplicator;
    private int constantTankLeakageCount;
    @FXML private TextField constantTankLeakageTankId;
    @FXML private TextField constantTankLeakageValue;
    @FXML private TextField constantTankLeakageDateStart;
    @FXML private TextField constantTankLeakageDateEnd;
    
    /* Variable Leakage */
    @FXML private Label variableTankLeakageApplicator;
    private int variableTankLeakageCount;
    @FXML private TextField variableTankLeakageTankId;
    @FXML private TextField variableTankLeakagePointHeight;
    @FXML private TextField variableTankLeakageDateStart;
    @FXML private TextField variableTankLeakageDateEnd;
    
    /* Pipeline Leakage */
    @FXML private Label pipelineTankLeakageApplicator;
    private int pipelineTankLeakageCount;
    @FXML private TextField pipelineTankLeakageTankId;
    @FXML private TextField pipelineTankLeakageValuePerCubicMeter;
    @FXML private TextField pipelineTankLeakageGunId;
    @FXML private TextField pipelineTankLeakageDateStart;
    @FXML private TextField pipelineTankLeakageDateEnd;
    
    /* Probe Hang */
    @FXML private Label probeHangApplicator;
    private int probeHangCount;
    @FXML private TextField probeHangTankId;
    @FXML private TextField probeHangDateStart;
    @FXML private TextField probeHangDateEnd;
    
    /* Meter Miscalibration */
    @FXML private Label meterMiscalibrationApplicator;
    private int meterMiscalibrationCount;
    @FXML private TextField meterMiscalibrationTankId;
    @FXML private TextField meterMiscalibrationGunId;
    @FXML private TextField meterMiscalibrationCoefficient;
    @FXML private TextField meterMiscalibrationDateStart;
    @FXML private TextField meterMiscalibrationDateEnd;
    
    private AnomalyHandler saveAnomalyHandler;
    private AnomalyHandler loadAnomalyHandler;
    
    @FXML
    private void handleSaveConfiguration(ActionEvent event) {
        saveAnomalyHandler.setInputDataSetFileFolder(fileSet.getValue().toString());
        saveAnomalyHandler.setOutpuDataSetFileFolder(outputDir.getText());
        
        FileHandler.saveAnomalyHandlerPropertiesAndConfigurators(configuratorName.getText(), saveAnomalyHandler);
        
        saveSuccess.setText("Save configuration success");
    }
    
    @FXML
    private void createConstantLeakageConfiguration(ActionEvent event) {
        AnomalyConfigurator anomalyConfigurator = new ConstantTankLeakageConfigurator(
                LocalDateTime.parse(constantTankLeakageDateStart.getText()), 
                LocalDateTime.parse(constantTankLeakageDateEnd.getText()), 
                Integer.parseInt(constantTankLeakageTankId.getText()), 
                Double.parseDouble(constantTankLeakageValue.getText())
        );
        saveAnomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        constantTankLeakageCount++;
        constantTankLeakageApplicator.setText("Added: " + constantTankLeakageCount);
    }
    
    @FXML
    private void createVariableLeakageConfiguration(ActionEvent event) {
        AnomalyConfigurator anomalyConfigurator = new VariableTankLeakageConfigurator(
                LocalDateTime.parse(variableTankLeakageDateStart.getText()), 
                LocalDateTime.parse(variableTankLeakageDateEnd.getText()), 
                Integer.parseInt(variableTankLeakageTankId.getText()), 
                Double.parseDouble(variableTankLeakagePointHeight.getText())
        );
        saveAnomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        variableTankLeakageCount++;
        variableTankLeakageApplicator.setText("Added: " + variableTankLeakageCount);
    }
    
    @FXML
    private void createPipelineLeakageConfiguration(ActionEvent event) {
        AnomalyConfigurator anomalyConfigurator = new PipelineLeakageConfigurator(
                LocalDateTime.parse(pipelineTankLeakageDateStart.getText()), 
                LocalDateTime.parse(pipelineTankLeakageDateEnd.getText()), 
                Integer.parseInt(pipelineTankLeakageTankId.getText()), 
                Integer.parseInt(pipelineTankLeakageGunId.getText()), 
                Double.parseDouble(pipelineTankLeakageValuePerCubicMeter.getText())
        );
        saveAnomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        pipelineTankLeakageCount++;
        pipelineTankLeakageApplicator.setText("Added: " + pipelineTankLeakageCount);
    }
    
    @FXML
    private void createProbeHangConfiguration(ActionEvent event) {
        AnomalyConfigurator anomalyConfigurator = new ProbeHangConfigurator(
                LocalDateTime.parse(probeHangDateStart.getText()), 
                LocalDateTime.parse(probeHangDateEnd.getText()), 
                Integer.parseInt(probeHangTankId.getText())
        );
        saveAnomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        probeHangCount++;
        probeHangApplicator.setText("Added: " + probeHangCount);
    }
    
    @FXML
    private void createMeterMiscalibrationConfiguration(ActionEvent event) {
        AnomalyConfigurator anomalyConfigurator = new MeterMiscalibrationConfigurator(
                LocalDateTime.parse(meterMiscalibrationDateStart.getText()), 
                LocalDateTime.parse(meterMiscalibrationDateEnd.getText()), 
                Integer.parseInt(meterMiscalibrationTankId.getText()), 
                Integer.parseInt(meterMiscalibrationGunId.getText()), 
                Double.parseDouble(meterMiscalibrationCoefficient.getText())
        );
        saveAnomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        meterMiscalibrationCount++;
        meterMiscalibrationApplicator.setText("Added: " + meterMiscalibrationCount);
    }
    
    @FXML
    private void handleLoadConfiguration(ActionEvent event) {
        loadAnomalyHandler = FileHandler.loadAnomalyHandlerPropertiesAndConfigurators(configuratorNameLoad.getText()); 
        for(AnomalyConfigurator configurator : loadAnomalyHandler.getAnomaliesConfigurators()){
                System.out.println(configurator);
        }
        loadSuccess.setText("Load configuration success");
    }
    
    
    @FXML
    private void handleStartApplication(ActionEvent event) {       
        
    }
      
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        saveAnomalyHandler = new AnomalyHandler();
        fileSet.setItems(FXCollections.observableArrayList(
            "Zestaw 1", "Zestaw 2", "Zestaw 3"
        ));
        constantTankLeakageCount = 0;
    }    
    
}
