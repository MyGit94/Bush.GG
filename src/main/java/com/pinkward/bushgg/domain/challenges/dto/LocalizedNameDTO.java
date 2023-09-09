package com.pinkward.bushgg.domain.challenges.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class LocalizedNameDTO {
    private String description;
    private String name;
    private String shortDescription;
}

