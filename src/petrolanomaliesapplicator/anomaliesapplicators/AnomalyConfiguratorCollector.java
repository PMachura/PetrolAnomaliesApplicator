/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author Przemek
 */
public class AnomalyConfiguratorCollector {
    
    private Collection <AnomalyConfigurator> anomaliesConfigurators;
    private ArrayList <String> dataSetsNames;
    
    public void addDataSetsNames(String [] dataSetsNames){
        this.dataSetsNames.addAll(Arrays.asList(dataSetsNames));
    }
    
    public void addAnomalyConfigurator(AnomalyConfigurator anomalyConfigurator){
        this.anomaliesConfigurators.add(anomalyConfigurator);
    }

    public Collection<AnomalyConfigurator> getAnomaliesConfigurators() {
        return anomaliesConfigurators;
    }

    public void setAnomaliesConfigurators(Collection<AnomalyConfigurator> anomaliesConfigurators) {
        this.anomaliesConfigurators = anomaliesConfigurators;
    }

 
    
    
}
