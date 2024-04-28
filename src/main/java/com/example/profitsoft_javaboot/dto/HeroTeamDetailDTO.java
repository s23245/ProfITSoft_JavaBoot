package com.example.profitsoft_javaboot.dto;

import com.example.profitsoft_javaboot.model.Hero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.sql.ast.tree.expression.Collation;

import java.util.Collection;

@Getter
@Setter
public class HeroTeamDetailDTO
{

    private String heroTeamName;
    private int heroTeamLevel;
    private int heroTeamSize;

    private Collection<Hero> heroes;

    public HeroTeamDetailDTO()
    {

    }

    public HeroTeamDetailDTO(String heroTeamName, int heroTeamLevel, int heroTeamSize, Collection<Hero> heroes)
    {
        this.heroTeamName = heroTeamName;
        this.heroTeamLevel = heroTeamLevel;
        this.heroTeamSize = heroTeamSize;
        this.heroes = heroes;
    }

}
