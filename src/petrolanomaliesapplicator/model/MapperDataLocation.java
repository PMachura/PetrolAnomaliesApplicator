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
public enum MapperDataLocation {
    TANK_1("dane/mapowanie/Tank1_10012.csv"),
    TANK_2("dane/mapowanie/Tank2_20000.csv"),
    TANK_3("dane/mapowanie/Tank3_30000.csv"),
    TANK_4("dane/mapowanie/Tank4_40000.csv");
    
    private String filePath;
    
    private MapperDataLocation(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    
}
