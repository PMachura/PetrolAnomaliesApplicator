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
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.model.DataSetFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class MeterMiscalibrationTest {
    static void test() {
        ArrayList<NozzleMeasure> nozzleMeasures = (ArrayList<NozzleMeasure>) DataSetFactory.getNozzleMeasures(BaseDataSetLocation.ZESTAW1.getFolderName());
        AnomalyConfigurator anomalyConfigurator = new MeterMiscalibrationConfigurator(LocalDateTime.of(2014, 1, 1, 1, 11, 0), LocalDateTime.of(2014, 1, 1, 1, 14, 0), 2, 14, 0.5);
        MeterMiscalibrationApplicator applicator = new MeterMiscalibrationApplicator((MeterMiscalibrationConfigurator) anomalyConfigurator);
       // ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) applicator.applyVariableTankLeakage(tankMeasures);
      //   ArrayList<TankMeasure> modifiedTankMeasures = (ArrayList<TankMeasure>) VariableTankLeakageApplicator.applyVariableTankLeakage(tankMeasures, LocalDateTime.of(2014,1,1,0,5,0),  LocalDateTime.of(2014,1,1,0,15,0),1,10.0);

        for (NozzleMeasure nozzleMeasure : nozzleMeasures) {
            System.out.println(applicator.applyMeterMiscalibration(nozzleMeasure));
        }

       //   for (NozzleMeasure nozzleMeasure : modifiedNozzleMeasures) {
        //     System.out.println(nozzleMeasure);
        //   }
    }

    public static void main(String[] args) {
        test();
    }
}
