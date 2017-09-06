/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.factory;

import java.util.ArrayList;
import java.util.Hashtable;
import petrolanomaliesapplicator.fileshandlers.FileHandler;
import petrolanomaliesapplicator.model.DataSetCollection;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.RefuelMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
//public class DataSetFactory {
//
//    private Hashtable<DataSetLocation, DataSetCollection> dataSets;
//
//    public DataSetFactory() {
//        dataSets = new Hashtable<DataSetLocation, DataSetCollection>();
//        dataSets.put(DataSetLocation.ZESTAW1, FileHandler.loadDataSetCollection(DataSetLocation.ZESTAW1));
//        dataSets.put(DataSetLocation.ZESTAW2, FileHandler.loadDataSetCollection(DataSetLocation.ZESTAW2));
//        dataSets.put(DataSetLocation.ZESTAW3, FileHandler.loadDataSetCollection(DataSetLocation.ZESTAW3));
//    }
//
//    public DataSetCollection getDataSet(DataSetLocation dataSetLocation) {
//        if (!dataSets.contains(dataSetLocation)) {
//            dataSets.put(dataSetLocation, FileHandler.loadDataSetCollection(dataSetLocation));
//        }
//        return dataSets.get(dataSetLocation);
//    }
//
//    public ArrayList<TankMeasure> getTankMeasures(DataSetLocation dataSetLocation) {
//        return this.getDataSet(dataSetLocation).getTankMeasures();
//    }
//
//    public ArrayList<NozzleMeasure> getNozzleMeasures(DataSetLocation dataSetLocation) {
//        return this.getDataSet(dataSetLocation).getNozzleMeasures();
//    }
//
//    public ArrayList<RefuelMeasure> gerRefuelMeasures(DataSetLocation dataSetLocation) {
//        return this.getDataSet(dataSetLocation).getRefuelMeasures();
//    }
//}
public class DataSetFactory {

    private static Hashtable<String, DataSetCollection> dataSets = new Hashtable<String, DataSetCollection>();

    public  DataSetFactory() {
        this.dataSets = new Hashtable<String, DataSetCollection>();
    }

    public static DataSetCollection getDataSet(String dataSetFolder) {
        if (!dataSets.contains(dataSetFolder)) {
            dataSets.put(dataSetFolder, FileHandler.loadDataSetCollection(dataSetFolder));
        }
        return dataSets.get(dataSetFolder);
    }

    public static ArrayList<TankMeasure> getTankMeasures(String dataSetFolder) {
        return getDataSet(dataSetFolder) != null ? getDataSet(dataSetFolder).getTankMeasures() : null;
    }
    public static ArrayList<NozzleMeasure> getNozzleMeasures(String dataSetFolder) {
        return getDataSet(dataSetFolder) != null ? getDataSet(dataSetFolder).getNozzleMeasures(): null;
    }
    public static ArrayList<RefuelMeasure> getRefuelMeasures(String dataSetFolder) {
        return getDataSet(dataSetFolder) != null ? getDataSet(dataSetFolder).getRefuelMeasures(): null;
    }
}
