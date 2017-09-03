/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesconfigurators;

import java.time.LocalDateTime;

/**
 *
 * @author Przemek
 */
public class MeterMiscalibrationConfigurator extends AnomalyConfigurator{
    

    private Integer gunId;
    private Double miscalibrationCoefficientPerOneCubicMeter;

    public MeterMiscalibrationConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer tankId, Integer gunId, Double miscalibrationCoefficientPerOneCubicMeter) {
        super(startDateTime, endDateTime,tankId);
        this.anomalyTpe = AnomalyType.METER_MISCALIBRATION;
        this.gunId = gunId;
        this.miscalibrationCoefficientPerOneCubicMeter = miscalibrationCoefficientPerOneCubicMeter;
    }


    public Integer getGunId() {
        return gunId;
    }

    public void setGunId(Integer gunId) {
        this.gunId = gunId;
    }

    public Double getMiscalibrationCoefficientPerOneCubicMeter() {
        return miscalibrationCoefficientPerOneCubicMeter;
    }

    public void setMiscalibrationCoefficientPerOneCubicMeter(Double miscalibrationCoefficientPerOneCubicMeter) {
        this.miscalibrationCoefficientPerOneCubicMeter = miscalibrationCoefficientPerOneCubicMeter;
    }

    @Override
    public String toString() {
        return super.toString() + "MeterMiscalibrationConfigurator{" + "tankId=" + tankId + ", gunId=" + gunId + ", miscalibrationCoefficientPerOneCubicMeter=" + miscalibrationCoefficientPerOneCubicMeter + '}';
    }
    
    
    
}
