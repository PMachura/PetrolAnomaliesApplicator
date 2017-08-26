/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import petrolanomaliesapplicator.fileshandlers.FileHandler;

import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;

/**
 *
 * @author Przemek
 */
public class NeuralNetworkTest {
    public static void main(String [] args){
        FuelHeightVolumeMapper fuelHeightVolumeMapper = new FuelHeightVolumeMapper(FileHandler.loadHightVolumeMappers());
        fuelHeightVolumeMapper.initializeNeuralNetworks(0.0001, 100000, 0.5);
       // fuelHeightVolumeMapper.saveNeuralNetworks("neuralNetwork");
       // fuelHeightVolumeMapper.loadNeuralNetworks("neuralNetwork");
        
        for(int i=0; i<=1500; i+=10){
          System.out.println("Input " + i + " Output " + fuelHeightVolumeMapper.calculate(2, (double)i)); 
        }
        
         
    }
}
