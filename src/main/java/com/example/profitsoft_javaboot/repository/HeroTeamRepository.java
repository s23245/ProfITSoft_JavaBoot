package com.example.profitsoft_javaboot.repository;

import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface HeroTeamRepository extends JpaRepository<HeroTeam, Long>
{
    Optional<HeroTeam> findByHeroTeamId(Long heroTeamId);

    List<HeroTeam> findByHeroesContains(Hero hero);
}
