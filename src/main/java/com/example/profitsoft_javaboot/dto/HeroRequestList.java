package com.example.profitsoft_javaboot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HeroRequestList
{
    private Long heroTeamId;
    private String heroTeamName;
    private int page;
    private int size;
}
