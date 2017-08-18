/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

import java.util.Hashtable;

/**
 *
 * @author Przemek
 */
public class HeightVolumeMapper {
    Hashtable<Integer, Hashtable<Double, Double>> mappers;

    public HeightVolumeMapper(Hashtable<Integer, Hashtable<Double, Double>> mappers) {
        this.mappers = mappers;
    }
    
    
}
