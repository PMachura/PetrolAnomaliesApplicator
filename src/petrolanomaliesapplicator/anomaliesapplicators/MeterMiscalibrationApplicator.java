/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.service.TimeCalculator;
import petrolanomaliesapplicator.model.NozzleMeasure;

/**
 *
 * @author Przemek
 */
public class MeterMiscalibrationApplicator extends AnomalyApplicator {

    private Double miscalibrationCoefficientPerOneCubicMeter;
    private Double previousTotalCounter = null;
    private Double totalMeasurementError = 0.0;
    private Integer gunId;

    public MeterMiscalibrationApplicator(MeterMiscalibrationConfigurator configurator) {
        super(configurator);
        this.miscalibrationCoefficientPerOneCubicMeter = configurator.getMiscalibrationCoefficientPerOneCubicMeter();
        this.previousTotalCounter = null;
        this.totalMeasurementError = new Double(0);
        this.gunId = configurator.getGunId();
    }

    public static Collection<NozzleMeasure> applyMeterMiscalibration(Collection<NozzleMeasure> nozzleMeasures,
            Integer tankId, Integer gunId, LocalDateTime startTime, LocalDateTime endTime, Double miscalibrationCoefficientPerOneCubicMeter) {

        Collection<NozzleMeasure> modifiedNozzleMeasures = new ArrayList<NozzleMeasure>();
        Double previousTotalCounter = null;
        Double totalMeasurementError = 0.0;

        for (NozzleMeasure nozzleMeasure : nozzleMeasures) {
            if (nozzleMeasure.getTankId().equals(tankId)
                    && nozzleMeasure.getGunId().equals(gunId)
                    && (TimeCalculator.isDateInRange(startTime, endTime, nozzleMeasure.getDateTime()))) {

                NozzleMeasure modifiedNozzleMeasure = nozzleMeasure.copy();

                Double measurementErrorForTotalCounter = calculateNozzleMeasurementError(previousTotalCounter, nozzleMeasure.getTotalCounter(), miscalibrationCoefficientPerOneCubicMeter);
                modifiedNozzleMeasure.verifySetTotalCounter(nozzleMeasure.getTotalCounter() + measurementErrorForTotalCounter + totalMeasurementError);

                Double measurementErrorForLiterCounter = calculateNozzleMeasurementError(0.0, nozzleMeasure.getLiterCounter(), miscalibrationCoefficientPerOneCubicMeter);
                modifiedNozzleMeasure.verifySetLiterCounter(nozzleMeasure.getLiterCounter() + measurementErrorForLiterCounter);

                modifiedNozzleMeasures.add(modifiedNozzleMeasure);

                totalMeasurementError += measurementErrorForTotalCounter;
                previousTotalCounter = nozzleMeasure.getTotalCounter();

            } else if (nozzleMeasure.getTankId().equals(tankId)
                    && nozzleMeasure.getGunId().equals(gunId)
                    && nozzleMeasure.getDateTime().isAfter(endTime)) {
                NozzleMeasure modifiedNozzleMeasure = nozzleMeasure.copy();
                modifiedNozzleMeasure.verifySetTotalCounter(nozzleMeasure.getTotalCounter() + totalMeasurementError);
                modifiedNozzleMeasures.add(modifiedNozzleMeasure);
            } else if (nozzleMeasure.getTankId().equals(tankId)
                    && nozzleMeasure.getGunId().equals(gunId)) {
                modifiedNozzleMeasures.add(nozzleMeasure);
                previousTotalCounter = nozzleMeasure.getTotalCounter();
            } else {
                modifiedNozzleMeasures.add(nozzleMeasure);
            }

        }
        return modifiedNozzleMeasures;
    }

    public Collection<NozzleMeasure> applyMeterMiscalibration(Collection<NozzleMeasure> nozzleMeasures) {
        return applyMeterMiscalibration(nozzleMeasures, tankId, gunId, startTime, endTime, miscalibrationCoefficientPerOneCubicMeter);
    }

    public NozzleMeasure applyMeterMiscalibration(NozzleMeasure nozzleMeasure) {

        NozzleMeasure modifiedNozzleMeasure = nozzleMeasure.copy();

        if (nozzleMeasure.getTankId().equals(tankId)
                && nozzleMeasure.getGunId().equals(gunId)
                && TimeCalculator.isDateInRange(startTime, endTime, nozzleMeasure.getDateTime())) {

            Double measurementErrorForTotalCounter = calculateNozzleMeasurementError(previousTotalCounter, nozzleMeasure.getTotalCounter(), miscalibrationCoefficientPerOneCubicMeter);
            modifiedNozzleMeasure.verifySetTotalCounter(nozzleMeasure.getTotalCounter() + measurementErrorForTotalCounter + totalMeasurementError);

            Double measurementErrorForLiterCounter = calculateNozzleMeasurementError(0.0, nozzleMeasure.getLiterCounter(), miscalibrationCoefficientPerOneCubicMeter);
            modifiedNozzleMeasure.verifySetLiterCounter(nozzleMeasure.getLiterCounter() + measurementErrorForLiterCounter);

            totalMeasurementError += measurementErrorForTotalCounter;
            previousTotalCounter = nozzleMeasure.getTotalCounter();

        } else if (nozzleMeasure.getTankId().equals(tankId)
                && nozzleMeasure.getGunId().equals(gunId)
                && nozzleMeasure.getDateTime().isAfter(endTime)) {
            modifiedNozzleMeasure.verifySetTotalCounter(nozzleMeasure.getTotalCounter() + totalMeasurementError);
        } else if(nozzleMeasure.getTankId().equals(tankId)
                && nozzleMeasure.getGunId().equals(gunId)){
            previousTotalCounter = nozzleMeasure.getTotalCounter();
        }
        return modifiedNozzleMeasure;
    }

    private static Double calculateNozzleMeasurementError(Double totalCounter,
            Double nextTotalCounter, Double miscalibrationCoefficientPerOneCubicMeter) {

        if (totalCounter == null || nextTotalCounter == null || miscalibrationCoefficientPerOneCubicMeter == null) {
            return 0.0;
        }

        Double totalCounterDifference = nextTotalCounter - totalCounter;
        return totalCounterDifference < 0 ? 0.0 : totalCounterDifference * miscalibrationCoefficientPerOneCubicMeter;

    }

}
