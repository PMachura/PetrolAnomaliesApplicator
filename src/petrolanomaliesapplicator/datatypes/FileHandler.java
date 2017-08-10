/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.datatypes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Przemek
 */
public class FileHandler {

    static File file;
    static FileInputStream fileInputStream;
    static BufferedReader bufferedReader;
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
    


    public static Collection<TankMeasure> loadTankMeasures(String fileName)  {
        try {
            return TankMeasureFileHandler.loadData(fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Collection<NozzleMeasure> loadNozzleMeasures(String fileName) {
        try {
            return NozzleMeasureFileHandler.loadData(fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Collection<RefuelMeasure> loadRefuelMeasures(String fileName) {
        try {
            return RefuelMeasureFileHandler.loadData(fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Hashtable<Double, Double> loadHeightVolumeMapper(String fileName){
        try {
            return HeightVolumeMapperFileHandler.loadMapper(fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Hashtable<Integer, Hashtable<Double, Double>> loadHightVolumeMappers(){
        try {
            return HeightVolumeMapperFileHandler.loadMappers();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static class TankMeasureFileHandler {
        
        public static Collection<TankMeasure> loadData(String fileName) throws FileNotFoundException, IOException, ParseException {
            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            Collection<TankMeasure> tankMeasures = new ArrayList();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                TankMeasure tankMeasure = initializeTankMeasureFromStringFieldsList(fields);
                tankMeasures.add(tankMeasure);
            }
            
            bufferedReader.close();
            fileInputStream.close();
            return tankMeasures;
        }

        private static TankMeasure initializeTankMeasureFromStringFieldsList(String[] fields) throws ParseException {
           
            TankMeasure tankMeasure = new TankMeasure(
                fields[0].isEmpty() ? null : LocalDateTime.parse(fields[0], dateTimeFormatter),
                null,
                null,
                fields[3].isEmpty() ? null : Integer.parseInt(fields[3]),
                fields[4].isEmpty() ? null : numberFormat.parse(fields[4]).doubleValue(),
                fields[5].isEmpty() ? null : numberFormat.parse(fields[5]).doubleValue(),
                fields[6].isEmpty() ? null : numberFormat.parse(fields[6]).doubleValue(),
                0.0,
                0.0
            );            
                      
            return tankMeasure;
        }
    }
       
    private static class NozzleMeasureFileHandler{
        public static Collection<NozzleMeasure> loadData(String fileName) throws FileNotFoundException, IOException, ParseException {
            
            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            Collection<NozzleMeasure> nozzleMeasures = new ArrayList();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                NozzleMeasure nozzleMeasure = initializeNozzleMeasureFromStringFieldsList(fields);
                nozzleMeasures.add(nozzleMeasure);
            }

            bufferedReader.close();
            fileInputStream.close();
            return nozzleMeasures;
        }
        private static NozzleMeasure initializeNozzleMeasureFromStringFieldsList(String[] fields) throws ParseException {
           
            NozzleMeasure nozzleMeasure = new NozzleMeasure(
                fields[0].isEmpty() ? null : LocalDateTime.parse(fields[0], dateTimeFormatter),
                null,
                fields[2].isEmpty() ? null : Integer.parseInt(fields[2]),
                fields[3].isEmpty() ? null : Integer.parseInt(fields[3]),
                fields[4].isEmpty() ? null : numberFormat.parse(fields[4]).doubleValue(),
                fields[5].isEmpty() ? null : numberFormat.parse(fields[5]).doubleValue(),
                fields[6].isEmpty() ? null : Integer.parseInt(fields[6])
            );            
                      
            return nozzleMeasure;
        }
    }
    
    private static class RefuelMeasureFileHandler{
        public static Collection<RefuelMeasure> loadData(String fileName) throws FileNotFoundException, IOException, ParseException {
            
            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            Collection<RefuelMeasure> refuelMeasures = new ArrayList();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                RefuelMeasure refuelMeasure = initializeRefuelMeasureFromStringFieldsList(fields);
                refuelMeasures.add(refuelMeasure);
            }

            bufferedReader.close();
            fileInputStream.close();
            return refuelMeasures;
        }
        private static RefuelMeasure initializeRefuelMeasureFromStringFieldsList(String[] fields) throws ParseException {
           
            RefuelMeasure refuelMeasure = new RefuelMeasure(
                fields[0].isEmpty() ? null : LocalDateTime.parse(fields[0], dateTimeFormatter),
                fields[1].isEmpty() ? null : Integer.parseInt(fields[1]),
                fields[2].isEmpty() ? null : numberFormat.parse(fields[2]).doubleValue(),
                fields[3].isEmpty() ? null : numberFormat.parse(fields[3]).doubleValue()
            );            
                      
            return refuelMeasure;
        }
    }
    
    private static class HeightVolumeMapperFileHandler {
        
        private static final String firstMapFile = "dane/mapowanie/Tank1_10012.csv";
        private static final String secondMapFile = "dane/mapowanie/Tank2_20000.csv";
        private static final String thirdMapFile = "dane/mapowanie/Tank3_30000.csv";
        private static final String fourthMapFile = "dane/mapowanie/Tank4_40000.csv";
        
        public static Hashtable<Double, Double> loadMapper(String fileName) throws FileNotFoundException, IOException, ParseException{
            
            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            Hashtable<Double, Double> mapper = new Hashtable<Double, Double>();
            
            line = bufferedReader.readLine(); //Because first row contains strings- attribute names
            while ((line = bufferedReader.readLine()) != null) {
                 String[] fields = line.split(";");
                 mapper.put(numberFormat.parse(fields[0]).doubleValue(), numberFormat.parse(fields[1]).doubleValue());
            }

            bufferedReader.close();
            fileInputStream.close();
            return mapper;
        }
        
        public static Hashtable <Integer, Hashtable<Double,Double>> loadMappers() throws IOException, FileNotFoundException, ParseException{
            Hashtable<Double, Double> firstMapper = loadMapper(firstMapFile);
            Hashtable<Double, Double> secondMapper = loadMapper(secondMapFile);
            Hashtable<Double, Double> thirdMapper = loadMapper(thirdMapFile);
            Hashtable<Double, Double> fourthMapper = loadMapper(fourthMapFile);
            
            Hashtable<Integer, Hashtable<Double,Double>> mappers = new Hashtable<Integer, Hashtable<Double, Double>>();
            mappers.put(1, firstMapper);
            mappers.put(2, secondMapper);
            mappers.put(3, thirdMapper);
            mappers.put(4, fourthMapper);
            
            return mappers;
        }
    }
}
