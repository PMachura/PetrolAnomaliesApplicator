/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

import java.util.ArrayList;

/**
 *
 * @author Przemek
 */
public class DataSetCollection {
    ArrayList<TankMeasure> tankMeasures;
    ArrayList<NozzleMeasure> nozzleMeasures;
    ArrayList<RefuelMeasure> refuelMeasures;

    public ArrayList<TankMeasure> getTankMeasures() {
        return tankMeasures;
    }

    public void setTankMeasures(ArrayList<TankMeasure> tankMeasures) {
        this.tankMeasures = tankMeasures;
    }

    public ArrayList<NozzleMeasure> getNozzleMeasures() {
        return nozzleMeasures;
    }

    public void setNozzleMeasures(ArrayList<NozzleMeasure> nozzleMeasures) {
        this.nozzleMeasures = nozzleMeasures;
    }

    public ArrayList<RefuelMeasure> getRefuelMeasures() {
        return refuelMeasures;
    }

    public void setRefuelMeasures(ArrayList<RefuelMeasure> refuelMeasures) {
        this.refuelMeasures = refuelMeasures;
    }
    
    
    
    
}
