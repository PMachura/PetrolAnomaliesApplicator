/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

import java.util.Hashtable;
import java.util.Set;
import org.neuroph.core.data.DataSetRow;

/**
 *
 * @author Przemek
 */
public class HeightVolumeMapper {

    private static Integer keyKode = 0;
    private static Integer valueKode = 1;
    private Hashtable<Integer, Double[]> minMaxValues;
    private Hashtable<Integer, Hashtable<Double, Double>> mappers;
    private Hashtable<Integer, Hashtable<Double, Double>> normalizedMappers;

    public HeightVolumeMapper(Hashtable<Integer, Hashtable<Double, Double>> mappers) {
        this.mappers = mappers;
        minMaxValues = new Hashtable<Integer, Double[]>();
        normalizedMappers = new Hashtable<Integer, Hashtable<Double, Double>>();
    }

    public Hashtable<Double, Double> getMapperForTank(Integer tankId) {
        return mappers.get(tankId);
    }

    public Hashtable<Double, Double> getNormalizedMapperForTank(Integer tankId) {
        if (!normalizedMappers.containsKey(tankId)) {
            normalizeMapper(tankId);
        }
        return normalizedMappers.get(tankId);
    }

    private void calculateMinMaxKeyValue(Integer tankId) {
        Double maxKey = null;
        Double maxValue = null;
        Hashtable<Double, Double> hightVolumeMapper = getMapperForTank(tankId);
        Set<Double> keys = hightVolumeMapper.keySet();
        for (Double key : keys) {
            if (maxKey == null) {
                maxKey = key;
            } else if (key > maxKey) {
                maxKey = key;
            }

            if (maxValue == null) {
                maxValue = hightVolumeMapper.get(key);
            } else if (hightVolumeMapper.get(key) > maxValue) {
                maxValue = hightVolumeMapper.get(key);
            }
        }

        Double[] minMaxKayAndValue = new Double[]{maxKey, maxValue};
        minMaxValues.put(tankId, minMaxKayAndValue);
    }

    private Double normalize(Double value, Double maxValue) {
        return value / maxValue * 0.8;
    }

    private Double denormalize(Double value, Double maxValue) {
        return value * maxValue / 0.8;
    }

    public double[] denormalizeValues(Integer tankId, double[] values) {

        double[] denormalizedValues = new double[values.length];
        Double maxValue = minMaxValues.get(tankId)[valueKode];

        for (int i = 0; i < values.length; i++) {
            denormalizedValues[i] = denormalize(values[i], maxValue);
        }

        return denormalizedValues;
    }

    public double denormalizeValue(Integer tankId, double value) {

        Double maxValue = minMaxValues.get(tankId)[valueKode];
        return denormalize(value, maxValue);

    }
    
    public double denormalizeKey(Integer tankId, double key) {

        Double maxValue = minMaxValues.get(tankId)[keyKode];
        return denormalize(key, maxValue);

    }
    
    public double[] denormalizeKeys(Integer tankId, double[] keys) {

        double[] denormalizedValues = new double[keys.length];
        Double maxValue = minMaxValues.get(tankId)[keyKode];

        for (int i = 0; i < keys.length; i++) {
            denormalizedValues[i] = denormalize(keys[i], maxValue);
        }

        return denormalizedValues;
    }

    public Hashtable<Double, Double> normalizeMapper(Integer tankId) {

        if (normalizedMappers.containsKey(tankId)) {
            return normalizedMappers.get(tankId);
        }

        this.calculateMinMaxKeyValue(tankId);

        Hashtable<Double, Double> hightVolumeMapper = getMapperForTank(tankId);
        Hashtable<Double, Double> normalizedHightVolumeMapper = new Hashtable<Double, Double>();

        Double maxKey = minMaxValues.get(tankId)[keyKode];
        Double maxValue = minMaxValues.get(tankId)[valueKode];

        Set<Double> keys = hightVolumeMapper.keySet();
        for (Double key : keys) {
            normalizedHightVolumeMapper.put(normalize(key, maxKey), normalize(hightVolumeMapper.get(key), maxValue));
        }

        normalizedMappers.put(tankId, normalizedHightVolumeMapper);

        return normalizedMappers.get(tankId);
    }

}
