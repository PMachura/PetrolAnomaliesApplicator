/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.model.NozzleMeasure;


/**
 *
 * @author Przemek
 */
public class MeterMiscalibrationApplicator {

    public static Collection<NozzleMeasure> applyMeterMiscalibration(Collection<NozzleMeasure> nozzleMeasures,
            Integer tankId, Integer gunId, LocalDateTime startTime, LocalDateTime endTime, Double miscalibrationCoefficientPerOneCubicMeter) {

        Collection<NozzleMeasure> modifiedNozzleMeasures = new ArrayList<NozzleMeasure>();
        Double previousTotalCounter = 0.0;
        Double totalMeasurementError = 0.0;
      
        for (NozzleMeasure nozzleMeasure : nozzleMeasures) {
            if (nozzleMeasure.getTankId().equals(tankId)
                    && nozzleMeasure.getGunId().equals(gunId)
                    && (nozzleMeasure.getDateTime().isEqual(startTime)
                    || nozzleMeasure.getDateTime().isEqual(endTime)
                    || (nozzleMeasure.getDateTime().isAfter(startTime) && nozzleMeasure.getDateTime().isBefore(endTime)))) {

                NozzleMeasure modifiedNozzleMeasure = nozzleMeasure.copy();
                Double measurementError = calculateNozzleMeasurementError(previousTotalCounter, nozzleMeasure.getTotalCounter(), miscalibrationCoefficientPerOneCubicMeter);
                modifiedNozzleMeasure.setTotalCounter(nozzleMeasure.getTotalCounter() + measurementError);
                modifiedNozzleMeasures.add(modifiedNozzleMeasure);
                
                totalMeasurementError += measurementError;
                previousTotalCounter = nozzleMeasure.getTotalCounter();

            } else if (nozzleMeasure.getTankId().equals(tankId) 
                    && nozzleMeasure.getGunId().equals(gunId)
                    && nozzleMeasure.getDateTime().isAfter(endTime)) {
                NozzleMeasure modifiedNozzleMeasure = nozzleMeasure.copy();
                modifiedNozzleMeasure.setTotalCounter(nozzleMeasure.getTotalCounter() + totalMeasurementError);
                modifiedNozzleMeasures.add(modifiedNozzleMeasure);
            } else {
                modifiedNozzleMeasures.add(nozzleMeasure);
                previousTotalCounter = nozzleMeasure.getTotalCounter();
            }

        }
        return modifiedNozzleMeasures;
    }
    
    private static Double calculateNozzleMeasurementError(Double totalCounter, 
            Double nextTotalCounter, Double miscalibrationCoefficientPerOneCubicMeter){
        if(totalCounter == null || nextTotalCounter == null || miscalibrationCoefficientPerOneCubicMeter == null)
            return 0.0;
        Double totalCounterDifference = nextTotalCounter - totalCounter;      
        return totalCounterDifference < 0 ? 0.0 : totalCounterDifference * miscalibrationCoefficientPerOneCubicMeter;
        
        
        
        
    }

}
