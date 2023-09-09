package com.pinkward.bushgg.domain.match.common;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class TimeTranslator {
    public static String unixToLocal(long unixTimestamp) {
        // 현재 시간을 Unix 타임스탬프로 얻기
        long unixTimestampNow = Instant.now().toEpochMilli();
        long endTimestamp = unixTimestampNow - unixTimestamp;

        // 시간 차이를 "몇 시간 전", "몇 분 전", "몇 일 전" 등의 형식으로 변환
        String formattedTimeDifference = formatTimeDifference(endTimestamp);

        return formattedTimeDifference;
    }

    // 시간 차이를 포맷팅하는 함수
    public static String formatTimeDifference(long milliseconds) {
        long seconds = milliseconds / 1000;
        if (seconds < 60) {
            return seconds + "초 전";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            long remainder = seconds % 60;
            if (remainder < 30) {
                return minutes + "분 전"; // 30초 미만은 반올림하지 않음
            } else {
                return (minutes + 1) + "분 전"; // 30초 이상은 반올림
            }
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            long remainder = seconds % 3600;
            if (remainder < 1800) {
                return hours + "시간 전"; // 30분 미만은 반올림하지 않음
            } else {
                return (hours + 1) + "시간 전"; // 30분 이상은 반올림
            }
        } else {
            long days = seconds / 86400;
            long remainder = seconds % 86400;
            if (remainder < 43200) {
                return days + "일 전"; // 12시간 미만은 반올림하지 않음
            } else {
                return (days + 1) + "일 전"; // 12시간 이상은 반올림
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
