/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.model.DataSetCollection;

/**
 *
 * @author Przemek
 */
public abstract class AnomalyApplicator {
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Integer tankId;
    
    public AnomalyApplicator(AnomalyConfigurator anomalyConfigurator){
        this.startTime = anomalyConfigurator.getStartDateTime();
        this.endTime = anomalyConfigurator.getEndDateTime();
        this.tankId = anomalyConfigurator.getTankId();
    }
    
    
}
