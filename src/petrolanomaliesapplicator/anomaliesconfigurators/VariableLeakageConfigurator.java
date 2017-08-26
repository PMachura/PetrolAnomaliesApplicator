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
public class VariableLeakageConfigurator extends AnomalyConfigurator{
    private Integer tankId;
    private Double leakingPointHeight;

    public VariableLeakageConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, AnomalyType anomalyTpe,Integer tankId, Double leakingPointHeight) {
        super(startDateTime, endDateTime, anomalyTpe);
        this.tankId = tankId;
        this.leakingPointHeight = leakingPointHeight;
    }

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }

    public Double getLeakingPointHeight() {
        return leakingPointHeight;
    }

    public void setLeakingPointHeight(Double leakingPointHeight) {
        this.leakingPointHeight = leakingPointHeight;
    }

    @Override
    public String toString() {
        return super.toString() + "VariableLeakageConfigurator{" + "tankId=" + tankId + ", leakingPointHeight=" + leakingPointHeight + '}';
    }
    
    
    
    
}
