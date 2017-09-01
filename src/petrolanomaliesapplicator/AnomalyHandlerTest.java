/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyConfiguratorCollector;
import petrolanomaliesapplicator.anomaliesconfigurators.AnomalyType;
import petrolanomaliesapplicator.anomaliesconfigurators.ConstantLeakageConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.MeterMiscalibrationConfigurator;
import petrolanomaliesapplicator.anomaliesconfigurators.ProbeHangConfigurator;
import petrolanomaliesapplicator.fileshandlers.FileHandler;

/**
 *
 * @author Przemek
 */
public class AnomalyConfiguratorTest {
    
    public static void main(String [] args){
        ArrayList<AnomalyConfiguratorCollector> anomalyConfiguratorCollectorsList = new ArrayList<AnomalyConfiguratorCollector>();
        AnomalyConfiguratorCollector anomalyConfiguratorCollector = new AnomalyConfiguratorCollector();
        AnomalyConfigurator anomalyConfigurator;
        
        anomalyConfiguratorCollector.addDataSetsNames(new String[]{"Zestaw 1", "Zestaw 2"});
        anomalyConfigurator = new ProbeHangConfigurator(LocalDateTime.now(), LocalDateTime.now().plusDays(3),2);
        anomalyConfiguratorCollector.addAnomalyConfigurator(anomalyConfigurator);
        
        anomalyConfigurator = new ConstantLeakageConfigurator(LocalDateTime.now(), LocalDateTime.now().plusDays(4),2, 5.0);
        anomalyConfiguratorCollector.addAnomalyConfigurator(anomalyConfigurator);
        
        anomalyConfigurator = new MeterMiscalibrationConfigurator(LocalDateTime.now(), LocalDateTime.now().plusDays(4),1,1, 2.0);
        anomalyConfiguratorCollector.addAnomalyConfigurator(anomalyConfigurator);
        
        anomalyConfiguratorCollectorsList.add(anomalyConfiguratorCollector);
        anomalyConfiguratorCollectorsList.add(anomalyConfiguratorCollector);
        
        FileHandler.saveAnomalyConfiguratorCollectorCollection("configurators.txt", anomalyConfiguratorCollectorsList);
        
        ArrayList<AnomalyConfiguratorCollector> anomalyConfiguratorCollectorsListLoaded = (ArrayList<AnomalyConfiguratorCollector>) FileHandler.loadAnomalyConfigurators("configurators.txt");
        for(AnomalyConfiguratorCollector collector : anomalyConfiguratorCollectorsListLoaded){
            for (String s : collector.getDataSetsNames()){
                System.out.println(s);
            }
            for(AnomalyConfigurator configurator : collector.getAnomaliesConfigurators()){
                System.out.println(configurator);
            }    
            System.out.println("@@@@@@@@");
        }
    }
}
