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
public class RefuelMeasure {
    LocalDateTime dateTime;
    Integer tankId;
    Double cisternPetrolVolume;
    Double refuelingSpeed;

    public RefuelMeasure(LocalDateTime dateTime, Integer tankId, Double cisternPetrolVolume, Double refuelingSpeed) {
        this.dateTime = dateTime;
        this.tankId = tankId;
        this.cisternPetrolVolume = cisternPetrolVolume;
        this.refuelingSpeed = refuelingSpeed;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }

    public Double getCisternPetrolVolume() {
        return cisternPetrolVolume;
    }

    public void setCisternPetrolVolume(Double cisternPetrolVolume) {
        this.cisternPetrolVolume = cisternPetrolVolume;
    }

    public Double getRefuelingSpeed() {
        return refuelingSpeed;
    }

    public void setRefuelingSpeed(Double refuelingSpeed) {
        this.refuelingSpeed = refuelingSpeed;
    }

    @Override
    public String toString() {
        return "RefuelMeasure{" + "dateTime=" + dateTime + ", tankId=" + tankId + ", cisternPetrolVolume=" + cisternPetrolVolume + ", refuelingSpeed=" + refuelingSpeed + '}';
    }
    
    
    
    
}
