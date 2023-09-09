package com.pinkward.bushgg.domain.challenges.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThresholdsDTO {
    private double CHALLENGER;
    private double GRANDMASTER;
    private double SILVER;
    private double IRON;
    private double PLATINUM;
    private double BRONZE;
    private double MASTER;
    private double DIAMOND;
    private double GOLD;
}