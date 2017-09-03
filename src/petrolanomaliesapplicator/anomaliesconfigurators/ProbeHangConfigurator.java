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


    public ProbeHangConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, Integer tankId) {
        super(startDateTime, endDateTime, tankId);
        this.anomalyTpe = AnomalyType.PROBE_HANG;
    }

    @Override
    public String toString() {
        return super.toString() + "ProbeHangConfigurator{" + "tankId=" + tankId + '}';
    }
    
    
    
    
}
