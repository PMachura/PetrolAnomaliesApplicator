/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.datatypes;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collection;
import petrolanomaliesapplicator.leakage.TankLeakageApplicator;
import probebreaccident.ProbeHangApplicator;

/**
 *
 * @author Przemek
 */
public class Test {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        Collection<TankMeasure> tankMeasures = FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
        Collection<TankMeasure> editedTankMeasures = TankLeakageApplicator.applyVariableLeakage(tankMeasures, 1, LocalDateTime.of(2014, 1, 7, 23, 0, 0), LocalDateTime.of(2014, 1, 7, 23, 45, 0), 0.0);
        editedTankMeasures.forEach((TankMeasure tankMeasure) -> {
            if (tankMeasure.getTankId() == 1) {
                System.out.println(tankMeasure);
            }
        });
        //    FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
        //    FileHandler.loadRefuelMeasures("dane/Zestaw 1/refuel.log");
        //    FileHandler.loadHeightVolumeMapper("dane/mapowanie/Tank1_10012.csv");
        //   HeightVolumeMapper hightVolumeMapper = new HeightVolumeMapper(FileHandler.loadHightVolumeMappers());
    }

}
