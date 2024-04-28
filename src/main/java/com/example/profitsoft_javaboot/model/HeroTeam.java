package com.example.profitsoft_javaboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeroTeam
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long heroTeamId;

    private String heroTeamName;
    private int heroTeamLevel;
    private int heroTeamSize;

    @OneToMany(mappedBy = "heroTeam",cascade = CascadeType.ALL)
    @JsonBackReference
    private Collection<Hero> heroes;
}
