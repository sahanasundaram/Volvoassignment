
package main.congestion.calculator;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class CongestionTaxCalculator {

    private static final int MAX_DAILY_TAX = 60;
    private static final Map<String, Integer> TOLL_FREE_VEHICLES = new HashMap<>();

    static {
        TOLL_FREE_VEHICLES.put("Motorcycle", 0);
        TOLL_FREE_VEHICLES.put("Tractor", 0);
        TOLL_FREE_VEHICLES.put("Emergency", 0);
        TOLL_FREE_VEHICLES.put("Diplomat", 0);
        TOLL_FREE_VEHICLES.put("Foreign", 0);
        TOLL_FREE_VEHICLES.put("Military", 0);
    }

    public int calculateTax(Vehicle vehicle, LocalDateTime[] timestamps) {
        if (vehicle == null || timestamps == null || timestamps.length == 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        if (isTollFreeVehicle(vehicle)) return 0;

        int totalFee = 0;
        LocalDateTime intervalStart = timestamps[0];
        int maxFeeInInterval = 0;

        for (LocalDateTime timestamp : timestamps) {
            int fee = getTollFee(timestamp);
            Duration duration = Duration.between(intervalStart, timestamp);
            long minutes = duration.toMinutes();

            if (minutes <= 60) {
                maxFeeInInterval = Math.max(maxFeeInInterval, fee);
            } else {
                totalFee += maxFeeInInterval;
                intervalStart = timestamp;
                maxFeeInInterval = fee;
            }
        }
        totalFee += maxFeeInInterval;

        return Math.min(totalFee, MAX_DAILY_TAX);
    }

    private boolean isTollFreeVehicle(Vehicle vehicle) {
        return vehicle != null && TOLL_FREE_VEHICLES.containsKey(vehicle.getVehicleType());
    }

    private int getTollFee(LocalDateTime dateTime) {
        if (isTollFreeDate(dateTime)) return 0;

        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();

        if (hour == 6 && minute >= 0 && minute <= 29) return 8;
        if (hour == 6 && minute >= 30 && minute <= 59) return 13;
        if (hour == 7) return 18;
        if (hour == 8 && minute >= 0 && minute <= 29) return 13;
        if (hour >= 8 && hour <= 14) return 8;
        if (hour == 15 && minute >= 0 && minute <= 29) return 13;
        if (hour == 15 || hour == 16) return 18;
        if (hour == 17) return 13;
        if (hour == 18 && minute >= 0 && minute <= 29) return 8;

        return 0;
    }

    private boolean isTollFreeDate(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        Month month = dateTime.getMonth();
        int dayOfMonth = dateTime.getDayOfMonth();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) return true;
        if (month == Month.JULY) return true;

        if (dateTime.getYear() == 2013) {
            return switch (month) {
                case JANUARY -> dayOfMonth == 1;
                case MARCH -> dayOfMonth == 28 || dayOfMonth == 29;
                case APRIL -> dayOfMonth == 1 || dayOfMonth == 30;
                case MAY -> dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9;
                case JUNE -> dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21;
                case NOVEMBER -> dayOfMonth == 1;
                case DECEMBER -> dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31;
                default -> false;
            };
        }
        return false;
    }
}
