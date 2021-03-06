/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.service;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 *
 * @author Przemek
 */
public class TimeCalculator {

    /**
     * Calculates time in hours between given end and start date
     * @param start
     * @param end
     * @return 
     */
    public static Double durationInHours(LocalDateTime start, LocalDateTime end){
        return Duration.between(start,end).toMillis()/1000.0/60.0/60.0;
    }
    
    public static Double durationInMinutes(LocalDateTime start, LocalDateTime end){
        return Duration.between(start,end).toMillis()/1000.0/60.0;
    }
    
    public static Boolean isDateInRange(LocalDateTime start, LocalDateTime end, LocalDateTime date){
        return date.isEqual(start) || date.isEqual(end) || (date.isAfter(start) && date.isBefore(end));
    }

}
