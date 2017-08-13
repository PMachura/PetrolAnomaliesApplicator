/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package petrolanomaliesapplicator.helpers;

import java.time.Duration;
import java.time.LocalDateTime;


/**
 *
 * @author Przemek
 */
public class TimeCalculator {

    public static Double durationInHours(LocalDateTime start, LocalDateTime end){
        return Duration.between(start,end).toMillis()/1000.0/60.0/60.0;
    }

}
