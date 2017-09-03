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
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyHandler;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyType;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantTankLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.PipelineLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ProbeHangConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.VariableTankLeakageConfigurator;
import petrolanomaliesapplicator.model.DataSetCollection;
import petrolanomaliesapplicator.model.BaseDataSetLocation;
import petrolanomaliesapplicator.model.DataSetType;
import petrolanomaliesapplicator.model.MapperDataLocation;

import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.RefuelMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class FileHandler {

    private static enum MapperType {
        HEIGHT_TO_VOLUME,
        VOLUME_TO_HEIGHT
    }

    static final String configuratorsPath = "dane/konfiguratory/";
    static final String dataSetPath = "dane/";
    static final String configuratorsFileName = "configurators.txt";

    static File file;
    static FileInputStream fileInputStream;
    static BufferedReader bufferedReader;
    static BufferedWriter bufferedWriter;
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
    static DecimalFormat decimalFormat = new DecimalFormat("#.##########");
    
    public static void createDirectory(String directory){
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static DataSetCollection loadDataSetCollection(BaseDataSetLocation dataSetLocation) {
        DataSetCollection dataSetCollection = new DataSetCollection();
        dataSetCollection.setTankMeasures((ArrayList<TankMeasure>) loadTankMeasures(dataSetLocation));
        dataSetCollection.setNozzleMeasures((ArrayList<NozzleMeasure>) loadNozzleMeasures(dataSetLocation));
        dataSetCollection.setRefuelMeasures((ArrayList<RefuelMeasure>) loadRefuelMeasures(dataSetLocation));
        return dataSetCollection;
    }

    public static DataSetCollection loadDataSetCollection(String folderName) {
        DataSetCollection dataSetCollection = new DataSetCollection();
        dataSetCollection.setTankMeasures((ArrayList<TankMeasure>) loadTankMeasures(dataSetPath + folderName + "/" + DataSetType.TANK_MEASURES.getFileName()));
        dataSetCollection.setNozzleMeasures((ArrayList<NozzleMeasure>) loadNozzleMeasures(dataSetPath + folderName + "/" + DataSetType.NOZZLE_MEASURES.getFileName()));
        dataSetCollection.setRefuelMeasures((ArrayList<RefuelMeasure>) loadRefuelMeasures(dataSetPath + folderName + "/" + DataSetType.REFUEL_MEASURES.getFileName()));
        return dataSetCollection;
    }

    public static void saveDataSetCollection(String folderName, DataSetCollection dataSetCollection) {
        createDirectory(dataSetPath + folderName);
        saveTankMeasuresToFile(dataSetCollection.getTankMeasures(), dataSetPath + folderName + "/" + DataSetType.TANK_MEASURES.getFileName());
        saveNozzleMeasuresToFile(dataSetCollection.getNozzleMeasures(), dataSetPath + folderName + "/" + DataSetType.NOZZLE_MEASURES.getFileName());
        saveRefuelMeasuresToFile(dataSetCollection.getRefuelMeasures(), dataSetPath + folderName + "/" + DataSetType.REFUEL_MEASURES.getFileName());
    }
    
    public static void saveAnomalyHandlerOutputDataSetCollection(AnomalyHandler anomalyHandler){
        saveDataSetCollection(anomalyHandler.getOutpuDataSetFileFolder(),anomalyHandler.getOutputDataSetCollection());
    }

    public static Collection<TankMeasure> loadTankMeasures(BaseDataSetLocation dataSetLocation) {
        return loadTankMeasures(dataSetLocation.getTankMeasurePath());
    }

    public static Collection<NozzleMeasure> loadNozzleMeasures(BaseDataSetLocation dataSetLocation) {
        return loadNozzleMeasures(dataSetLocation.getNozzleMeasurePath());
    }

    public static Collection<RefuelMeasure> loadRefuelMeasures(BaseDataSetLocation dataSetLocation) {
        return loadRefuelMeasures(dataSetLocation.getRefuelMeasurePath());
    }

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

    public static Hashtable<Double, Double> loadHeightToVolumeMapper(String fileName) {
        try {
            return HeightVolumeMapperFileHandler.loadMapper(fileName, MapperType.HEIGHT_TO_VOLUME);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Hashtable<Integer, Hashtable<Double, Double>> loadHeightToVolumeMappers() {
        try {
            return HeightVolumeMapperFileHandler.loadMappers(MapperType.HEIGHT_TO_VOLUME);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Hashtable<Double, Double> loadVolumeToHeightMapper(String fileName) {
        try {
            return HeightVolumeMapperFileHandler.loadMapper(fileName, MapperType.VOLUME_TO_HEIGHT);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Hashtable<Integer, Hashtable<Double, Double>> loadVolumeToHeightMappers() {
        try {
            return HeightVolumeMapperFileHandler.loadMappers(MapperType.VOLUME_TO_HEIGHT);
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

    public static Collection<AnomalyHandler> loadAnomalyHandlersPropertiesAndConfigurators(String fileName) {
        try {
            return AnomalyHandlerFileHandler.loadAnomalyHandlersPropertiesAndConfigurators(configuratorsPath + fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static Collection<AnomalyHandler> loadAnomalyHandlersPropertiesAndConfigurators() {
        return loadAnomalyHandlersPropertiesAndConfigurators(configuratorsFileName);
    }

    public static AnomalyHandler loadAnomalyHandlerPropertiesAndConfigurators(String fileName) throws IOException {
        try {
            return AnomalyHandlerFileHandler.loadAnomalyHandlerPropertiesAndConfigurators(configuratorsPath + fileName);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException();
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public static void saveAnomalyHandlerPropertiesAndConfigurators(String fileName, Collection<AnomalyHandler> anomalyHandlersCollection) {
        try {
            AnomalyHandlerFileHandler.saveAnomalyHandlerPropertiesAndConfigurators(configuratorsPath + fileName, (ArrayList<AnomalyHandler>) anomalyHandlersCollection);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveAnomalyHandlerPropertiesAndConfigurators(String fileName, AnomalyHandler anomalyHandler) {
        try {
            AnomalyHandlerFileHandler.saveAnomalyHandlerPropertiesAndConfigurators(configuratorsPath + fileName, anomalyHandler);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveAnomalyHandlerPropertiesAndConfigurators(Collection<AnomalyHandler> anomalyHandlersCollection) {
        saveAnomalyHandlerPropertiesAndConfigurators(configuratorsFileName, (ArrayList<AnomalyHandler>) anomalyHandlersCollection);
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

    private static LocalDateTime toDate(String date) {
        return (date == null || date.isEmpty()) ? null : LocalDateTime.parse(date, dateTimeFormatter);
    }

    private static Double toDouble(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : numberFormat.parse(value).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

    private static Integer toInteger(String value) {
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

        private static final String firstMapFile = MapperDataLocation.TANK_1.getFilePath();
        private static final String secondMapFile = MapperDataLocation.TANK_2.getFilePath();
        private static final String thirdMapFile = MapperDataLocation.TANK_3.getFilePath();
        private static final String fourthMapFile = MapperDataLocation.TANK_4.getFilePath();

        public static Hashtable<Double, Double> loadMapper(String fileName, MapperType mapperType) throws FileNotFoundException, IOException, ParseException {

            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            Hashtable<Double, Double> mapper = new Hashtable<Double, Double>();

            line = bufferedReader.readLine(); //Because first row contains strings- attribute names
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                if (mapperType == MapperType.HEIGHT_TO_VOLUME) {
                    mapper.put(toDouble(fields[0]), toDouble(fields[1]));
                } else if (mapperType == MapperType.VOLUME_TO_HEIGHT) {
                    mapper.put(toDouble(fields[1]), toDouble(fields[0]));
                }

            }

            bufferedReader.close();
            fileInputStream.close();
            return mapper;
        }

        public static Hashtable<Integer, Hashtable<Double, Double>> loadMappers(MapperType mapperType) throws IOException, FileNotFoundException, ParseException {
            Hashtable<Double, Double> firstMapper = loadMapper(firstMapFile, mapperType);
            Hashtable<Double, Double> secondMapper = loadMapper(secondMapFile, mapperType);
            Hashtable<Double, Double> thirdMapper = loadMapper(thirdMapFile, mapperType);
            Hashtable<Double, Double> fourthMapper = loadMapper(fourthMapFile, mapperType);

            Hashtable<Integer, Hashtable<Double, Double>> mappers = new Hashtable<Integer, Hashtable<Double, Double>>();
            mappers.put(1, firstMapper);
            mappers.put(2, secondMapper);
            mappers.put(3, thirdMapper);
            mappers.put(4, fourthMapper);

            return mappers;
        }

    }

    private static class AnomalyHandlerFileHandler {

        private static final String endSingInFileForDataSetsConfigurators = "----------------------------------";
        private static final String endFileSign = "END";

        private static MeterMiscalibrationConfigurator initializeMeterMiscalibrationConfigurator(String[] params) {
            return new MeterMiscalibrationConfigurator(toDate(params[1]), toDate(params[2]),
                    toInteger(params[3]), toInteger(params[4]),
                    toDouble(params[5]));
        }

        private static ProbeHangConfigurator initializeProbeHangConfigurator(String[] params) {
            return new ProbeHangConfigurator(toDate(params[1]), toDate(params[2]),
                    toInteger(params[3]));
        }

        private static ConstantTankLeakageConfigurator initializeConstantLeakageConfigurator(String[] params) {
            return new ConstantTankLeakageConfigurator(toDate(params[1]), toDate(params[2]),
                    toInteger(params[3]), toDouble(params[4]));
        }

        private static VariableTankLeakageConfigurator initializeVariableLeakageConfigurator(String[] params) {
            return new VariableTankLeakageConfigurator(toDate(params[1]), toDate(params[2]),
                    toInteger(params[3]), toDouble(params[4]));
        }

        private static PipelineLeakageConfigurator initializePipelineLeakageConfigurator(String[] params) {
            return new PipelineLeakageConfigurator(toDate(params[1]), toDate(params[2]),
                    toInteger(params[3]), toInteger(params[4]), toDouble(params[5]));
        }

        private static void saveBasicConfigurator(AnomalyConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            writeValueToFile(anomalyConfigurator.getAnomalyTpe().getAnomalyName(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
            writeValueToFile(anomalyConfigurator.getStartDateTime(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
            writeValueToFile(anomalyConfigurator.getEndDateTime(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
            writeValueToFile(anomalyConfigurator.getTankId(), bufferedWriter);
            System.out.println("Tank id " + anomalyConfigurator.getTankId());
            writeSemicolonToFile(bufferedWriter);
        }

        private static void saveConstantLeakageConfigurator(ConstantTankLeakageConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            saveBasicConfigurator(anomalyConfigurator, bufferedWriter);
            writeValueToFile(anomalyConfigurator.getLeakageVolumePerHour(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
        }

        private static void saveVariableLeakageConfigurator(VariableTankLeakageConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            saveBasicConfigurator(anomalyConfigurator, bufferedWriter);
            writeValueToFile(anomalyConfigurator.getLeakingPointHeight(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);

        }

        private static void saveProbeHangConfigurator(ProbeHangConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            saveBasicConfigurator(anomalyConfigurator, bufferedWriter);
        }

        private static void saveMeterMiscalibrationConfigurator(MeterMiscalibrationConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            saveBasicConfigurator(anomalyConfigurator, bufferedWriter);
            writeValueToFile(anomalyConfigurator.getGunId(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
            writeValueToFile(anomalyConfigurator.getMiscalibrationCoefficientPerOneCubicMeter(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);

        }

        public static void savePipelineLeakageConfigurator(PipelineLeakageConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            saveBasicConfigurator(anomalyConfigurator, bufferedWriter);
            writeValueToFile(anomalyConfigurator.getGunId(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
            writeValueToFile(anomalyConfigurator.getLeakageVolumePerCubicMeter(), bufferedWriter);
            writeSemicolonToFile(bufferedWriter);
        }

        public static Collection<AnomalyHandler> loadAnomalyHandlersPropertiesAndConfigurators(String fileName) throws FileNotFoundException, IOException, ParseException {

            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            Collection<AnomalyHandler> anomalyConfiguratorCollectorCollection
                    = new ArrayList<AnomalyHandler>();

            String line;

            while ((line = bufferedReader.readLine()) != null) {

                if (line.equals(endFileSign)) {
                    break;
                }

                /**
                 * Wczytywanie danych dla AnomalyHandler
                 */
                AnomalyHandler anomalyHandler = new AnomalyHandler();
                String[] dataSetFolderLocation = line.split(";");
                anomalyHandler.setDataSetFileFolders(dataSetFolderLocation);

                /**
                 * Wczytywanie poszczególnych konfiguratorów
                 */
                ArrayList<AnomalyConfigurator> anomalyConfiguratorCollector = loadAnomalyConfigurators(bufferedReader);
                anomalyHandler.setAnomaliesConfigurators(anomalyConfiguratorCollector);

                anomalyConfiguratorCollectorCollection.add(anomalyHandler);
            }

            bufferedReader.close();
            fileInputStream.close();
            return anomalyConfiguratorCollectorCollection;
        }

        public static AnomalyHandler loadAnomalyHandlerPropertiesAndConfigurators(String fileName) throws FileNotFoundException, IOException, ParseException {

            file = new File(fileName);
            fileInputStream = new FileInputStream(file);
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            String line;
            AnomalyHandler anomalyHandler = null;


            while ((line = bufferedReader.readLine()) != null) {

                if (line.equals(endFileSign)) {
                    break;
                }

                /**
                 * Wczytywanie danych dla AnomalyHandler
                 */
                anomalyHandler = new AnomalyHandler();
                String[] dataSetFolderLocation = line.split(";");
                anomalyHandler.setDataSetFileFolders(dataSetFolderLocation);

                /**
                 * Wczytywanie poszczególnych konfiguratorów
                 */
                ArrayList<AnomalyConfigurator> anomalyConfiguratorCollector = loadAnomalyConfigurators(bufferedReader);
                anomalyHandler.setAnomaliesConfigurators(anomalyConfiguratorCollector);

            }

            bufferedReader.close();
            fileInputStream.close();
            return anomalyHandler;
        }

        private static ArrayList<AnomalyConfigurator> loadAnomalyConfigurators(BufferedReader bufferedReader) throws IOException {

            String line;
            AnomalyConfigurator anomalyConfigurator = null;
            ArrayList<AnomalyConfigurator> anomalyConfiguratorCollector = new ArrayList<AnomalyConfigurator>();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(endSingInFileForDataSetsConfigurators)) {
                    break;
                }

                String[] anomalyParameters = line.split(";");
                if (anomalyParameters[0].equals(AnomalyType.METER_MISCALIBRATION.getAnomalyName())) {
                    anomalyConfigurator = initializeMeterMiscalibrationConfigurator(anomalyParameters);

                } else if (anomalyParameters[0].equals(AnomalyType.PROBE_HANG.getAnomalyName())) {
                    anomalyConfigurator = initializeProbeHangConfigurator(anomalyParameters);

                } else if (anomalyParameters[0].equals(AnomalyType.CONSTANT_LEAKAGE.getAnomalyName())) {
                    anomalyConfigurator = initializeConstantLeakageConfigurator(anomalyParameters);

                } else if (anomalyParameters[0].equals(AnomalyType.VARIABLE_LEAKAGE.getAnomalyName())) {
                    anomalyConfigurator = initializeVariableLeakageConfigurator(anomalyParameters);

                } else if (anomalyParameters[0].equals(AnomalyType.PIPELINE_LEAKAGE.getAnomalyName())) {
                    anomalyConfigurator = initializePipelineLeakageConfigurator(anomalyParameters);
                }

                if (anomalyConfigurator != null) {
                    anomalyConfiguratorCollector.add(anomalyConfigurator);
                }
            }
            return anomalyConfiguratorCollector;
        }

        public static void saveAnomalyHandlerPropertiesAndConfigurators(String fileName, ArrayList<AnomalyHandler> anomalyHandlerList) throws IOException {
            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            for (AnomalyHandler anomalyHandler : anomalyHandlerList) {

                writeValueToFile(anomalyHandler.getInputDataSetFileFolder(), bufferedWriter);
                writeValueToFile(";", bufferedWriter);
                writeValueToFile(anomalyHandler.getOutpuDataSetFileFolder(), bufferedWriter);
                writeValueToFile(";", bufferedWriter);

                bufferedWriter.newLine();

                for (AnomalyConfigurator anomalyConfigurator : anomalyHandler.getAnomaliesConfigurators()) {
                    saveAnomalyConfigurator(anomalyConfigurator, bufferedWriter);
                    bufferedWriter.newLine();
                }
                writeValueToFile(endSingInFileForDataSetsConfigurators, bufferedWriter);
                bufferedWriter.newLine();
            }
            writeValueToFile(endFileSign, bufferedWriter);

            bufferedWriter.close();
        }

        public static void saveAnomalyHandlerPropertiesAndConfigurators(String fileName, AnomalyHandler anomalyHandler) throws IOException {
            File file = new File(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

            writeValueToFile(anomalyHandler.getInputDataSetFileFolder(), bufferedWriter);
            writeValueToFile(";", bufferedWriter);
            writeValueToFile(anomalyHandler.getOutpuDataSetFileFolder(), bufferedWriter);
            writeValueToFile(";", bufferedWriter);

            bufferedWriter.newLine();

            for (AnomalyConfigurator anomalyConfigurator : anomalyHandler.getAnomaliesConfigurators()) {
                saveAnomalyConfigurator(anomalyConfigurator, bufferedWriter);
                bufferedWriter.newLine();
            }
            writeValueToFile(endSingInFileForDataSetsConfigurators, bufferedWriter);
            bufferedWriter.newLine();

            writeValueToFile(endFileSign, bufferedWriter);

            bufferedWriter.close();
        }

        public static void saveAnomalyConfigurator(AnomalyConfigurator anomalyConfigurator, BufferedWriter bufferedWriter) throws IOException {
            switch (anomalyConfigurator.getAnomalyTpe()) {

                case CONSTANT_LEAKAGE: {
                    saveConstantLeakageConfigurator((ConstantTankLeakageConfigurator) anomalyConfigurator, bufferedWriter);
                    break;
                }

                case METER_MISCALIBRATION: {
                    saveMeterMiscalibrationConfigurator((MeterMiscalibrationConfigurator) anomalyConfigurator, bufferedWriter);
                    break;
                }

                case PIPELINE_LEAKAGE: {
                    savePipelineLeakageConfigurator((PipelineLeakageConfigurator) anomalyConfigurator, bufferedWriter);
                    break;
                }

                case PROBE_HANG: {
                    saveProbeHangConfigurator((ProbeHangConfigurator) anomalyConfigurator, bufferedWriter);
                    break;
                }

                case VARIABLE_LEAKAGE: {
                    saveVariableLeakageConfigurator((VariableTankLeakageConfigurator) anomalyConfigurator, bufferedWriter);
                    break;
                }

            }
        }

    }
}
