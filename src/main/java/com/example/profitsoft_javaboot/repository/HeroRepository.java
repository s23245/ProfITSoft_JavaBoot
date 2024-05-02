package com.example.profitsoft_javaboot.repository;

import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface HeroRepository extends JpaRepository<Hero, Long>, JpaSpecificationExecutor<Hero>
{
    Optional<Hero> findByHeroId(Long hero_id);

    /*@Query("SELECT h FROM Hero h WHERE h.heroTeam.heroTeamId = :heroTeamId")
    Page<Hero> findByHeroTeamId(@Param("heroTeamId") Long heroTeamId, Pageable pageable);
*/

    default Page<Hero> findByHeroTeamId(Long heroTeamId, Pageable pageable) {
        return findAll(HeroSpecification.hasHeroTeamId(heroTeamId), pageable);
    }

    default Page<Hero> findByHeroClassName(String heroClassName, Pageable pageable) {
        return findAll(HeroSpecification.hasHeroClassName(heroClassName), pageable);
    }

    default Page<Hero> findByHeroTeamName(String heroTeamName, Pageable pageable) {
        return findAll(HeroSpecification.hasHeroTeamName(heroTeamName), pageable);
    }
}
