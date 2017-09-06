/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.anomaliesconfigurators.VariableTankLeakageConfigurator;
import petrolanomaliesapplicator.service.TimeCalculator;
import petrolanomaliesapplicator.service.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.factory.FuelHeightVolumeMapperFactory;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class VariableTankLeakageApplicator extends AnomalyApplicator {

    private Double leakingPointHeight;
    private Double leakedFuelSum = 0.0;
    private FuelHeightVolumeMapper volumeToHeightMapper;
    private Double newCurrentFuelHeight = 0.0;
    private TankMeasure lastModifiedTankMeasure;

    public VariableTankLeakageApplicator(VariableTankLeakageConfigurator configurator) {
        super(configurator);
        this.leakingPointHeight = configurator.getLeakingPointHeight();
        this.leakedFuelSum = new Double(0.0);
        newCurrentFuelHeight = new Double(0.0);
        lastModifiedTankMeasure = null;
        volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);
    }

    /**
     * Apply variable leakage to given TankMeasure dataset which depends on fuel
     * volume and leaking point height Modifies only the fuel volume attribute
     *
     * @param tankMeasures
     * @param tankId
     * @param startTime
     * @param endTime
     * @param leakageVolumePerHour
     * @return
     */
    public static Collection<TankMeasure> applyVariableTankLeakage(Collection<TankMeasure> tankMeasures,
            LocalDateTime startTime, LocalDateTime endTime, Integer tankId, Double leakingPointHeight) {

        ArrayList<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        Double leakedFuelSum = 0.0;
        FuelHeightVolumeMapper volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);
        Double newCurrentFuelHeight = 0.0;
        TankMeasure lastModifiedTankMeasure =null;

        for (TankMeasure tankMeasure : tankMeasures) {
            if (tankMeasure.getTankId().equals(tankId)
                    && (tankMeasure.getFuelHeight() > leakingPointHeight)
                    && (tankMeasure.getFuelHeight() > newCurrentFuelHeight)
                    && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {

                TankMeasure modifiedTankMeasure = tankMeasure.clone();

                Double elapsedHours;
                Double leakedFuel;
                if (lastModifiedTankMeasure == null) {
                    elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                    leakedFuel = elapsedHours * leakIntensityVolumeDependinOnTankVolume(tankMeasure.getFuelVolume());
        
                } else {
                    elapsedHours = TimeCalculator.durationInHours(lastModifiedTankMeasure.getDateTime(), tankMeasure.getDateTime());
                    leakedFuel = elapsedHours * leakIntensityVolumeDependinOnTankVolume(lastModifiedTankMeasure.getFuelVolume());
              
                }
                leakedFuelSum += leakedFuel;

                modifiedTankMeasure.verifySetFuelVolume(tankMeasure.getFuelVolume() - leakedFuelSum);

                newCurrentFuelHeight = volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume());
                modifiedTankMeasure.verifySetFuelHeight(newCurrentFuelHeight);

                lastModifiedTankMeasure = modifiedTankMeasure;
                modifiedTankMeasures.add(modifiedTankMeasure);


            } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(startTime)) {

                TankMeasure modifiedTankMeasure = tankMeasure.clone();
                modifiedTankMeasure.verifySetFuelVolume(modifiedTankMeasure.getFuelVolume() - leakedFuelSum);
                modifiedTankMeasure.verifySetFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
                modifiedTankMeasures.add(modifiedTankMeasure);
 
            } else {

                modifiedTankMeasures.add(tankMeasure.clone());
            }

        }
        return modifiedTankMeasures;
    }

    public Collection<TankMeasure> applyVariableTankLeakage(Collection<TankMeasure> tankMeasures) {

        return VariableTankLeakageApplicator.this.applyVariableTankLeakage(tankMeasures, startTime, endTime, tankId, leakingPointHeight);
    }

    public TankMeasure applyVariableTankLeakage(TankMeasure tankMeasure) {

        TankMeasure modifiedTankMeasure = tankMeasure.clone();
        if (tankMeasure.getTankId().equals(tankId)
                && (tankMeasure.getFuelHeight() > leakingPointHeight)
                && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {

            Double elapsedHours;
            Double leakedFuel;
            if (lastModifiedTankMeasure==null) {
                elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                leakedFuel = elapsedHours * leakIntensityVolumeDependinOnTankVolume(tankMeasure.getFuelVolume());
            } else {
                elapsedHours = TimeCalculator.durationInHours(lastModifiedTankMeasure.getDateTime(), tankMeasure.getDateTime());
                leakedFuel = elapsedHours * leakIntensityVolumeDependinOnTankVolume(lastModifiedTankMeasure.getFuelVolume());
            }
            leakedFuelSum += leakedFuel;
            modifiedTankMeasure.verifySetFuelVolume(tankMeasure.getFuelVolume() - leakedFuelSum);

            newCurrentFuelHeight = volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume());
            modifiedTankMeasure.verifySetFuelHeight(newCurrentFuelHeight);

        } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
            modifiedTankMeasure.verifySetFuelVolume(tankMeasure.getFuelVolume() - leakedFuelSum);
            modifiedTankMeasure.verifySetFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
        }
        return modifiedTankMeasure;
    }

    private static Double leakIntensityVolumeDependinOnTankVolume(Double tankVolume) {
        return Math.sqrt(tankVolume) * (0.3);
    }
}
