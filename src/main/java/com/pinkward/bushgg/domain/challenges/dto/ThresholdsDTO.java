package com.pinkward.bushgg.domain.challenges.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
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