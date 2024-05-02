package com.example.profitsoft_javaboot.dto;

import com.example.profitsoft_javaboot.enums.HeroElements;
import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroTeam;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Setter
@Getter
public class HeroDetailDTO
{
    @NotBlank(message = "Hero class name is mandatory")
    private String heroClassName;
    @Min(value = 1, message = "Hero level must be at least 1")
    private int heroLevel;
    @Min(value = 1, message = "Mana amount must be at least 1")
    private int manaAmount;
    @Nullable
    private String abilities;
    @NotNull(message = "Hero main element is mandatory")
    private HeroElements heroMainElement;
    @NotNull(message = "Hero team id is mandatory")
    private Long heroTeamId;
    @Nullable
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

    public HeroDetailDTO(Hero heroValue, @Nullable HeroTeam heroTeam, Long heroTeamId)
    {
        this.heroClassName = heroValue.getHeroClassName();
        this.heroLevel = heroValue.getHeroLevel();
        this.manaAmount = heroValue.getManaAmount();
        this.abilities = heroValue.getAbilities();
        this.heroMainElement = heroValue.getHeroMainElement();
        this.heroTeam = heroTeam;
        this.heroTeamId = heroTeamId;
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
