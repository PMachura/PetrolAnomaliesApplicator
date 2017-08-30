/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import petrolanomaliesapplicator.fileshandlers.FileHandler;

import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapperFactory;

/**
 *
 * @author Przemek
 */
public class NeuralNetworkTest {
    
    public static void createMappersFromFactory(){
        FuelHeightVolumeMapper heightToVolumeMapper = FuelHeightVolumeMapperFactory.getHeightToVolumeMapper(1);
        //FuelHeightVolumeMapper sameMapper = FuelHeightVolumeMapperFactory.getHeightToVolumeMapper(1);
        FuelHeightVolumeMapper volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(1);
        
        for(int i=0; i<=1500; i+=10){
      //   System.out.println("Input " + i + " Output " + heightToVolumeMapper.calculate((double)i)); 
        }
        
        for(int i=10012; i >= 0; i-=10){
         System.out.println("Input " + i + " Output " + volumeToHeightMapper.calculate((double)i)); 
        }
    }
    
    public static void main(String [] args){
        
//        FuelHeightVolumeMapper fuelHeightVolumeMapper = new FuelHeightVolumeMapper(FileHandler.loadVolumeToHeightMapper("dane/mapowanie/Tank1_10012.csv"));
//        fuelHeightVolumeMapper.initializeNeuralNetwork(0.0001, 100000, 0.3);
//       // fuelHeightVolumeMapper.saveNeuralNetworks("neuralNetwork");
//       // fuelHeightVolumeMapper.loadNeuralNetworks("neuralNetwork");
//        
//        for(int i=0; i<=1500; i+=10){
//        //  System.out.println("Input " + i + " Output " + fuelHeightVolumeMapper.calculate((double)i)); 
//        }
//        
//        for(int i=10012; i >= 0; i-=10){
//         System.out.println("Input " + i + " Output " + fuelHeightVolumeMapper.calculate((double)i)); 
//        }
        
         createMappersFromFactory();
    }
}
