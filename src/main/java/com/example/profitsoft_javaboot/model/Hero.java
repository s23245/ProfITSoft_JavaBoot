package com.example.profitsoft_javaboot.model;

import com.example.profitsoft_javaboot.enums.HeroElements;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hero
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long heroId;

    private String heroClassName;
    private int heroLevel;
    private int manaAmount;
    private String abilities;
    private HeroElements heroMainElement;

    @ManyToOne
    @JoinColumn(name = "heroes")
    @JsonBackReference
    private HeroTeam heroTeam;



    @Validated
    public void setHeroTeam(HeroTeam heroTeam)
    {
        this.heroTeam = heroTeam;
    }

    public void setHeroClassName(String heroClassName)
    {
        if(heroClassName == null || heroClassName.isEmpty())
        {
            throw new IllegalArgumentException("Hero class name cannot be null.");
        }
        if(heroClassName.length() < 3)
        {
            throw new IllegalArgumentException("Hero class name must be at least 3 characters long.");
        }

        this.heroClassName = heroClassName;
    }

    public void setHeroLevel(int heroLevel)
    {
        if(heroLevel < 1)
        {
            throw new IllegalArgumentException("Hero level must be at least 1.");
        }
        this.heroLevel = heroLevel;
    }

    public void setManaAmount(int manaAmount)
    {
        if(manaAmount < 0)
        {
            throw new IllegalArgumentException("Mana amount cannot be negative.");
        }
        this.manaAmount = manaAmount;
    }

    public void setAbilities(String abilities)
    {
        if(abilities == null || abilities.isEmpty())
        {
            throw new IllegalArgumentException("Abilities cannot be null or empty.");
        }
        if(abilities.length() < 10)
        {
            throw new IllegalArgumentException("Abilities must be at least 10 characters long.");
        }
        this.abilities = abilities;
    }

    public void setHeroMainElement(HeroElements heroMainElement)
    {
        if(heroMainElement == null)
        {
            throw new IllegalArgumentException("Hero main element cannot be null.");
        }
        this.heroMainElement = heroMainElement;
    }

    @Override
    public String toString()
    {
        return "{" +
                "heroClassName='" + heroClassName + '\'' +
                ", heroLevel=" + heroLevel +
                ", manaAmount=" + manaAmount +
                ", abilities='" + abilities + '\'' +
                ", heroMainElement=" + heroMainElement +
                '}' + '\n';
    }
}

