/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.helpers.TimeCalculator;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class ProbeHangApplicator extends AnomalyApplicator {

    private Double readFuelHeight = null;
    private Double readFuelVolume = null;

    /**
     * Applys probe hang anomaly to given TankMeasure collection Modifies only
     * fuel height attribute
     *
     * @param tankMeasures
     * @param tankId
     * @param startTime
     * @param endTime
     * @return
     */
    public static Collection<TankMeasure> applyProbeHang(Collection<TankMeasure> tankMeasures, Integer tankId, LocalDateTime startTime, LocalDateTime endTime) {
        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        Double readFuelHeight = null;
        for (TankMeasure tankMeasure : tankMeasures) {

            if (tankMeasure.getTankId().equals(tankId)
                    && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {
                if (readFuelHeight == null) {
                    readFuelHeight = tankMeasure.getFuelHeight();
                } else {
                    TankMeasure modifiedTankMeasure = tankMeasure.copy();
                    modifiedTankMeasure.setFuelHeight(readFuelHeight);
                    modifiedTankMeasures.add(modifiedTankMeasure);
                }
            } else {
                modifiedTankMeasures.add(tankMeasure.copy());
            }

        }
        return modifiedTankMeasures;
    }

    public TankMeasure applyPorbeHang(TankMeasure tankMeasure) {
        
        TankMeasure modifiedTankMeasure = tankMeasure.copy();
        if (tankMeasure.getTankId().equals(tankId)
                && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {
            if (readFuelHeight == null) {
                readFuelHeight = tankMeasure.getFuelHeight();
                readFuelVolume = tankMeasure.getFuelVolume();
            } else {

                modifiedTankMeasure.setFuelHeight(readFuelHeight);
                modifiedTankMeasure.setFuelVolume(readFuelVolume);
            }
        }

        return modifiedTankMeasure;
    }

}
