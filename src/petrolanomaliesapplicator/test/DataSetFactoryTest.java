/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.model.DataSetCollection;
import petrolanomaliesapplicator.factory.DataSetFactory;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class DataSetFactoryTest {
    
    public static void test(){
        DataSetCollection dataSetCollection = DataSetFactory.getDataSet(BaseDataSetLocation.ZESTAW1.getFolderName());
        for(TankMeasure tankMeasure : dataSetCollection.getTankMeasures()){
       //     System.out.println(tankMeasure);
        }
        FileHandler.saveDataSetCollection("Zestaw 20", dataSetCollection);
    }
    
    public static void main(String [] args){
        test();
    }
}
