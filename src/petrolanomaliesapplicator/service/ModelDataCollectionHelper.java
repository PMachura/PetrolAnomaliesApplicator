/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class ModelDataCollectionHelper {
    
    public static ArrayList<TankMeasure> getMatching(ArrayList<TankMeasure> tankMeasures, LocalDateTime start, LocalDateTime end, Integer tankId){
        ArrayList<TankMeasure> selectedTankMeasures = new ArrayList<TankMeasure>();
        for(TankMeasure tankMeasure : tankMeasures){
            if(isMatch(tankMeasure, start, end, tankId)){
                selectedTankMeasures.add(tankMeasure);
            }
        }
        
        return selectedTankMeasures;
    }
    public static ArrayList<NozzleMeasure> getMatching(ArrayList<NozzleMeasure> nozzleMeasures, LocalDateTime start, LocalDateTime end, Integer tankId, Integer gunId){
        ArrayList<NozzleMeasure> selectedTankMeasures = new ArrayList<NozzleMeasure>();
        for(NozzleMeasure nozzleMeasure : nozzleMeasures){
            if(isMatch(nozzleMeasure, start, end, tankId, gunId)){
                selectedTankMeasures.add(nozzleMeasure);
            }
        }        
        return selectedTankMeasures;
    }
    
    
    public static TankMeasure getIfMatching(TankMeasure tankMeasure,LocalDateTime start, LocalDateTime end, Integer tankId ){
        return isMatch(tankMeasure, start, end, tankId) ? tankMeasure : null;
    }
    
    private static boolean isMatch(TankMeasure tankMeasure, LocalDateTime start, LocalDateTime end, Integer tankId){
        return tankMeasure.getTankId().equals(tankId) && TimeCalculator.isDateInRange(start, end, tankMeasure.getDateTime()) ? true : false;
    }
    
    private static boolean isMatch(NozzleMeasure nozzleMeasure, LocalDateTime start, LocalDateTime end, Integer tankId, Integer gunId){
        return nozzleMeasure.getTankId().equals(tankId) && nozzleMeasure.getGunId().equals(gunId) && TimeCalculator.isDateInRange(start, end, nozzleMeasure.getDateTime());
    }
}
