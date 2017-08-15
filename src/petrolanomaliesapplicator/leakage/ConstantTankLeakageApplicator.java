/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.leakage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.datatypes.TankMeasure;
import petrolanomaliesapplicator.helpers.TimeCalculator;

/**
 *
 * @author Przemek
 */
public class ConstantTankLeakageApplicator {

    /**
     * Applys constant leakage to given TankMeasure collection
     * Modifies only fuel volume attribute
     * @param tankMeasures
     * @param tankId
     * @param startTime
     * @param endTime
     * @param leakageVolumePerHour
     * @return 
     */
    public static Collection<TankMeasure> applyConstantLeakage(Collection<TankMeasure> tankMeasures, Integer tankId, LocalDateTime startTime, LocalDateTime endTime, Double leakageVolumePerHour) {

        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        TankMeasure previousTankMeasure = null;
        Double elapsedHours = null;
        Double wholeLeakageVolume = TimeCalculator.durationInHours(startTime, endTime) * leakageVolumePerHour;
        for (TankMeasure tankMeasure : tankMeasures) {
            if (tankMeasure.getTankId().equals(tankId)
                    && (tankMeasure.getDateTime().isEqual(startTime)
                    || tankMeasure.getDateTime().isEqual(endTime)
                    || (tankMeasure.getDateTime().isAfter(startTime) && tankMeasure.getDateTime().isBefore(endTime)))) {

                TankMeasure modifiedTankMeasure = tankMeasure.copy();
                elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - elapsedHours * leakageVolumePerHour);
                modifiedTankMeasures.add(modifiedTankMeasure);

            } else if(tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
                TankMeasure modifiedTankMeasure = tankMeasure.copy();
                modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - wholeLeakageVolume);
                modifiedTankMeasures.add(modifiedTankMeasure);
            } else {
                modifiedTankMeasures.add(tankMeasure.copy());
            }

        }
        return modifiedTankMeasures;
    }
}
