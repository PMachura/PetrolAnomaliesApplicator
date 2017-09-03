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
    private Integer gunId;
    private Double leakageVolumePerCubicMeter;

    public PipelineLeakageConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime,Integer tankId, Integer gunId, Double leakageVolumePerCubicMeter) {
        super(startDateTime, endDateTime, tankId);
        this.anomalyTpe = AnomalyType.PIPELINE_LEAKAGE;
        this.gunId = gunId;
        this.leakageVolumePerCubicMeter = leakageVolumePerCubicMeter;
    }

    public Integer getGunId() {
        return gunId;
    }

    public void setGunId(Integer gunId) {
        this.gunId = gunId;
    }

    public Double getLeakageVolumePerCubicMeter() {
        return leakageVolumePerCubicMeter;
    }

    public void setLeakageVolumePerCubicMeter(Double leakageVolumePerCubicMeter) {
        this.leakageVolumePerCubicMeter = leakageVolumePerCubicMeter;
    }

      
}
