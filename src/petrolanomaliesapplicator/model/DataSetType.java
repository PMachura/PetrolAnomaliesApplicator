/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

/**
 *
 * @author Przemek
 */
public enum DataSetType {
    TANK_MEASURES("tankMeasures.log"),
    REFUEL_MEASURES("refuel.log"),
    NOZZLE_MEASURES("nozzleMeasures.log");
    
    private String fileName;
    
    private DataSetType(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
    
    
}
