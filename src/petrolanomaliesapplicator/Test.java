/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.io.File;
import java.io.FileInputStream;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.anomaliesapplicators.TankLeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.ProbeHangApplicator;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.RefuelMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class Test {


    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {
        
        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
        ArrayList<NozzleMeasure> nozzleMeasures = (ArrayList<NozzleMeasure>) FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
        ArrayList<RefuelMeasure> refuelMeasures = (ArrayList<RefuelMeasure>) FileHandler.loadRefuelMeasures("dane/Zestaw 1/refuel.log");
        
        ArrayList<TankMeasure> tankMeasuresList = new ArrayList<TankMeasure>();
        tankMeasuresList.add(tankMeasures.get(0));
        tankMeasuresList.add(tankMeasures.get(1));
        
       // FileHandler.saveTankMeasuresToFile(tankMeasuresList, "saveTest.txt");
       // FileHandler.saveNozzleMeasuresToFile(nozzleMeasures, "saveTest.txt");
       FileHandler.saveRefuelMeasuresToFile(refuelMeasures, "saveTest.txt");
        //    Collection<TankMeasure> editedTankMeasures = TankLeakageApplicator.applyVariableLeakage(tankMeasures, 1, LocalDateTime.of(2014, 1, 7, 23, 0, 0), LocalDateTime.of(2014, 1, 7, 23, 45, 0), 0.0);
//        editedTankMeasures.forEach((TankMeasure tankMeasure) -> {
//            if (tankMeasure.getTankId() == 1) {
//                System.out.println(tankMeasure);
//            }
//        });
        //    FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
        //    FileHandler.loadRefuelMeasures("dane/Zestaw 1/refuel.log");
        //    FileHandler.loadHeightVolumeMapper("dane/mapowanie/Tank1_10012.csv");
        //   HeightVolumeMapper hightVolumeMapper = new HeightVolumeMapper(FileHandler.loadHightVolumeMappers());
    }

}
