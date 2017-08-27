/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesconfigurators;

/**
 *
 * @author Przemek
 */
/**
 *
 * @author Przemek
 */
public enum AnomalyType {
    METER_MISCALIBRATION("MeterMiscalibration"),
    PROBE_HANG("ProbeHang"),
    CONSTANT_LEAKAGE("ConstantLeakage"),
    VARIABLE_LEAKAGE("VariableLeakage"),
    PIPELINE_LEAKAGE("PipelineLeakage");

    private final String anomalyName;

    private AnomalyType(String anomalyName) {
        this.anomalyName = anomalyName;
    }

    public String getAnomalyName() {
        return anomalyName;
    }
    
    

}


