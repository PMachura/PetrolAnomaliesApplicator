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
public class ConstantLeakageConfigurator extends AnomalyConfigurator {
    private Integer tankId;
    private Double leakageVolumePerHour;

    public ConstantLeakageConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, AnomalyType anomalyTpe, Integer tankId, Double leakageVolumePerHour) {
        super(startDateTime, endDateTime, anomalyTpe);
        this.tankId = tankId;
        this.leakageVolumePerHour = leakageVolumePerHour;
    }

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }

    public Double getLeakageVolumePerHour() {
        return leakageVolumePerHour;
    }

    public void setLeakageVolumePerHour(Double leakageVolumePerHour) {
        this.leakageVolumePerHour = leakageVolumePerHour;
    }

    @Override
    public String toString() {
        return super.toString() + "ConstantLeakageConfigurator{" + "tankId=" + tankId + ", leakageVolumePerHour=" + leakageVolumePerHour + '}';
    }
    
    
    
}
