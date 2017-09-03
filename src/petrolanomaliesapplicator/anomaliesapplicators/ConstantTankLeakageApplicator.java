/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantTankLeakageConfigurator;

import petrolanomaliesapplicator.helpers.TimeCalculator;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapperFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class ConstantTankLeakageApplicator extends AnomalyApplicator {

    private Double leakageVolumePerHour;
    private FuelHeightVolumeMapper volumeToHeightMapper;
    private Double wholeLeakageVolume;

    public ConstantTankLeakageApplicator(ConstantTankLeakageConfigurator configurator){
        super(configurator);
        this.leakageVolumePerHour = configurator.getLeakageVolumePerHour();
        this.wholeLeakageVolume = TimeCalculator.durationInHours(startTime, endTime) * leakageVolumePerHour;
        this.volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);
    }
    /**
     * Applys constant leakage to given TankMeasure collection
     *
     * @param tankMeasures
     * @param tankId
     * @param startTime
     * @param endTime
     * @param leakageVolumePerHour
     * @return
     */
    public static Collection<TankMeasure> applyConstantTankLeakage(Collection<TankMeasure> tankMeasures,
             LocalDateTime startTime, LocalDateTime endTime,Integer tankId, Double leakageVolumePerHour) {

        Collection<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>();
        //Collection<TankMeasure> modified = tankMeasures.stream().map(tankMeasure -> tankMeasure.clone()).collect(Collectors.toCollection(ArrayList::new));
        Double wholeLeakageVolume = TimeCalculator.durationInHours(startTime, endTime) * leakageVolumePerHour;
        FuelHeightVolumeMapper volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);
        
 

        for (TankMeasure tankMeasure : tankMeasures) {
            if (tankMeasure.getTankId().equals(tankId)
                    && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {

                TankMeasure modifiedTankMeasure = tankMeasure.clone();
                Double elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
                System.out.println("HELLLLOOOOO");
           //     System.out.println(elapsedHours);
             //   System.out.println(leakageVolumePerHour);
                modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - elapsedHours * leakageVolumePerHour);
                modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
                modifiedTankMeasures.add(modifiedTankMeasure);

            } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
                TankMeasure modifiedTankMeasure = tankMeasure.clone();
                modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - wholeLeakageVolume);
                modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
                modifiedTankMeasures.add(modifiedTankMeasure);
            } else {
                modifiedTankMeasures.add(tankMeasure.clone());
            }

        }
        return modifiedTankMeasures;

    }
    
    public Collection<TankMeasure> applyConstantTankLeakage(Collection<TankMeasure> tankMeasures) {

        return applyConstantTankLeakage(tankMeasures, startTime, endTime,  tankId, leakageVolumePerHour);

    }

    public TankMeasure applyConstantTankLeakage(TankMeasure tankMeasure) {

        TankMeasure modifiedTankMeasure = tankMeasure.clone();

        if (tankMeasure.getTankId().equals(tankId)
                && (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()))) {
            Double elapsedHours = TimeCalculator.durationInHours(startTime, tankMeasure.getDateTime());
            modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - elapsedHours * leakageVolumePerHour);
            modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));

        } else if (tankMeasure.getTankId().equals(tankId) && tankMeasure.getDateTime().isAfter(endTime)) {
            modifiedTankMeasure.setFuelVolume(modifiedTankMeasure.getFuelVolume() - wholeLeakageVolume);
            modifiedTankMeasure.setFuelHeight(volumeToHeightMapper.calculate(modifiedTankMeasure.getFuelVolume()));
        }

        return modifiedTankMeasure;
    }
}


