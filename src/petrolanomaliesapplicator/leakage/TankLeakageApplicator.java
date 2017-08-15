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
public class TankLeakageApplicator {

    /**
     * Applys constant leakage to given TankMeasure collection Modifies only
     * fuel volume attribute
     *
     * @param tankMeasures
     * @param tankId
     * @param startTime
     * @param endTime
     * @param leakageVolumePerHour
     * @return
     */
    public static Collection<TankMeasure> applyConstantLeakage(Collection<TankMeasure> tankMeasures,
            Integer tankId, LocalDateTime startTime, LocalDateTime endTime, Double leakageVolumePerHour) {

        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        Double wholeLeakageVolume = TimeCalculator.durationInHours(startTime, endTime) * leakageVolumePerHour;
        
        for (TankMeasure tankMeasure : tankMeasures) {
            if (tankMeasure.getTankId().equals(tankId)
                    && (tankMeasure.getDateTime().isEqual(startTime)
                    || tankMeasure.getDateTime().isEqual(endTime)
                    || (tankMeasure.getDateTime().isAfter(startTime) && tankMeasure.getDateTime().isBefore(endTime)))) {

                TankMeasure modifiedTankMeasure = tankMeasure.copy();
                Double elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - elapsedHours * leakageVolumePerHour);
                modifiedTankMeasures.add(modifiedTankMeasure);

            } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
                TankMeasure modifiedTankMeasure = tankMeasure.copy();
                modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - wholeLeakageVolume);
                modifiedTankMeasures.add(modifiedTankMeasure);
            } else {
                modifiedTankMeasures.add(tankMeasure.copy());
            }

        }
        return modifiedTankMeasures;
    }

    /**
     * Apply variable leakage to given TankMeasure dataset which depends on fuel volume and leaking point height
     * Modifies only the fuel volume attribute
     * @param tankMeasures
     * @param tankId
     * @param startTime
     * @param endTime
     * @param leakageVolumePerHour
     * @return 
     */
    public static Collection<TankMeasure> applyVariableLeakage(Collection<TankMeasure> tankMeasures,
            Integer tankId, LocalDateTime startTime, LocalDateTime endTime, Double leakingPointHeight) {

        
        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        Double leakedFuelSum = 0.0;
      
        for (TankMeasure tankMeasure : tankMeasures) {
            if (tankMeasure.getTankId().equals(tankId)
                    && tankMeasure.getFuelHeight() > leakingPointHeight
                    && (tankMeasure.getDateTime().isEqual(startTime)
                    || tankMeasure.getDateTime().isEqual(endTime)
                    || (tankMeasure.getDateTime().isAfter(startTime) && tankMeasure.getDateTime().isBefore(endTime)))) {

                TankMeasure modifiedTankMeasure = tankMeasure.copy();
                Double elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                Double leakedFuel =  elapsedHours * leakIntensityVolumeDependinOnTankVolume(tankMeasure.getFuelVolume());
                modifiedTankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - leakedFuel);
                modifiedTankMeasures.add(modifiedTankMeasure);
                
                leakedFuelSum += leakedFuel;

            } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
                TankMeasure modifiedTankMeasure = tankMeasure.copy();
                modifiedTankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - leakedFuelSum);
                modifiedTankMeasures.add(modifiedTankMeasure);
            } else {
                modifiedTankMeasures.add(tankMeasure.copy());
            }

        }
        return modifiedTankMeasures;
    }
    
    
    private static Double leakIntensityVolumeDependinOnTankVolume(Double tankVolume){
        return Math.log(tankVolume);
    }
}
