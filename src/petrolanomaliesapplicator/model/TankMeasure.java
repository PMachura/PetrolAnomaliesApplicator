/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Przemek
 */
public class TankMeasure {

    LocalDateTime dateTime;
    Integer locationId;
    Integer meterId;
    Integer tankId;
    Double fuelHeight;
    Double fuelVolume;
    Double fuelTemperature;
    Double waterHeight;
    Double waterVolume;

    public TankMeasure() {
    }

    ;

    public TankMeasure(LocalDateTime dateTime, Integer locationId, Integer meterId, Integer tankId, Double fuelHeight, Double fuelVolume, Double fuelTemperature, Double waterHeight, Double waterVolume) {
        this.dateTime = dateTime;
        this.locationId = locationId;
        this.meterId = meterId;
        this.tankId = tankId;
        this.fuelHeight = fuelHeight;
        this.fuelVolume = fuelVolume;
        this.fuelTemperature = fuelTemperature;
        this.waterHeight = waterHeight;
        this.waterVolume = waterVolume;
    }

    public TankMeasure clone() {
        TankMeasure tankMeasureCopy = new TankMeasure();
        tankMeasureCopy.setDateTime(dateTime);
        tankMeasureCopy.setFuelHeight(fuelHeight);
        tankMeasureCopy.setFuelTemperature(fuelTemperature);
        tankMeasureCopy.setFuelVolume(fuelVolume);
        tankMeasureCopy.setLocationId(locationId);
        tankMeasureCopy.setMeterId(meterId);
        tankMeasureCopy.setTankId(tankId);
        tankMeasureCopy.setWaterHeight(waterHeight);
        tankMeasureCopy.setWaterVolume(waterVolume);

        return tankMeasureCopy;

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

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }

    public Integer getTankId() {
        return tankId;
    }

    public void setTankId(Integer tankId) {
        this.tankId = tankId;
    }

    public Double getFuelHeight() {
        return fuelHeight;
    }

    public void setFuelHeight(Double fuelHeight) {
        this.fuelHeight = fuelHeight;
    }

    public Double getFuelVolume() {
        return fuelVolume;
    }

    public void setFuelVolume(Double fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public Double getFuelTemperature() {
        return fuelTemperature;
    }

    public void setFuelTemperature(Double fuelTemperature) {
        this.fuelTemperature = fuelTemperature;
    }

    public Double getWaterHeight() {
        return waterHeight;
    }

    public void setWaterHeight(Double waterHeight) {
        this.waterHeight = waterHeight;
    }

    public Double getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(Double waterVolume) {
        this.waterVolume = waterVolume;
    }

    @Override
    public String toString() {
        return "TankMeasure{" + "dateTime=" + dateTime + ", locationId=" + locationId + ", meterId=" + meterId + ", tankId=" + tankId + ", fuelHeight=" + fuelHeight + ", fuelVolume=" + fuelVolume + ", fuelTemperature=" + fuelTemperature + ", waterHeight=" + waterHeight + ", waterVolume=" + waterVolume + '}';
    }

}
