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
public enum BaseDataSetLocation {
    ZESTAW1("dane/Zestaw 1","dane/Zestaw 1/tankMeasures.log","dane/Zestaw 1/nozzleMeasures.log","dane/Zestaw 1/refuel.log", "Zestaw 1"),
    ZESTAW2("dane/Zestaw 2","dane/Zestaw 2/tankMeasures.log","dane/Zestaw 2/nozzleMeasures.log","dane/Zestaw 2/refuel.log", "Zestaw 2"),
    ZESTAW3("dane/Zestaw 3","dane/Zestaw 3/tankMeasures.log","dane/Zestaw 3/nozzleMeasures.log","dane/Zestaw 3/refuel.log", "Zestaw 3");
    
    private String filePath;
    private String tankMeasurePath;
    private String nozzleMeasurePath;
    private String refuelMeasurePath;
    private String folderName;
    
    private BaseDataSetLocation(String filePath, String tankMeasurePath, String nozzleMeasurePath, String refuelMeasurePath, String folderName){
        this.filePath = filePath;
        this.tankMeasurePath = tankMeasurePath;
        this.nozzleMeasurePath = nozzleMeasurePath;
        this.refuelMeasurePath = refuelMeasurePath;
        this.folderName = folderName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTankMeasurePath() {
        return tankMeasurePath;
    }

    public void setTankMeasurePath(String tankMeasurePath) {
        this.tankMeasurePath = tankMeasurePath;
    }

    public String getNozzleMeasurePath() {
        return nozzleMeasurePath;
    }

    public void setNozzleMeasurePath(String nozzleMeasurePath) {
        this.nozzleMeasurePath = nozzleMeasurePath;
    }

    public String getRefuelMeasurePath() {
        return refuelMeasurePath;
    }

    public void setRefuelMeasurePath(String refuelMeasurePath) {
        this.refuelMeasurePath = refuelMeasurePath;
    }

    public String getFolderName() {
        return folderName;
    }

    
    
    
    
    
}
