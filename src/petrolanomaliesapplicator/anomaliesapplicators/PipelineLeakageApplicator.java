/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.anomaliesapplicators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import petrolanomaliesapplicator.helpers.TimeCalculator;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapper;
import petrolanomaliesapplicator.model.FuelHeightVolumeMapperFactory;
import petrolanomaliesapplicator.model.NozzleMeasure;
import petrolanomaliesapplicator.model.TankMeasure;

/**
 *
 * @author Przemek
 */
public class PipelineLeakageApplicator {
    /**
     * Funkcja działa następująco:
     * 1. Zbieramy serie tankowań z zadanego przediału czasowego (dane nozzleMeasure). Seria oznacza pomiary od momentu rozpoczęcia do zakończenia tankowania.
     * 2. Iterując kolejno po seriach tankowań, w danej serii wybieramy początek i koniec odczytując ich daty rozpoczęcia i zakończenia oraz wartości pomiaru liczników
     * 3. Na podstawie danych z pkt. 2 wyszukujemy pomiary zbiorników (tankMeasure), które odpowiadają odczytanym ramom czasowym i aplikujemy ubytek paliwa
     *    rozłożony równomiernie między wszystkimi znalezionymi pomiarami zbiorników, pamiętając o ubytku totalnym- pochodzącym z wcześniejszych wycieków.
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
        for (ArrayList<NozzleMeasure> nozzleMeasureSeries : nozzleMesuresRefuelingSeries) {
            if (!nozzleMeasureSeries.isEmpty()) {
                System.out.println("Nie pusta");
                LocalDateTime start = nozzleMeasureSeries.get(0).getDateTime();
                LocalDateTime end = nozzleMeasureSeries.get(nozzleMeasureSeries.size() - 1).getDateTime();
                Double fuelVolumeCounterDifference = nozzleMeasureSeries.get(nozzleMeasureSeries.size() - 1).getTotalCounter()
                        - nozzleMeasureSeries.get(0).getTotalCounter();

                Double leakedFuelVolumeInSerie = fuelVolumeCounterDifference * leakageVolumePerCubicMeter;
                totalLeakedFuelVolume += leakedFuelVolumeInSerie;
                applyVolumeLeakageInTime(tankMeasures, start, end, tankId, leakedFuelVolumeInSerie, totalLeakedFuelVolume);
            }
        }

        return tankMeasures;

    }

    // Tu powinno być jeszcze tankId
    // Co dorobić - jakiegoś helpera, który bierze np tankMeasure i odejmuje od jego fuelVolume wartość i ją ustawia w tym tankMeasure
    // Zmienić tak by funkcje nie generowały nowego zestawu danych, tylko modyfikowały już istniejący
    // Zmienić tak by funkcje nie przeglądały za każdym razem całego zestawu daych kilka razy, tylko np. wyslekecjonowac dane na których będziemy operować
    // np. na podstawie kryterium czasu
    private static Collection<TankMeasure> applyVolumeLeakageInTime(Collection<TankMeasure> tankMeasures, LocalDateTime startTime, LocalDateTime endTime,
            Integer tankId, Double leakedFuelVolume, Double totalLeakedFuelVolume) {

        ArrayList<TankMeasure> tankMeasuresInDateRange = new ArrayList<TankMeasure>();
        for (TankMeasure tankMeasure : tankMeasures) {
            if (TimeCalculator.isDateInRange(startTime, endTime, tankMeasure.getDateTime()) && tankMeasure.getTankId().equals(tankId)) {
                tankMeasuresInDateRange.add(tankMeasure);
            }
            if (tankMeasure.getDateTime().isAfter(endTime)) {
                tankMeasuresInDateRange.add(tankMeasure);
                break;
            }
        }

        // Tutaj Musi być dodana zmiana wyskokości paliwa
        if (!tankMeasuresInDateRange.isEmpty()) {
            FuelHeightVolumeMapper volumeToHeightMapper = FuelHeightVolumeMapperFactory.getVolumeToHeightMapper(tankId);
            System.out.println("Aplikuje wyciek");
            Double totalLeakageInSerie = 0.0;
            Double leakageVolumePerOneMeasurement = leakedFuelVolume / tankMeasuresInDateRange.size();
            for (TankMeasure tankMeasure : tankMeasuresInDateRange) {
                System.out.println("Przed " + tankMeasure);
                totalLeakageInSerie += leakageVolumePerOneMeasurement;
                tankMeasure.setFuelVolume(tankMeasure.getFuelVolume() - totalLeakageInSerie - totalLeakedFuelVolume);
                tankMeasure.setFuelHeight(volumeToHeightMapper.calculate(tankMeasure.getFuelVolume()));
                System.out.println("Po " + tankMeasure);
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
                } else if ((nozzleMeasure.getStatus() == 0) && !(refuelingStartNozzleMeasure.getTotalCounter().equals(nozzleMeasure.getTotalCounter()))) {
                    refuelingStartNozzleMeasure = nozzleMeasure;
                    nozzleMesuresRefuelingSeries.add(new ArrayList<NozzleMeasure>());
                    nozzleMesuresRefuelingSeries.get(nozzleMesuresRefuelingSeries.size() - 1).add(nozzleMeasure);
                } else if (nozzleMeasure.getStatus() == 0 && refuelingStartNozzleMeasure != null) {
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
