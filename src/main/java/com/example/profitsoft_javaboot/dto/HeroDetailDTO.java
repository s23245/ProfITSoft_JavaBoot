package com.example.profitsoft_javaboot.dto;

import com.example.profitsoft_javaboot.enums.HeroElements;
import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroTeam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HeroDetailDTO
{
    private String heroClassName;
    private int heroLevel;
    private int manaAmount;
    private String abilities;
    private HeroElements heroMainElement;
    private Long heroTeamId;
    private HeroTeam heroTeam;

    public HeroDetailDTO()
    {

    }


    public HeroDetailDTO(Hero hero, Long heroTeamId)
    {
        this.heroClassName = hero.getHeroClassName();
        this.heroLevel = hero.getHeroLevel();
        this.manaAmount = hero.getManaAmount();
        this.abilities = hero.getAbilities();
        this.heroMainElement = hero.getHeroMainElement();
        this.heroTeamId = heroTeamId;
    }

    public HeroDetailDTO(Hero hero, HeroTeam heroTeam)
    {
        this.heroClassName = hero.getHeroClassName();
        this.heroLevel = hero.getHeroLevel();
        this.manaAmount = hero.getManaAmount();
        this.abilities = hero.getAbilities();
        this.heroMainElement = hero.getHeroMainElement();
        this.heroTeam = heroTeam;
    }

    public HeroDetailDTO(Hero hero)
    {
        this.heroClassName = hero.getHeroClassName();
        this.heroLevel = hero.getHeroLevel();
        this.manaAmount = hero.getManaAmount();
        this.abilities = hero.getAbilities();
        this.heroMainElement = hero.getHeroMainElement();
    }

    @Override
    public String toString() {
        return "HeroDetailDTO{" +
                "heroClassName='" + heroClassName + '\'' +
                ", heroLevel=" + heroLevel +
                ", manaAmount=" + manaAmount +
                ", abilities='" + abilities + '\'' +
                ", heroMainElement=" + heroMainElement +
                ", heroTeam=" + heroTeam +
                '}';
    }
}
