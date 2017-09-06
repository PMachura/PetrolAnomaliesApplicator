/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesapplicators.ConstantTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.PipelineLeakageApplicator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.service.ModelDataCollectionHelper;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */





public class PipelineLeakageTest {
    
    static void pipelineLeakageTest(){
        
        LocalDateTime start = LocalDateTime.of(2014, 1, 1, 9,0);
        LocalDateTime end = LocalDateTime.of(2014, 1, 1, 11,0);
        Integer tankId = 2;
        Integer gunId = 14;
        Double leakageVolumePerCubicMeter = 0.5;//02:05 04:55
        
        ArrayList<NozzleMeasure> nozzleMeasures = (ArrayList<NozzleMeasure>) FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
        
        ArrayList<ArrayList<NozzleMeasure>> refuelingSeries = PipelineLeakageApplicator.findNozzleMesuresRefuelingSeries(nozzleMeasures,start,end,tankId,gunId);
        int i =0;
        for(ArrayList<NozzleMeasure> refuelinSerie : refuelingSeries){
            FileHandler.saveNozzleMEasuresToFileSuitableForExcel(refuelinSerie, "refuelingSerie " + i + ".txt");
            for(NozzleMeasure nozzleMeasure : refuelinSerie){
              //  System.out.println(nozzleMeasure);
              
            }
            i++;
        }
        ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) PipelineLeakageApplicator.applyPipelineLeakage(tankMeasures, nozzleMeasures, start,end, tankId, gunId, leakageVolumePerCubicMeter);
        
        ArrayList<TankMeasure> modifiedSelectedTankTankMeasures = ModelDataCollectionHelper.getMatching(modifiedTankMeasures, start.minusMinutes(30), end.plusMinutes(30), tankId);
        ArrayList<TankMeasure> selectedTankTankMeasures = ModelDataCollectionHelper.getMatching(tankMeasures,start.minusMinutes(30), end.plusMinutes(30), tankId);
        
        FileHandler.saveTankMeasuresToFileSuitableForExcel(selectedTankTankMeasures, "selectedTankMeasures.txt");
        FileHandler.saveTankMeasuresToFileSuitableForExcel(modifiedSelectedTankTankMeasures, "modifiedSelectedTankMeasures.txt");
    }
    
    public static void main(String [] args){
        pipelineLeakageTest();
    }
    
}
