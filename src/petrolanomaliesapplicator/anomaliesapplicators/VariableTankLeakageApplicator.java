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
import petrolanomaliesapplicator.helpers.TimeCalculator;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapperFactory;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class VariableTankLeakageApplicator extends AnomalyApplicator {

    private Double leakingPointHeight;
    private Double leakedFuelSum = 0.0;
    private FuelHeightVolumeMapper volumeToHeightMapper;
    
    public VariableTankLeakageApplicator(VariableTankLeakageConfigurator configurator){
        super(configurator);
        this.leakingPointHeight = configurator.getLeakingPointHeight();
        this.leakedFuelSum = new Double(0.0);
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

        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        Double leakedFuelSum = 0.0;
        FuelHeightVolumeMapper volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);

        for (TankMeasure tankMeasure : tankMeasures) {
            if (tankMeasure.getTankId().equals(tankId)
                    && (tankMeasure.getFuelHeight() > leakingPointHeight)
                    && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {

                TankMeasure modifiedTankMeasure = tankMeasure.clone();
                Double elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                Double leakedFuel = elapsedHours * leakIntensityVolumeDependinOnTankVolume(tankMeasure.getFuelVolume());
                modifiedTankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - leakedFuel);
                modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
                modifiedTankMeasures.add(modifiedTankMeasure);

                leakedFuelSum += leakedFuel;

            } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
                TankMeasure modifiedTankMeasure = tankMeasure.clone();
                modifiedTankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - leakedFuelSum);
                modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
                modifiedTankMeasures.add(modifiedTankMeasure);
            } else {
                modifiedTankMeasures.add(tankMeasure.clone());
            }

        }
        return modifiedTankMeasures;
    }
    
    public  Collection<TankMeasure> applyVariableTankLeakage(Collection<TankMeasure> tankMeasures) {
     
        return VariableTankLeakageApplicator.this.applyVariableTankLeakage(tankMeasures,  startTime, endTime, tankId, leakingPointHeight);
    }
    
    public TankMeasure applyVariableTankLeakage(TankMeasure tankMeasure){
       
        TankMeasure modifiedTankMeasure = tankMeasure.clone();     
        if (tankMeasure.getTankId().equals(tankId)
                    && (tankMeasure.getFuelHeight() > leakingPointHeight)
                    && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {
            
                Double elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                Double leakedFuel = elapsedHours * leakIntensityVolumeDependinOnTankVolume(tankMeasure.getFuelVolume());
                modifiedTankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - leakedFuel);
                modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
               

                leakedFuelSum += leakedFuel;

            } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
                modifiedTankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - leakedFuelSum);
                modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
            } 
        return modifiedTankMeasure;
    }

    private static Double leakIntensityVolumeDependinOnTankVolume(Double tankVolume) {
        return Math.log(tankVolume);
    }
}
