/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesapplicators.MeterMiscalibrationApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.VariableTankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.VariableTankLeakageConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.service.ModelDataCollectionHelper;
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.factory.DataSetFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class MeterMiscalibrationTest {
    static void test() {
        
        LocalDateTime start = LocalDateTime.of(2014, 1, 1, 1, 11, 0);
        LocalDateTime end = LocalDateTime.of(2014, 1, 1, 1, 20, 0);
        Integer tankId = 2;
        Integer gunId = 14;
        Double miscalibrationCoefficientPerOneCubicMeter = 0.3;//02:05 04:55
        
        ArrayList<NozzleMeasure> nozzleMeasures = (ArrayList<NozzleMeasure>) DataSetFactory.getNozzleMeasures(BaseDataSetLocation.ZESTAW1.getFolderName());
        AnomalyConfigurator anomalyConfigurator = new MeterMiscalibrationConfigurator(start,end,tankId,gunId,miscalibrationCoefficientPerOneCubicMeter);
        MeterMiscalibrationApplicator applicator = new MeterMiscalibrationApplicator((MeterMiscalibrationConfigurator) anomalyConfigurator);
        ArrayList<NozzleMeasure> modifiedNozzleMeasure = (ArrayList<NozzleMeasure>) applicator.applyMeterMiscalibration(nozzleMeasures);
        //ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) VariableTankLeakageApplicator.applyVariableTankLeakage(tankMeasures, LocalDateTime.of(2014,1,1,0,5,0),  LocalDateTime.of(2014,1,1,0,15,0),1,10.0);

        ArrayList<NozzleMeasure> modifiedSelectedTankTankMeasures = ModelDataCollectionHelper.getMatching(modifiedNozzleMeasure, start.minusMinutes(5), end.plusMinutes(5), tankId, gunId);
        ArrayList<NozzleMeasure> selectedTankTankMeasures = ModelDataCollectionHelper.getMatching(nozzleMeasures, start.minusMinutes(5), end.plusMinutes(5), tankId, gunId);
        
        FileHandler.saveNozzleMEasuresToFileSuitableForExcel(selectedTankTankMeasures, "selectedTankMeasures.txt");
        FileHandler.saveNozzleMEasuresToFileSuitableForExcel(modifiedSelectedTankTankMeasures, "modifiedSelectedTankMeasures.txt");
        
        for (NozzleMeasure nozzleMeasure : nozzleMeasures) {
     //       System.out.println(applicator.applyMeterMiscalibration(nozzleMeasure));
        }

       //   for (NozzleMeasure nozzleMeasure : modifiedNozzleMeasures) {
        //     System.out.println(nozzleMeasure);
        //   }
    }

    public static void main(String[] args) {
        test();
    }
}
