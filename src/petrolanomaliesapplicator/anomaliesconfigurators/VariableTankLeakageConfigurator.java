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
public class VariableTankLeakageConfigurator extends AnomalyConfigurator{

    private Double leakingPointHeight;

    public VariableTankLeakageConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime,Integer tankId, Double leakingPointHeight) {
        super(startDateTime, endDateTime, tankId);
        this.anomalyTpe = AnomalyType.VARIABLE_LEAKAGE;
        this.leakingPointHeight = leakingPointHeight;
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
