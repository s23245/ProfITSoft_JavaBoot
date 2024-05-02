package com.example.profitsoft_javaboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hero_team")
public class HeroTeam
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heroTeamId;

    private String heroTeamName;
    private int heroTeamLevel;
    private int heroTeamSize;

    @OneToMany(mappedBy = "heroTeam",fetch = FetchType.EAGER)
    @JsonBackReference
    private Collection<Hero> heroes;
}
