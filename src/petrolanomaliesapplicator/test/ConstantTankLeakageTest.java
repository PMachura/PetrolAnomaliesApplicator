/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import javafx.util.converter.LocalDateTimeStringConverter;
import petrolanomaliesapplicator.anomaliesapplicators.ConstantTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.PipelineLeakageApplicator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantTankLeakageConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.service.ModelDataCollectionHelper;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class ConstantTankLeakageTest {

    static void test() {
        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");

        LocalDateTime start = LocalDateTime.of(2014, 1, 1, 0, 30, 0);
        LocalDateTime end = LocalDateTime.of(2014, 1, 1, 2, 30, 0);
        Integer tankId = 1;
        Double leakageVolumePerHour = 20.0;

        AnomalyConfigurator anomalyConfigurator = new ConstantTankLeakageConfigurator(start, end, tankId, leakageVolumePerHour);
        ConstantTankLeakageApplicator applicator = new ConstantTankLeakageApplicator((ConstantTankLeakageConfigurator) anomalyConfigurator);

        ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) applicator.applyConstantTankLeakage(tankMeasures);

        ArrayList<TankMeasure> modifiedSelectedTankTankMeasures = ModelDataCollectionHelper.getMatching(modifiedTankMeasures, start.minusHours(1), end.plusHours(1), tankId);
        ArrayList<TankMeasure> selectedTankTankMeasures = ModelDataCollectionHelper.getMatching(tankMeasures, start.minusHours(1), end.plusHours(1), tankId);

        FileHandler.saveTankMeasuresToFileSuitableForExcel(selectedTankTankMeasures, "selectedTankTankMeasures.txt");
        FileHandler.saveTankMeasuresToFileSuitableForExcel(modifiedSelectedTankTankMeasures, "modifiedSelectedTankTankMeasures.txt");
//  ConstantTankLeakageApplicator.applyConstantTankLeakage(tankMeasures, LocalDateTime.of(2014,1,1,0,5,0),  LocalDateTime.of(2014,1,1,0,15,0),1,5.0);

        //    for (TankMeasure tankMeasure : tankMeasures) {
        //      System.out.println(applicator.applyConstantTankLeakage(tankMeasure));
        //  }
        //  for (TankMeasure tankMeasure : modifiedTankMeasures) {
        //     System.out.println(tankMeasure);
        //   }
    }

    public static void main(String[] args) {
        test();
    }
}
