/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.datatypes;

import java.io.FileNotFoundException;
import java.text.ParseException;

/**
 *
 * @author Przemek
 */
public class Test {
    
    
    public static void main(String [] args) throws FileNotFoundException, ParseException{
    //   FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
    //    FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
    //    FileHandler.loadRefuelMeasures("dane/Zestaw 1/refuel.log");
        FileHandler.loadHeightVolumeMapper("dane/mapowanie/Tank1_10012.csv");
        HeightVolumeMapper hightVolumeMapper = new HeightVolumeMapper(FileHandler.loadHightVolumeMappers());
    }
    
}
