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
    
    private Integer tankId;
    private Integer gunId;
    private Double miscalibrationCoefficientPerOneCubicMeter;

    public MeterMiscalibrationConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, AnomalyType anomalyTpe, Integer tankiId, Integer gunId, Double miscalibrationCoefficientPerOneCubicMeter) {
        super(startDateTime, endDateTime, anomalyTpe);
        this.tankId = tankiId;
        this.gunId = gunId;
        this.miscalibrationCoefficientPerOneCubicMeter = miscalibrationCoefficientPerOneCubicMeter;
    }

    
    
    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankiId) {
        this.tankId = tankiId;
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
