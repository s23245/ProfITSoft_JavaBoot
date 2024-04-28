package com.example.profitsoft_javaboot.dto;

import com.example.profitsoft_javaboot.enums.HeroElements;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.profitsoft_javaboot.model.Hero}
 */
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Value
public class HeroListDetailsDTO
{
    Long heroId;
    String heroClassName;
    int heroLevel;
    int manaAmount;
    HeroElements heroMainElement;


}