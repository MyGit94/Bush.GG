package com.pinkward.bushgg.domain.match.common;

import lombok.extern.slf4j.Slf4j;
import java.time.Instant;

/**
 * 유닉스 시간을 로컬 시간으로 변환 하는 클래스
 */
@Slf4j
public class TimeTranslator {

    /**
     * 받아온 유닉스 시간이 로컬 시간으로부터 얼마나 지났는지 변환하는 메소드
     * @param unixTimestamp 유닉스 시간
     * @return 변환된 시간
     */
    public static String unixToLocal(long unixTimestamp) {

        long unixTimestampNow = Instant.now().toEpochMilli();
        long endTimestamp = unixTimestampNow - unixTimestamp;
        String formattedTimeDifference = formatTimeDifference(endTimestamp);

        return formattedTimeDifference;
    }

    /**
     * 유닉스 시간을 로컬 시간으로부터 얼마나 지났는지 변환하는 메소드
     * @param milliseconds
     * @return 변환된 시간
     */
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

    /**
     * 유닉스 시간을 로컬 시간으로 변환하는 메소드
     * @param unixTimestamp 유닉스 시간
     * @return 변환된 시간
     */
    public static String unixMinAndSec(long unixTimestamp) {
        String minAndSec;
        long minutes = unixTimestamp / 60;
        long seconds = unixTimestamp % 60;
        minAndSec = minutes+"분 "+seconds+"초";
        return minAndSec;
    }

    /**
     * 진행중인 실시간 게임 시간을 로컬 시간으로 변경하는 메소드
     * @param unixTimestamp 진행중인 실시간 게임의 유닉스 시간
     * @return
     */
    public static String currentGameTime(long unixTimestamp) {

        long unixTimestampNow = Instant.now().toEpochMilli();
        long endTimestamp = unixTimestampNow - unixTimestamp;
        endTimestamp = endTimestamp/ 1000;
        String formattedTimeDifference = unixMinAndSec(endTimestamp);

        return formattedTimeDifference;
    }
}
