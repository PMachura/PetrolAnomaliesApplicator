/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;
import petrolanomaliesapplicator.anomaliesconfigurators.PipelineLeakageConfigurator;
import petrolanomaliesapplicator.service.TimeCalculator;
import petrolanomaliesapplicator.service.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.factory.FuelHeightVolumeMapperFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class PipelineLeakageApplicator extends AnomalyApplicator {

    private Collection<NozzleMeasure> nozzleMeasures;
    private Integer gunId;
    private Double leakageVolumePerCubicMeter;
    private Double totalLeakedFuelVolume = 0.0;
    //   private ArrayList<ArrayList<NozzleMeasure>> nozzleMesuresRefuelingSeries;
    //  private Iterator nozzleMesuresRefuelingSeriesIterator = nozzleMesuresRefuelingSeries.iterator();

    public PipelineLeakageApplicator(PipelineLeakageConfigurator configurator) {
        super(configurator);
        this.gunId = configurator.getGunId();
        this.leakageVolumePerCubicMeter = configurator.getLeakageVolumePerCubicMeter();
        this.totalLeakedFuelVolume = new Double(0);
        //   this.nozzleMesuresRefuelingSeriesIterator = nozzleMesuresRefuelingSeries.iterator();
    }

    /**
     * Funkcja działa następująco: 1. Zbieramy serie tankowań z zadanego
     * przediału czasowego (dane nozzleMeasure). Seria oznacza pomiary od
     * momentu rozpoczęcia do zakończenia tankowania. 2. Iterując kolejno po
     * seriach tankowań, w danej serii wybieramy początek i koniec odczytując
     * ich daty rozpoczęcia i zakończenia oraz wartości pomiaru liczników 3. Na
     * podstawie danych z pkt. 2 wyszukujemy pomiary zbiorników (tankMeasure),
     * które odpowiadają odczytanym ramom czasowym i aplikujemy ubytek paliwa
     * rozłożony równomiernie między wszystkimi znalezionymi pomiarami
     * zbiorników, pamiętając o ubytku totalnym- pochodzącym z wcześniejszych
     * wycieków.
     *
     * @param tankMeasures
     * @param nozzleMeasures
     * @param startTime
     * @param endTime
     * @param tankId
     * @param gunId
     * @param leakageVolumePerCubicMeter
     * @return
     */
    public static Collection<TankMeasure> applyPipelineLeakage(Collection<TankMeasure> tankMeasures, Collection<NozzleMeasure> nozzleMeasures,
            LocalDateTime startTime, LocalDateTime endTime, Integer tankId, Integer gunId, Double leakageVolumePerCubicMeter) {

        Double totalLeakedFuelVolume = 0.0;
        ArrayList<ArrayList<NozzleMeasure>> nozzleMesuresRefuelingSeries = findNozzleMesuresRefuelingSeries(nozzleMeasures, startTime, endTime, tankId, gunId);
        ArrayList<TankMeasure> modifiedTankMeasures = new ArrayList<TankMeasure>(tankMeasures.size());
        for (TankMeasure tankMeasure : tankMeasures) {
            modifiedTankMeasures.add(tankMeasure.clone());
        }

        for (ArrayList<NozzleMeasure> nozzleMeasureSeries : nozzleMesuresRefuelingSeries) {
            if (!nozzleMeasureSeries.isEmpty()) {

                LocalDateTime start = nozzleMeasureSeries.get(0).getDateTime();
                LocalDateTime end = nozzleMeasureSeries.get(nozzleMeasureSeries.size() - 1).getDateTime();
                Double fuelVolumeCounterDifference = nozzleMeasureSeries.get(nozzleMeasureSeries.size() - 1).getTotalCounter()
                        - nozzleMeasureSeries.get(0).getTotalCounter();

                Double leakedFuelVolumeInSerie = fuelVolumeCounterDifference * leakageVolumePerCubicMeter;
                totalLeakedFuelVolume += leakedFuelVolumeInSerie;
                applyVolumeLeakageInTime(modifiedTankMeasures, start, end, tankId, leakedFuelVolumeInSerie, totalLeakedFuelVolume);
            }
        }

        return modifiedTankMeasures;

    }

    public Collection<TankMeasure> applyPipelineLeakage(Collection<TankMeasure> tankMeasures, Collection<NozzleMeasure> nozzleMeasures) {

        return applyPipelineLeakage(tankMeasures, nozzleMeasures, startTime, endTime, tankId, gunId, leakageVolumePerCubicMeter);

    }

    private static Collection<TankMeasure> applyVolumeLeakageInTime(Collection<TankMeasure> tankMeasures, LocalDateTime startTime, LocalDateTime endTime,
            Integer tankId, Double leakedFuelVolume, Double totalLeakedFuelVolume) {

        FuelHeightVolumeMapper volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);
        ArrayList<TankMeasure> tankMeasuresInDateRange = new ArrayList<TankMeasure>();
        boolean isLastTankMeasureAdded = false;

        for (TankMeasure tankMeasure : tankMeasures) {
            if (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()) && tankMeasure.getTankId().equals(tankId)) {
                tankMeasuresInDateRange.add(tankMeasure);
            }
            if (tankMeasure.getDateTime().isAfter(endTime)) {
                if (isLastTankMeasureAdded) {
                    tankMeasure.verifySetFuelVolume(tankMeasure.getFuelVolume() - leakedFuelVolume);
                    tankMeasure.verifySetFuelHeight(volumeToHeightMapper.calculate(tankMeasure.getFuelVolume()));
                } else {
                    tankMeasuresInDateRange.add(tankMeasure);
                    isLastTankMeasureAdded = true;
                }

            }
        }

        if (!tankMeasuresInDateRange.isEmpty()) {

            Double totalLeakageInSerie = 0.0;
            Double leakageVolumePerOneMeasurement = leakedFuelVolume / tankMeasuresInDateRange.size();
            for (TankMeasure tankMeasure : tankMeasuresInDateRange) {

                totalLeakageInSerie += leakageVolumePerOneMeasurement;
              //  tankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - totalLeakageInSerie - totalLeakedFuelVolume);
              tankMeasure.verifySetFuelVolume(tankMeasure.getFuelVolume() - totalLeakageInSerie);
                tankMeasure.verifySetFuelHeight(volumeToHeightMapper.calculate(tankMeasure.getFuelVolume()));

            }
        }

        return tankMeasures;
    }

    public static ArrayList<ArrayList<NozzleMeasure>> findNozzleMesuresRefuelingSeries(Collection<NozzleMeasure> nozzleMeasures, LocalDateTime startTime,
            LocalDateTime endTime, Integer tankId, Integer gunId) {

        ArrayList<ArrayList<NozzleMeasure>> nozzleMesuresRefuelingSeries = new ArrayList<ArrayList<NozzleMeasure>>();
        NozzleMeasure refuelingStartNozzleMeasure = null;

        for (NozzleMeasure nozzleMeasure : nozzleMeasures) {
            if (nozzleMeasure.getDateTime().isAfter(endTime)) {
                break;
            } else if (TimeCalculator.isDateInRange(startTime, endTime, nozzleMeasure.getDateTime())) {
                if (nozzleMeasure.getTankId() != tankId || nozzleMeasure.getGunId() != gunId) {
                    continue;
                } else if ((nozzleMeasure.getStatus() == 0 && refuelingStartNozzleMeasure == null)) {
                    refuelingStartNozzleMeasure = nozzleMeasure;
                    nozzleMesuresRefuelingSeries.add(new ArrayList<NozzleMeasure>());
                    nozzleMesuresRefuelingSeries.get(nozzleMesuresRefuelingSeries.size() - 1).add(nozzleMeasure);
                } else if ((nozzleMeasure.getStatus() == 0) && refuelingStartNozzleMeasure!= null && !(refuelingStartNozzleMeasure.getTotalCounter().equals(nozzleMeasure.getTotalCounter()))) {
                    refuelingStartNozzleMeasure = nozzleMeasure;
                    nozzleMesuresRefuelingSeries.add(new ArrayList<NozzleMeasure>());
                    nozzleMesuresRefuelingSeries.get(nozzleMesuresRefuelingSeries.size() - 1).add(nozzleMeasure);
                } else if (nozzleMeasure.getStatus() == 0 && refuelingStartNozzleMeasure != null  && (refuelingStartNozzleMeasure.getTotalCounter().equals(nozzleMeasure.getTotalCounter())) ) {
                    nozzleMesuresRefuelingSeries.get(nozzleMesuresRefuelingSeries.size() - 1).add(nozzleMeasure);
                } else if (nozzleMeasure.getStatus() == 1 && refuelingStartNozzleMeasure != null) {
                    nozzleMesuresRefuelingSeries.get(nozzleMesuresRefuelingSeries.size() - 1).add(nozzleMeasure);
                    refuelingStartNozzleMeasure = null;

                }
            }
        }

        return nozzleMesuresRefuelingSeries;

    }
}
