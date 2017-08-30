/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;
import petrolanomaliesapplicator.fileshandlers.FileHandler;

/**
 *
 * @author Przemek
 */
public class FuelHeightVolumeMapperFactory {

    private static final String firstMapFile = "dane/mapowanie/Tank1_10012.csv";
    private static final String secondMapFile = "dane/mapowanie/Tank2_20000.csv";
    private static final String thirdMapFile = "dane/mapowanie/Tank3_30000.csv";
    private static final String fourthMapFile = "dane/mapowanie/Tank4_40000.csv";

    private static final String HeightToVolumeNetworkFileName = "HeightToVolumeNetwork";
    private static final String VolumeToHeightNetworkFileName = "VolumeToHeightNetwork";

    private static Set<Integer> allowedMappersIds = new HashSet<Integer>(Arrays.asList(1,2,3,4));
    private static Hashtable<Integer, FuelHeightVolumeMapper> heightToVolumeMappers = new Hashtable<Integer, FuelHeightVolumeMapper>();
    private static Hashtable<Integer, FuelHeightVolumeMapper> volumeToHeightMappers = new Hashtable<Integer, FuelHeightVolumeMapper>();

    public static FuelHeightVolumeMapper getHeightToVolumeMapper(Integer id) {
        if (!heightToVolumeMappers.containsKey(id)) {
            FuelHeightVolumeMapper mapper = new FuelHeightVolumeMapper(FileHandler.loadHeightToVolumeMapper(getMapDataFileName(id)));
            heightToVolumeMappers.put(id, mapper);
            try {
                System.out.println("TRY");
                mapper.setNeuralNetwork(loadNeuralNetwork(HeightToVolumeNetworkFileName + id.toString()));
                mapper.normalizeMapper();

            } catch (Exception e) {
                System.out.println("CATCH");
                mapper.initializeNeuralNetwork(0.0001, 100000, 0.5);
                saveNeuralNetwork(HeightToVolumeNetworkFileName + id.toString(), mapper.getNeuralNetwork());

            }
            return mapper;
        }
        return heightToVolumeMappers.get(id);
    }
    
    public static Hashtable<Integer, FuelHeightVolumeMapper> getAllHeightToVolumeMappers(){
        Hashtable<Integer, FuelHeightVolumeMapper> mappers = new Hashtable<Integer, FuelHeightVolumeMapper>();
        for(Integer id : allowedMappersIds){
            mappers.put(id, getHeightToVolumeMapper(id));
        }
        return mappers;
    }

    public static FuelHeightVolumeMapper getVolumeToHeightMapper(Integer id) {
        if (!volumeToHeightMappers.containsKey(id)) {
            FuelHeightVolumeMapper mapper = new FuelHeightVolumeMapper(FileHandler.loadVolumeToHeightMapper(getMapDataFileName(id)));
            volumeToHeightMappers.put(id, mapper);
            try {
                System.out.println("TRY");
                mapper.setNeuralNetwork(loadNeuralNetwork(VolumeToHeightNetworkFileName + id.toString()));
                mapper.normalizeMapper();

            } catch (Exception e) {
                System.out.println("CATCH");
                mapper.initializeNeuralNetwork(0.0001, 100000, 0.5);
                saveNeuralNetwork(VolumeToHeightNetworkFileName + id.toString(), mapper.getNeuralNetwork());

            }
            return mapper;
        }
        return volumeToHeightMappers.get(id);
    }
    
    public static Hashtable<Integer, FuelHeightVolumeMapper> getAllVolumeToHeightMappers(){
        Hashtable<Integer, FuelHeightVolumeMapper> mappers = new Hashtable<Integer, FuelHeightVolumeMapper>();
        for(Integer id : allowedMappersIds){
            mappers.put(id, getVolumeToHeightMapper(id));
        }
        return mappers;
    }

    private static MultiLayerPerceptron loadNeuralNetwork(String fileName) {
        return (MultiLayerPerceptron) NeuralNetwork.createFromFile(fileName);
    }

    private static void saveNeuralNetwork(String fileName, MultiLayerPerceptron neuralNetwork) {
            neuralNetwork.save(fileName);   
    }

    private static String getMapDataFileName(Integer tankId) {
        switch (tankId) {
            case 1:
                return firstMapFile;
            case 2:
                return secondMapFile;
            case 3:
                return thirdMapFile;
            case 4:
                return fourthMapFile;
            default:
                return null;
        }
    }

}
