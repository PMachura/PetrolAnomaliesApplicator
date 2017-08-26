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
public class ProbeHangConfigurator extends AnomalyConfigurator {
    private Integer tankId;

    public ProbeHangConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, AnomalyType anomalyTpe, Integer tankId) {
        super(startDateTime, endDateTime, anomalyTpe);
        this.tankId = tankId;
    }

    
    
    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }

    @Override
    public String toString() {
        return super.toString() + "ProbeHangConfigurator{" + "tankId=" + tankId + '}';
    }
    
    
    
    
}
