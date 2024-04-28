package com.example.profitsoft_javaboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeroListDTO
{
    private List<HeroListDetailsDTO> list;
    private int totalPages;
}
