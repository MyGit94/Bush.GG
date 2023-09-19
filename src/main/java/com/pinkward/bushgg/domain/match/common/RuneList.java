package com.pinkward.bushgg.domain.match.common;

import lombok.Getter;

import java.util.List;


public class RuneList {
    private static final int[] precisionRune = {8000, 8005, 8008, 8021, 8010, 9101, 9111, 8009, 9104, 9105, 9103, 8014, 8017, 8299};
    private static final int[] dominationRune = {8100, 8112, 8124, 8128, 9923, 8126, 8139, 8143, 8136, 8120, 8138, 8135, 8134, 8105, 8106};
    private static final int[] sorceryRune = {8200, 8214, 8229, 8230, 8224, 8226, 8275, 8210, 8234, 8233, 8237, 8232, 8236};
    private static final int[] resolveRune ={8400, 8437, 8439, 8465, 8446, 8463, 8401, 8429, 8444, 8473, 8451, 8453, 8242};
    private static final int[] inspirationRune = {8300, 8351, 8360, 8369, 8306, 8304, 8313, 8321, 8316, 8345, 8347, 8410, 8352};
    private static final int[] statPerksDefense = {5001, 5002, 5003};
    private static final int[] statPerksFlex = {5008, 5002, 5003};
    private static final int[] statPerksOffense = {5008, 5005, 5007};

    public static int[] getStatPerksDefense() {
        return statPerksDefense;
    }

    public static int[] getStatPerksFlex() {
        return statPerksFlex;
    }

    public static int[] getStatPerksOffense() {
        return statPerksOffense;
    }

    // 서브룬 만들기
    public static int[] getSubRuneArray(int[] inputArray) {
        int length = inputArray.length;

        // 배열의 길이에 따라 시작 인덱스 선택
        int startIndex = (length == 13) ? 4 : 5;

        // 시작 인덱스부터 끝까지 새로운 배열로 복사
        int subArrayLength = length - startIndex;
        int[] subArray = new int[subArrayLength];

        System.arraycopy(inputArray, startIndex, subArray, 0, subArrayLength);

        return subArray;
    }

    // 메인룬 가져오기
    public static int[] getRuneListByValue(int value) {
        int[] selectedRune;

        switch (value) {
            case 8000:
                selectedRune = precisionRune;
                break;
            case 8100:
                selectedRune = dominationRune;
                break;
            case 8200:
                selectedRune = sorceryRune;
                break;
            case 8300:
                selectedRune = inspirationRune;
                break;
            case 8400:
                selectedRune = resolveRune;
                break;
            default:
                // 다른 값에 대한 처리
                selectedRune = null;
                break;
        }

        return selectedRune;
    }

}
