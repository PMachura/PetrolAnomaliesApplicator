/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probebreaccident;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.datatypes.TankMeasure;

/**
 *
 * @author Przemek
 */
public class ProbeHangApplicator {

    public static Collection<TankMeasure> applyProbeHang(Collection<TankMeasure> tankMeasures, Integer tankId, LocalDateTime startTime, LocalDateTime endTime) {
        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        Double readFuelHeight = null;
        for (TankMeasure tankMeasure : tankMeasures) {

            if (tankMeasure.getTankId().equals(tankId)
                    && (tankMeasure.getDateTime().isEqual(startTime)
                    || tankMeasure.getDateTime().isEqual(endTime)
                    || (tankMeasure.getDateTime().isAfter(startTime) && tankMeasure.getDateTime().isBefore(endTime)))) {
                if (readFuelHeight == null) {
                    readFuelHeight = tankMeasure.getFuelHeight();
                } else {
                    TankMeasure modifiedTankMeasure = tankMeasure.copy();
                    modifiedTankMeasure.setFuelHeight(readFuelHeight);
                    modifiedTankMeasures.add(modifiedTankMeasure);
                }
            }else{
                modifiedTankMeasures.add(tankMeasure.copy());
            }

        }
        return modifiedTankMeasures;
    }

}
