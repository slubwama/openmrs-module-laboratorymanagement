package org.openmrs.module.labmanagement.api.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TurnAroundTimeCalculator {
    public static long getTurnAroundTime(Date startDate, Date endDate) {
        if(endDate == null){
            endDate = new Date();
        }
        return endDate.getTime() - startDate.getTime();
    }

    public static String formatTurnAroundTime(BigDecimal timeDifference) {
        // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds = 86400000 milliseconds
        BigDecimal years = timeDifference.divide(BigDecimal.valueOf(365L * 86400000L), BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_FLOOR);
        timeDifference = timeDifference.subtract(years.multiply(BigDecimal.valueOf( 365L * 86400000L)));
        BigDecimal days = timeDifference.divide(BigDecimal.valueOf(86400000L), BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_FLOOR);
        timeDifference = timeDifference.subtract(days.multiply(BigDecimal.valueOf(86400000L)));
        BigDecimal hours = timeDifference.divide(BigDecimal.valueOf(3600000L), BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_FLOOR);
        timeDifference = timeDifference.subtract(hours.multiply(BigDecimal.valueOf(3600000L)));
        BigDecimal minutes = timeDifference.divide(BigDecimal.valueOf(60000L), BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_FLOOR);
        if (years.equals(BigDecimal.ZERO)  && days.equals(BigDecimal.ZERO) && hours.equals(BigDecimal.ZERO) && minutes.equals(BigDecimal.ZERO)) {
            return "0m";
        }
        return Stream.of(
                years.compareTo(BigDecimal.ZERO) > 0 ? years.toString()+"y" : null,
                days.compareTo(BigDecimal.ZERO) > 0 ? days.toString()+"d" : null,
                hours.compareTo(BigDecimal.ZERO) > 0 ? hours.toString()+"h" : null,
                minutes.compareTo(BigDecimal.ZERO) > 0 ? minutes.toString()+"m" : null
        ).filter(Objects::nonNull).collect(Collectors.joining(" "));
    }

    public static String formatTurnAroundTime(Long turnAroundTime) {
        // 24 hours * 60 minutes * 60 seconds * 1000 milliseconds = 86400000 milliseconds
        double timeDifference = Double.valueOf(turnAroundTime);
        double years = Math.floor((double) timeDifference / (365 * 86400000));
        timeDifference = timeDifference - (years * 365 * 86400000);
        double days = Math.floor(timeDifference / 86400000);
        timeDifference = timeDifference - days * 86400000;
        double hours = Math.floor(timeDifference / 3600000);
        timeDifference = timeDifference - hours * 3600000;
        double minutes = Math.floor(timeDifference / 60000);
        if (years == 0 && days == 0 && hours == 0 && minutes == 0) {
            return "0m";
        }
        return Stream.of(
        years > 0 ? String.format("%.0fy", years) : null,
                days > 0 ? String.format("%.0fd", days) : null,
                hours > 0 ? String.format("%.0fh", hours) : null,
                minutes > 0 ? String.format("%.0fm", minutes) : null
        ).filter(p-> p != null).collect(Collectors.joining(" "));
    }
}
