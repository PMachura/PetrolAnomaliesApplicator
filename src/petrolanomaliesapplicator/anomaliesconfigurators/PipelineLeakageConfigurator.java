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
public class PipelineLeakageConfigurator extends AnomalyConfigurator {
    private Double leakageVolumePerHour;
    private Double leakingPointHeight;
    private Integer tankId;

    public PipelineLeakageConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime,  Double leakageVolumePerHour, Double leakingPointHeight, Integer tankId) {
        super(startDateTime, endDateTime);
        this.anomalyTpe = AnomalyType.PIPELINE_LEAKAGE;
        this.leakageVolumePerHour = leakageVolumePerHour;
        this.leakingPointHeight = leakingPointHeight;
        this.tankId = tankId;
    }

    
    
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
