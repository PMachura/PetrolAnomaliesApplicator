/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesapplicators.ProbeHangApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.VariableTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ProbeHangConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.VariableTankLeakageConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.service.ModelDataCollectionHelper;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class ProbeHangTest {
    static void test() {
               
        LocalDateTime start = LocalDateTime.of(2014, 1, 1, 0, 30, 0);
        LocalDateTime end = LocalDateTime.of(2014, 1, 1, 2, 30, 0);
        Integer tankId = 1;
        Double leakingPointHeight = 1340.0;//02:05 04:55
        
        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
      //  ArrayList<TankMeasure> tankMeasuresForModyfing = ModelDataCollectionHelper.getMatching(tankMeasures, start, end, tankId);
        
        AnomalyConfigurator anomalyConfigurator = new ProbeHangConfigurator(start,end,tankId);
        ProbeHangApplicator applicator = new ProbeHangApplicator(  (ProbeHangConfigurator) anomalyConfigurator);
        
        ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) applicator.applyProbeHang(tankMeasures);
        
        ArrayList<TankMeasure> modifiedSelectedTankTankMeasures = ModelDataCollectionHelper.getMatching(modifiedTankMeasures, start.minusHours(1), end.plusHours(1), tankId);
        ArrayList<TankMeasure> selectedTankTankMeasures = ModelDataCollectionHelper.getMatching(tankMeasures, start.minusHours(1), end.plusHours(1), tankId);
        
        FileHandler.saveTankMeasuresToFileSuitableForExcel(selectedTankTankMeasures, "selectedTankMeasures.txt");
        FileHandler.saveTankMeasuresToFileSuitableForExcel(modifiedSelectedTankTankMeasures, "modifiedSelectedTankMeasures.txt");
        
      //   ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) VariableTankLeakageApplicator.applyVariableTankLeakage(tankMeasures, LocalDateTime.of(2014,1,1,0,5,0),  LocalDateTime.of(2014,1,1,0,15,0),1,10.0);

     //   for (TankMeasure tankMeasure : tankMeasures) {
     //       System.out.println(applicator.applyVariableTankLeakage(tankMeasure));
     //   }

          for (TankMeasure tankMeasure : modifiedSelectedTankTankMeasures) {
        //     System.out.println(tankMeasure);
          }
    }

    public static void main(String[] args) {
        test();
    }
}
