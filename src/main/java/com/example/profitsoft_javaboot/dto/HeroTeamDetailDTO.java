package com.example.profitsoft_javaboot.dto;

import com.example.profitsoft_javaboot.model.HeroTeam;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeroTeamDetailDTO
{

    @NotBlank(message = "Hero team name is mandatory")
    private String heroTeamName;
    @Min(value = 1, message = "Hero team level must be at least 1")
    private int heroTeamLevel;
    @Min(value = 1, message = "Hero team size must be at least 1")
    private int heroTeamSize;

    public HeroTeamDetailDTO()
    {

    }

    public HeroTeamDetailDTO(String heroTeamName, int heroTeamLevel, int heroTeamSize)
    {
        this.heroTeamName = heroTeamName;
        this.heroTeamLevel = heroTeamLevel;
        this.heroTeamSize = heroTeamSize;
    }

    public HeroTeamDetailDTO(HeroTeam heroTeamValue)
    {
        this.heroTeamName = heroTeamValue.getHeroTeamName();
        this.heroTeamLevel = heroTeamValue.getHeroTeamLevel();
        this.heroTeamSize = heroTeamValue.getHeroTeamSize();
    }
}
