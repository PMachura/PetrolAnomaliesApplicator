/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesapplicators.ConstantTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.VariableTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantTankLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.VariableTankLeakageConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class VariableTankLeakageTest {
    static void test() {
        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
        AnomalyConfigurator anomalyConfigurator = new VariableTankLeakageConfigurator(LocalDateTime.of(2014, 1, 1, 0, 5, 0), LocalDateTime.of(2014, 1, 1, 0, 15, 0), 1, 10.0);
        VariableTankLeakageApplicator applicator = new VariableTankLeakageApplicator( (VariableTankLeakageConfigurator) anomalyConfigurator);
       // ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) applicator.applyVariableTankLeakage(tankMeasures);
      //   ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) VariableTankLeakageApplicator.applyVariableTankLeakage(tankMeasures, LocalDateTime.of(2014,1,1,0,5,0),  LocalDateTime.of(2014,1,1,0,15,0),1,10.0);

        for (TankMeasure tankMeasure : tankMeasures) {
            System.out.println(applicator.applyVariableTankLeakage(tankMeasure));
        }

       //   for (TankMeasure tankMeasure : modifiedTankMeasures) {
        //     System.out.println(tankMeasure);
        //   }
    }

    public static void main(String[] args) {
        test();
    }
}
