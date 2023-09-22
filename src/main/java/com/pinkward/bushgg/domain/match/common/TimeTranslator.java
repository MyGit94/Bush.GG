package com.pinkward.bushgg.domain.match.common;

import lombok.extern.slf4j.Slf4j;
import java.time.Instant;

@Slf4j
public class TimeTranslator {
    public static String unixToLocal(long unixTimestamp) {

        long unixTimestampNow = Instant.now().toEpochMilli();
        long endTimestamp = unixTimestampNow - unixTimestamp;
        String formattedTimeDifference = formatTimeDifference(endTimestamp);

        return formattedTimeDifference;
    }

    public static String formatTimeDifference(long milliseconds) {
        long seconds = milliseconds / 1000;
        if (seconds < 60) {
            return seconds + "초 전";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            long remainder = seconds % 60;
            if (remainder < 30) {
                return minutes + "분 전";
            } else {
                return (minutes + 1) + "분 전";
            }
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            long remainder = seconds % 3600;
            if (remainder < 1800) {
                return hours + "시간 전";
            } else {
                return (hours + 1) + "시간 전";
            }
        } else {
            long days = seconds / 86400;
            long remainder = seconds % 86400;
            if (remainder < 43200) {
                return days + "일 전";
            } else {
                return (days + 1) + "일 전";
            }
        }
    }

    public static String unixMinAndSec(int unixTimestamp) {
        String minAndSec;
        int minutes = unixTimestamp / 60;
        int seconds = unixTimestamp % 60;
        minAndSec = minutes+"분 "+seconds+"초";
        return minAndSec;
    }
}
