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
public class ProbeHangConfigurator extends AnomalyConfigurator {
    private Integer tankId;

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }
    
    
}
