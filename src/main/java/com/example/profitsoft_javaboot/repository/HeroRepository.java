package com.example.profitsoft_javaboot.repository;

import com.example.profitsoft_javaboot.model.Hero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface HeroRepository extends JpaRepository<Hero, Long>
{
    Optional<Hero> findByHeroId(Long hero_id);
    @Query("SELECT h FROM Hero h WHERE h.heroTeam.heroTeamId = :heroTeamId")
    Page<Hero> findByHeroTeamId(@Param("heroTeamId") Long heroTeamId, Pageable pageable);
}
