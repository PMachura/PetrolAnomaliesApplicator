/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.io.File;
import java.io.FileInputStream;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.IterativeLearning;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;
import petrolanomaliesapplicator.anomaliesapplicators.LeakageApplicator;
import petrolanomaliesapplicator.anomaliesapplicators.ProbeHangApplicator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfiguratorCollector;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyType;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ProbeHangConfigurator;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.RefuelMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class Test {

  
    
    public static void main(String[] args) throws FileNotFoundException, ParseException, IOException {

        ArrayList<TankMeasure> tankMeasures = (ArrayList<TankMeasure>) FileHandler.loadTankMeasures("dane/Zestaw 1/tankMeasures.log");
        ArrayList<NozzleMeasure> nozzleMeasures = (ArrayList<NozzleMeasure>) FileHandler.loadNozzleMeasures("dane/Zestaw 1/nozzleMeasures.log");
        ArrayList<RefuelMeasure> refuelMeasures = (ArrayList<RefuelMeasure>) FileHandler.loadRefuelMeasures("dane/Zestaw 1/refuel.log");
        FuelHeightVolumeMapper hightVolumeMapper = new FuelHeightVolumeMapper(FileHandler.loadHightVolumeMappers());
        
        
        
        
        Hashtable<Double, Double> firstTankHightVolumeMapper = hightVolumeMapper.getNormalizedMapperForTank(1);

        DataSet trainingSet = new DataSet(1, 1);
        Set<Double> keys = firstTankHightVolumeMapper.keySet();
        keys.forEach((Double key) -> {
            trainingSet.addRow(new DataSetRow(new double[]{key}, new double[]{firstTankHightVolumeMapper.get(key)}));
        });
        

        for (DataSetRow dataRow : trainingSet.getRows()) {
              System.out.println("Input " + Arrays.toString(dataRow.getInput()) + " Output " + Arrays.toString(dataRow.getDesiredOutput()));
        }

        System.out.println("Hello");  
        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, 1,3,3,3, 1);
        multiLayerPerceptron.getLearningRule().setMaxError(0.0001);
        multiLayerPerceptron.getLearningRule().setMaxIterations(10000);
        multiLayerPerceptron.getLearningRule().setLearningRate(0.7);
        
        System.out.println("Hello");
        multiLayerPerceptron.learn(trainingSet);
        
        System.out.println("Hello");
        for (DataSetRow dataRow : trainingSet.getRows()) {
            multiLayerPerceptron.setInput(dataRow.getInput());
            multiLayerPerceptron.calculate();
            double[] output = multiLayerPerceptron.getOutput().clone();
            System.out.print("Input " + Arrays.toString(hightVolumeMapper.denormalizeKeys(1,dataRow.getInput())) + " ");
            System.out.print("Desired Output " + Arrays.toString(hightVolumeMapper.denormalizeValues(1, dataRow.getDesiredOutput())) + " ");
            System.out.println("Output " + Arrays.toString(hightVolumeMapper.denormalizeValues(1,output)));
        }

    }

}
