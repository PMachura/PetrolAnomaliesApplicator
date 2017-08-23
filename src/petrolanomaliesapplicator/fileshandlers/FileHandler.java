/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.fileshandlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
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
import petrolanomaliesapplicator.anomaliesapplicators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesapplicators.AnomalyConfiguratorCollector;
import petrolanomaliesapplicator.anomaliesapplicators.AnomalyType;
import petrolanomaliesapplicator.anomaliesapplicators.LeakageConfigurator;
import petrolanomaliesapplicator.anomaliesapplicators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesapplicators.ProbeHangConfigurator;

import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.RefuelMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class FileHandler {

    static File file;
    static FileInputStream fileInputStream;
    static BufferedReader bufferedReader;
    static BufferedWriter bufferedWriter;
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
    static DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    public static Collection<TankMeasure> loadTankMeasures(String fileName) {
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

    public static Hashtable<Double, Double> loadHeightVolumeMapper(String fileName) {
        try {
            return HeightVolumeMapperFileHandler.loadMapper(fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Hashtable<Integer, Hashtable<Double, Double>> loadHightVolumeMappers() {
        try {
            return HeightVolumeMapperFileHandler.loadMappers();
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void saveNozzleMeasuresToFile(Collection<NozzleMeasure> dataCollection, String fileName) {
        try {
            NozzleMeasureFileHandler.saveData((Collection<NozzleMeasure>) dataCollection, fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveTankMeasuresToFile(Collection<TankMeasure> dataCollection, String fileName) {
        try {
            TankMeasureFileHandler.saveData((Collection<TankMeasure>) dataCollection, fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveRefuelMeasuresToFile(Collection<RefuelMeasure> dataCollection, String fileName) {
        try {
            RefuelMeasureFileHandler.saveData((Collection<RefuelMeasure>) dataCollection, fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void writeValueToFile(String value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(value);
        }
    }

    private static void writeValueToFile(Double value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(decimalFormat.format(value));
        }
    }

    private static void writeValueToFile(Integer value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(value.toString());
        }
    }

    private static void writeValueToFile(LocalDateTime value, BufferedWriter bufferedWriter) throws IOException {
        if (value != null) {
            bufferedWriter.write(value.format(dateTimeFormatter));
        }
    }

    private static void writeSemicolonToFile(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(";");
    }
    
    private static LocalDateTime toDate(String date){
        return (date == null || date.isEmpty()) ? null : LocalDateTime.parse(date, dateTimeFormatter);
    }
    
    private static Double toDouble(String value){
        try {
            return (value == null || value.isEmpty()) ? null : numberFormat.parse(value).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }
    
    private static Integer toInteger(String value){
        return (value == null || value.isEmpty()) ? null : Integer.parseInt(value);
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

        public static void saveData(Collection<TankMeasure> tankMeasures, String fileName) throws IOException {

            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            tankMeasures.forEach((TankMeasure tankMeasure) -> {

                try {
                    writeValueToFile(tankMeasure.getDateTime(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(tankMeasure.getLocationId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(tankMeasure.getMeterId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(tankMeasure.getTankId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(tankMeasure.getFuelHeight(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(tankMeasure.getFuelVolume(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(tankMeasure.getFuelTemperature(), bufferedWriter);

                    bufferedWriter.newLine();

                } catch (IOException ex) {
                    Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            );

            bufferedWriter.close();
        }

        private static TankMeasure initializeTankMeasureFromStringFieldsList(String[] fields) throws ParseException {

            TankMeasure tankMeasure = new TankMeasure(
                    toDate(fields[0]),
                    null,
                    null,
                    toInteger(fields[3]),
                    toDouble(fields[4]),
                    toDouble(fields[5]),
                    toDouble(fields[6]),
                    0.0,
                    0.0
            );

            return tankMeasure;
        }
    }

    private static class NozzleMeasureFileHandler {

        public static void saveData(Collection<NozzleMeasure> nozzleMeasures, String fileName) throws IOException {

            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            nozzleMeasures.forEach((NozzleMeasure nozzleMeasure) -> {

                try {
                    writeValueToFile(nozzleMeasure.getDateTime(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(nozzleMeasure.getLocationId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(nozzleMeasure.getGunId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(nozzleMeasure.getTankId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(nozzleMeasure.getLiterCounter(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(nozzleMeasure.getTotalCounter(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(nozzleMeasure.getStatus(), bufferedWriter);

                    bufferedWriter.newLine();

                } catch (IOException ex) {
                    Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            );

            bufferedWriter.close();
        }

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
                    toDate(fields[0]),
                    null,
                    toInteger(fields[2]),
                    toInteger(fields[3]),
                    toDouble(fields[4]),
                    toDouble(fields[5]),
                    toInteger(fields[6])
            );

            return nozzleMeasure;
        }
    }

    private static class RefuelMeasureFileHandler {

        public static void saveData(Collection<RefuelMeasure> refuelMeasures, String fileName) throws IOException {

            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            refuelMeasures.forEach((RefuelMeasure refuelMeasure) -> {

                try {
                    writeValueToFile(refuelMeasure.getDateTime(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(refuelMeasure.getTankId(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(refuelMeasure.getCisternPetrolVolume(), bufferedWriter);
                    writeSemicolonToFile(bufferedWriter);
                    writeValueToFile(refuelMeasure.getRefuelingSpeed(), bufferedWriter);

                    bufferedWriter.newLine();

                } catch (IOException ex) {
                    Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            );

            bufferedWriter.close();
        }

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
                    toDate(fields[0]),
                    toInteger(fields[1]),
                    toDouble(fields[2]),
                    toDouble(fields[3])
            );

            return refuelMeasure;
        }
    }

    private static class HeightVolumeMapperFileHandler {

        private static final String firstMapFile = "dane/mapowanie/Tank1_10012.csv";
        private static final String secondMapFile = "dane/mapowanie/Tank2_20000.csv";
        private static final String thirdMapFile = "dane/mapowanie/Tank3_30000.csv";
        private static final String fourthMapFile = "dane/mapowanie/Tank4_40000.csv";

        public static Hashtable<Double, Double> loadMapper(String fileName) throws FileNotFoundException, IOException, ParseException {

            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            Hashtable<Double, Double> mapper = new Hashtable<Double, Double>();

            line = bufferedReader.readLine(); //Because first row contains strings- attribute names
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                mapper.put(toDouble(fields[0]), toDouble(fields[1]));
            }

            bufferedReader.close();
            fileInputStream.close();
            return mapper;
        }

        public static Hashtable<Integer, Hashtable<Double, Double>> loadMappers() throws IOException, FileNotFoundException, ParseException {
            Hashtable<Double, Double> firstMapper = loadMapper(firstMapFile);
            Hashtable<Double, Double> secondMapper = loadMapper(secondMapFile);
            Hashtable<Double, Double> thirdMapper = loadMapper(thirdMapFile);
            Hashtable<Double, Double> fourthMapper = loadMapper(fourthMapFile);

            Hashtable<Integer, Hashtable<Double, Double>> mappers = new Hashtable<Integer, Hashtable<Double, Double>>();
            mappers.put(1, firstMapper);
            mappers.put(2, secondMapper);
            mappers.put(3, thirdMapper);
            mappers.put(4, fourthMapper);

            return mappers;
        }
    }

    private static class AnomalyConfiguratorFileHandler {

        
        public static AnomalyConfiguratorCollector loadConfigurators(String fileName) throws FileNotFoundException, IOException, ParseException {

            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            String anomalyDescriptor;
            AnomalyConfiguratorCollector anomalyConfiguratorCollector = new AnomalyConfiguratorCollector();
            AnomalyType[] anomalyTypes = AnomalyType.values();
            AnomalyConfigurator anomalyConfigurator;

            while ((line = bufferedReader.readLine()) != null) {
                String[] dataSets = line.split(";");
                anomalyConfiguratorCollector.addDataSetsNames(dataSets);
                while ((anomalyDescriptor = bufferedReader.readLine()) != null) {
                    String [] anomalyParameters = anomalyDescriptor.split(";");
                    switch (anomalyParameters[0]) {
                        case ("MeterMiscalibration"):
                            anomalyConfigurator = new MeterMiscalibrationConfigurator();
                        case ("ProbeHang"):
                            anomalyConfigurator = new ProbeHangConfigurator();
                        case ("ConstantLeakage"):
                            anomalyConfigurator = new LeakageConfigurator();
                        case ("VariableLeakage"):
                            anomalyConfigurator = new LeakageConfigurator();
                        case ("PipelineLeakage"):
                            anomalyConfigurator = new LeakageConfigurator();

                    }
                }
            }

            bufferedReader.close();
            fileInputStream.close();
            return anomalyConfiguratorCollector;
        }
    }
}
