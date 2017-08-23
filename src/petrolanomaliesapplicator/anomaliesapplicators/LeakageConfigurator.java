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
public class LeakageConfigurator extends AnomalyConfigurator {
    private Double leakageVolumePerHour;
    private Double leakingPointHeight;
    private Integer tankId;

    public Double getLeakageVolumePerHour() {
        return leakageVolumePerHour;
    }

    public void setLeakageVolumePerHour(Double leakageVolumePerHour) {
        this.leakageVolumePerHour = leakageVolumePerHour;
    }

    public Double getLeakingPointHeight() {
        return leakingPointHeight;
    }

    public void setLeakingPointHeight(Double leakingPointHeight) {
        this.leakingPointHeight = leakingPointHeight;
    }

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }
    
    
}
