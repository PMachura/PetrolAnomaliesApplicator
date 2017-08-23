/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

/**
 *
 * @author Przemek
 */
public class MeterMiscalibrationConfigurator extends AnomalyConfigurator{
    
    private Integer tankiId;
    private Integer gunId;
    private Double miscalibrationCoefficientPerOneCubicMeter;

    public Integer getTankiId() {
        return tankiId;
    }

    public void setTankiId(Integer tankiId) {
        this.tankiId = tankiId;
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
    
    
}
