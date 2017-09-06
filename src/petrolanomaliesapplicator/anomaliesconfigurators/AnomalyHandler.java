/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesconfigurators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import petrolanomaliesapplicator.anomaliesapplicators.AnomalyApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.ConstantTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.MeterMiscalibrationApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.PipelineLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.ProbeHangApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.VariableTankLeakageApplicator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.model.DataSetCollection;
import petrolanomaliesapplicator.factory.DataSetFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class AnomalyHandler {

    private String name;
    private Collection<AnomalyConfigurator> anomaliesConfigurators = new ArrayList<AnomalyConfigurator>();
    private DataSetCollection inputDataSetCollection;
    private DataSetCollection outputDataSetCollection = new DataSetCollection();
    private String inputDataSetFileFolder;
    private String outpuDataSetFileFolder;

    public void setDataSetFileFolders(String[] folders) {
        inputDataSetFileFolder = folders[0];
        outpuDataSetFileFolder = folders[1];
    }

    public void saveOutputDataSet() {
        if(outputDataSetCollection.getNozzleMeasures() == null){
            outputDataSetCollection.setNozzleMeasures(inputDataSetCollection.getNozzleMeasures());
        }
        if(outputDataSetCollection.getTankMeasures()== null){
            outputDataSetCollection.setTankMeasures(inputDataSetCollection.getTankMeasures());
        }
        if(outputDataSetCollection.getRefuelMeasures()== null){
            outputDataSetCollection.setRefuelMeasures(inputDataSetCollection.getRefuelMeasures());
        }
        FileHandler.saveAnomalyHandlerOutputDataSetCollection(this);
    }

    public void applyAnomalies() {
        loadInputDataSetCollection();
        for (AnomalyConfigurator configurator : anomaliesConfigurators) {
            switch (configurator.anomalyTpe) {
                case CONSTANT_LEAKAGE:
                   ConstantTankLeakageApplicator constantTankLeakageApplicator = new ConstantTankLeakageApplicator((ConstantTankLeakageConfigurator) configurator);  
                   outputDataSetCollection.setTankMeasures((ArrayList<TankMeasure>) constantTankLeakageApplicator.applyConstantTankLeakage(inputDataSetCollection.getTankMeasures()));
                    break;
                case METER_MISCALIBRATION:
                   MeterMiscalibrationApplicator meterMiscalibrationApplicator = new MeterMiscalibrationApplicator((MeterMiscalibrationConfigurator) configurator);
                   outputDataSetCollection.setNozzleMeasures((ArrayList<NozzleMeasure>) meterMiscalibrationApplicator.applyMeterMiscalibration(inputDataSetCollection.getNozzleMeasures()));
                    break;
                case PIPELINE_LEAKAGE:
                   PipelineLeakageApplicator pipelineLeakageApplicator = new PipelineLeakageApplicator((PipelineLeakageConfigurator) configurator);
                   outputDataSetCollection.setTankMeasures((ArrayList<TankMeasure>) pipelineLeakageApplicator.applyPipelineLeakage(inputDataSetCollection.getTankMeasures(), inputDataSetCollection.getNozzleMeasures()));
                    break;
                case PROBE_HANG:
                   ProbeHangApplicator probeHangApplicator = new ProbeHangApplicator((ProbeHangConfigurator) configurator);
                   outputDataSetCollection.setTankMeasures((ArrayList<TankMeasure>) probeHangApplicator.applyProbeHang(inputDataSetCollection.getTankMeasures()));
                    break;
                case VARIABLE_LEAKAGE:
                    VariableTankLeakageApplicator variableTankLeakageApplicator = new VariableTankLeakageApplicator((VariableTankLeakageConfigurator) configurator);
                    outputDataSetCollection.setTankMeasures((ArrayList<TankMeasure>) variableTankLeakageApplicator.applyVariableTankLeakage(inputDataSetCollection.getTankMeasures()));
                    break;
            }
        }
    }

    public void loadInputDataSetCollection() {
        inputDataSetCollection = DataSetFactory.getDataSet(inputDataSetFileFolder);
    }

    public void addAnomalyConfigurator(AnomalyConfigurator configurator) {
        anomaliesConfigurators.add(configurator);
    }

    public Collection<AnomalyConfigurator> getAnomaliesConfigurators() {
        return anomaliesConfigurators;
    }

    public void setAnomaliesConfigurators(Collection<AnomalyConfigurator> anomaliesConfigurators) {
        this.anomaliesConfigurators = anomaliesConfigurators;
    }

    public String getInputDataSetFileFolder() {
        return inputDataSetFileFolder;
    }

    public void setInputDataSetFileFolder(String inputDataSetFileFolder) {
        this.inputDataSetFileFolder = inputDataSetFileFolder;
    }

    public String getOutpuDataSetFileFolder() {
        return outpuDataSetFileFolder;
    }

    public void setOutpuDataSetFileFolder(String outpuDataSetFileFolder) {
        this.outpuDataSetFileFolder = outpuDataSetFileFolder;
    }

    public DataSetCollection getInputDataSetCollection() {
        return inputDataSetCollection;
    }

    public void setInputDataSetCollection(DataSetCollection inputDataSetCollection) {
        this.inputDataSetCollection = inputDataSetCollection;
    }

    public DataSetCollection getOutputDataSetCollection() {
        return outputDataSetCollection;
    }

    public void setOutputDataSetCollection(DataSetCollection outputDataSetCollection) {
        this.outputDataSetCollection = outputDataSetCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
