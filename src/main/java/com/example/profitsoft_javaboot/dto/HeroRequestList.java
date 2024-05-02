package com.example.profitsoft_javaboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HeroRequestList
{
    private Long heroTeamId;
    private String heroTeamName;
    private String heroClassName;

    private int page;
    private int size;
}
