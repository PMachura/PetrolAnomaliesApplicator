/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyHandler;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyType;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantTankLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ProbeHangConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;

/**
 *
 * @author Przemek
 */
public class AnomalyHandlerTest {
    
    public static void main(String [] args){
        ArrayList<AnomalyHandler> anomaliesHandlers = new ArrayList<AnomalyHandler>();
        AnomalyHandler anomalyHandler = new AnomalyHandler();
        AnomalyConfigurator anomalyConfigurator;
        
        anomalyHandler.setInputDataSetFileFolder("Zestaw 1");
        anomalyHandler.setOutpuDataSetFileFolder("Zestaw 1 Konfiguracja");
        anomalyConfigurator = new ProbeHangConfigurator(LocalDateTime.now(), LocalDateTime.now().plusDays(3),2);
        anomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        
        anomalyConfigurator = new ConstantTankLeakageConfigurator(LocalDateTime.now(), LocalDateTime.now().plusDays(4),2, 5.0);
        anomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        
        anomalyConfigurator = new MeterMiscalibrationConfigurator(LocalDateTime.now(), LocalDateTime.now().plusDays(4),1,1, 2.0);
        anomalyHandler.addAnomalyConfigurator(anomalyConfigurator);
        
        anomaliesHandlers.add(anomalyHandler);
        anomaliesHandlers.add(anomalyHandler);
        
        FileHandler.saveAnomalyHandlerPropertiesAndConfigurators("configurators.txt", anomaliesHandlers);
        FileHandler.saveAnomalyHandlerPropertiesAndConfigurators("singleConfigurator.txt", anomalyHandler);
        
        anomalyHandler = FileHandler.loadAnomalyHandlerPropertiesAndConfigurators("singleConfigurator.txt");
        System.out.println("Pojedynczy");
        for(AnomalyConfigurator configurator : anomalyHandler.getAnomaliesConfigurators()){
                System.out.println(configurator);
        } 
        
        System.out.println("Zbiorowy");
        ArrayList<AnomalyHandler> anomalyConfiguratorCollectorsListLoaded = (ArrayList<AnomalyHandler>) FileHandler.loadAnomalyHandlersPropertiesAndConfigurators("configurators.txt");
        for(AnomalyHandler collector : anomalyConfiguratorCollectorsListLoaded){
            System.out.println("Nowy anomaly handler");
            for(AnomalyConfigurator configurator : collector.getAnomaliesConfigurators()){
                System.out.println(configurator);
            }    
        }
    }
}
