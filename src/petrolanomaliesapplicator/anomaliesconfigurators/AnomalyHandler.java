/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesconfigurators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.model.DataSetCollection;
import petrolanomaliesapplicator.model.DataSetFactory;

/**
 *
 * @author Przemek
 */
public class AnomalyHandler {
    
    private String name;
    private Collection <AnomalyConfigurator> anomaliesConfigurators = new ArrayList<AnomalyConfigurator>();
    private DataSetCollection inputDataSetCollection;
    private DataSetCollection outputDataSetCollection;
    private String inputDataSetFileFolder;
    private String outpuDataSetFileFolder;

    public void setDataSetFileFolders(String [] folders){
        inputDataSetFileFolder = folders[0];
        outpuDataSetFileFolder = folders[1];
    }
    
    public void saveOutputDataSet(){
        FileHandler.saveAnomalyHandlerOutputDataSetCollection(this);
    }
    
    public void loadInputDataSetCollection(){
        inputDataSetCollection = DataSetFactory.getDataSet(inputDataSetFileFolder);
    }
    
    public void addAnomalyConfigurator(AnomalyConfigurator configurator){
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
