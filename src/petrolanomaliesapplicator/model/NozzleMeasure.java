/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

import java.time.LocalDateTime;

/**
 *
 * @author Przemek
 */
public class NozzleMeasure {
    LocalDateTime dateTime;
    Integer locationId;
    Integer gunId;
    Integer tankId;
    Double literCounter;
    Double totalCounter;
    Integer status;

    public NozzleMeasure(LocalDateTime dateTime, Integer locationId, Integer gunId, Integer tankId, Double literCounter, Double totalCounter, Integer status) {
        this.dateTime = dateTime;
        this.locationId = locationId;
        this.gunId = gunId;
        this.tankId = tankId;
        this.literCounter = literCounter;
        this.totalCounter = totalCounter;
        this.status = status;
    }

    public NozzleMeasure copy(){
        return new NozzleMeasure(this.getDateTime(),
            this.getLocationId(),
            this.getGunId(),
            this.getTankId(),
            this.getLiterCounter(),
            this.getTotalCounter(),
            this.getStatus());
    }
    
    @Override
    public String toString() {
        return "NozzleMeasure{" + "dateTime=" + dateTime + ", locationId=" + locationId + ", gunId=" + gunId + ", tankId=" + tankId + ", literCounter=" + literCounter + ", totalCounter=" + totalCounter + ", status=" + status + '}';
    }

    
    
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getGunId() {
        return gunId;
    }

    public void setGunId(Integer gunId) {
        this.gunId = gunId;
    }

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }

    public Double getLiterCounter() {
        return literCounter;
    }

    public void verifySetLiterCounter(Double literCounter) {
        if(literCounter >=0.0){
            this.literCounter = literCounter;
        }else{
            this.literCounter = 0.0;
        }
        
    }

    public Double getTotalCounter() {
        return totalCounter;
    }

    public void verifySetTotalCounter(Double totalCounter) {
        if(totalCounter >= 0.0){
            this.totalCounter = totalCounter;
        }else{
            this.totalCounter = 0.0;
        }
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    
}
