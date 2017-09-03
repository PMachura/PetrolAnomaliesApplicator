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
public class ConstantTankLeakageConfigurator extends AnomalyConfigurator {

    private Double leakageVolumePerHour;

    public ConstantTankLeakageConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer tankId, Double leakageVolumePerHour) {
        super(startDateTime, endDateTime, tankId);
        this.anomalyTpe = AnomalyType.CONSTANT_LEAKAGE;
        this.leakageVolumePerHour = leakageVolumePerHour;
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
