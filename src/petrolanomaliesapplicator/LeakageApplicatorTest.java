/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesapplicators.ConstantTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.PipelineLeakageApplicator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */




public class LeakageApplicatorTest {
    
    static void pipelineLeakageTest(){
        ArrayList<NozzleMeasure> nozzleMeasures = (ArrayList<NozzleMeasure>) FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
        
        ArrayList<ArrayList<NozzleMeasure>> refuelingSeries = PipelineLeakageApplicator.findNozzleMesuresRefuelingSeries(nozzleMeasures, LocalDateTime.of(2014, 1, 1, 9,0), LocalDateTime.of(2014, 1, 1, 9,10),2, 14);
        for(ArrayList<NozzleMeasure> refuelinSerie : refuelingSeries){
            System.out.println("Nowa seria");
            for(NozzleMeasure nozzleMeasure : refuelinSerie){
                System.out.println(nozzleMeasure);
            }
        }
        PipelineLeakageApplicator.applyPipelineLeakage(tankMeasures, nozzleMeasures, LocalDateTime.of(2014, 1, 1, 9,0), LocalDateTime.of(2014, 1, 1, 9,10), 2, 14, 3.0);
    }
    
    public static void main(String [] args){
        pipelineLeakageTest();
    }
    
}
