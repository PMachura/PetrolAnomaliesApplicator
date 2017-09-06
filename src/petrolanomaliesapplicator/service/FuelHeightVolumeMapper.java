/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.service;

import java.util.Hashtable;
import java.util.Set;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

/**
 *
 * @author Przemek
 */
public class FuelHeightVolumeMapper {

    private static final Integer keyKode = 0;
    private static final Integer valueKode = 1;
    private Double[] maxValuesForMappers;
    private Hashtable<Double, Double> mapper;
    private Hashtable<Double, Double> normalizedMapper;
    private MultiLayerPerceptron neuralNetwork;

    public FuelHeightVolumeMapper(String neuralNetowrkFileName,  Hashtable<Double, Double> mappers) {
        this(mappers);
        loadNeuralNetworks(neuralNetowrkFileName);
        normalizeMapper();
    }

    public FuelHeightVolumeMapper(Hashtable<Double, Double> mapper) {
        this.mapper = mapper;
        maxValuesForMappers = new Double[2];
        normalizedMapper = new Hashtable<Double, Double>();
        // initializeNeuralNetworks(0.0001, 100000, 0.5);
    }

    public void initializeNeuralNetwork(Double maxError, Integer maxIterations, Double learningRate) {

        normalizeMapper();

        DataSet trainingSet = new DataSet(1, 1);
        Set<Double> keys = normalizedMapper.keySet();
        keys.forEach((Double key) -> {
            trainingSet.addRow(new DataSetRow(new double[]{key}, new double[]{normalizedMapper.get(key)}));
        });

        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 1, 3, 3, 3, 1);
        multiLayerPerceptron.getLearningRule().setMaxError(maxError);
        multiLayerPerceptron.getLearningRule().setMaxIterations(maxIterations);
        multiLayerPerceptron.getLearningRule().setLearningRate(learningRate);

        multiLayerPerceptron.learn(trainingSet);

        neuralNetwork = multiLayerPerceptron;

    }

    public Double calculate(Double value) {
        if (value.equals(new Double(0))) {
            return new Double(0);
        }       
        Double normalizedValue = this.normalize(value, maxValuesForMappers[keyKode]);
        neuralNetwork.setInput(new double[]{normalizedValue});
        neuralNetwork.calculate();
        double[] outputs = denormalizeValues(neuralNetwork.getOutput());
        return outputs[0];
    }

    public void saveNeuralNetworks(String fileName) {
        neuralNetwork.save(fileName);
    }

    public void loadNeuralNetworks(String fileName) {
        neuralNetwork = (MultiLayerPerceptron) NeuralNetwork.createFromFile(fileName);
    }

    private void calculateMaxKeyValue() {
        Double maxKey = null;
        Double maxValue = null;

        Set<Double> keys = mapper.keySet();
        for (Double key : keys) {
            if (maxKey == null) {
                maxKey = key;
            } else if (key > maxKey) {
                maxKey = key;
            }

            if (maxValue == null) {
                maxValue = mapper.get(key);
            } else if (mapper.get(key) > maxValue) {
                maxValue = mapper.get(key);
            }
        }

        maxValuesForMappers = new Double[]{maxKey, maxValue};
    }

    private Double normalize(Double value, Double maxValue) {
        return value / maxValue * 0.8;
    }

    private Double denormalize(Double value, Double maxValue) {
        return value * maxValue / 0.8;
    }

    public double[] denormalizeValues(double[] values) {

        double[] denormalizedValues = new double[values.length];
        Double maxValue = maxValuesForMappers[valueKode];

        for (int i = 0; i < values.length; i++) {
            denormalizedValues[i] = denormalize(values[i], maxValue);
        }

        return denormalizedValues;
    }

    public double denormalizeValue(Integer tankId, double value) {

        Double maxValue = maxValuesForMappers[valueKode];
        return denormalize(value, maxValue);

    }

    public double denormalizeKey(double key) {

        Double maxValue = maxValuesForMappers[keyKode];
        return denormalize(key, maxValue);

    }

    public double[] denormalizeKeys(double[] keys) {

        double[] denormalizedKeys = new double[keys.length];
        Double maxValue = maxValuesForMappers[keyKode];

        for (int i = 0; i < keys.length; i++) {
            denormalizedKeys[i] = denormalize(keys[i], maxValue);
        }

        return denormalizedKeys;
    }

    public Hashtable<Double, Double> normalizeMapper() {

        this.calculateMaxKeyValue();

        Hashtable<Double, Double> normalizedHightVolumeMapper = new Hashtable<Double, Double>();

        Double maxKey = maxValuesForMappers[keyKode];
        Double maxValue = maxValuesForMappers[valueKode];

        Set<Double> keys = mapper.keySet();
        for (Double key : keys) {
            normalizedHightVolumeMapper.put(normalize(key, maxKey), normalize(mapper.get(key), maxValue));
        }

        normalizedMapper = normalizedHightVolumeMapper;

        return normalizedMapper;
    }

    public Double[] getMaxValuesForMappers() {
        return maxValuesForMappers;
    }

    public void setMaxValuesForMappers(Double[] maxValuesForMappers) {
        this.maxValuesForMappers = maxValuesForMappers;
    }

    public Hashtable<Double, Double> getMapper() {
        return mapper;
    }

    public void setMapper(Hashtable<Double, Double> mapper) {
        this.mapper = mapper;
    }

    public Hashtable<Double, Double> getNormalizedMapper() {
        return normalizedMapper;
    }

    public void setNormalizedMapper(Hashtable<Double, Double> normalizedMapper) {
        this.normalizedMapper = normalizedMapper;
    }

    public MultiLayerPerceptron getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(MultiLayerPerceptron neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

}
