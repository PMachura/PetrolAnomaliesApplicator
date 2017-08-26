/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesconfigurators;

import java.time.LocalDateTime;



public abstract class AnomalyConfigurator {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private AnomalyType anomalyTpe;

    public AnomalyConfigurator(LocalDateTime startDateTime, LocalDateTime endDateTime, AnomalyType anomalyTpe) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.anomalyTpe = anomalyTpe;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public AnomalyType getAnomalyTpe() {
        return anomalyTpe;
    }

    public void setAnomalyTpe(AnomalyType anomalyTpe) {
        this.anomalyTpe = anomalyTpe;
    }

    @Override
    public String toString() {
        return "AnomalyConfigurator{" + "startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", anomalyTpe=" + anomalyTpe + '}';
    }
    
    
    
    
}