package com.example.profitsoft_javaboot.service;

import com.example.profitsoft_javaboot.dto.HeroDetailDTO;
import com.example.profitsoft_javaboot.dto.HeroListDTO;
import com.example.profitsoft_javaboot.dto.HeroListDetailsDTO;
import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroTeam;
import com.example.profitsoft_javaboot.repository.HeroRepository;
import com.example.profitsoft_javaboot.repository.HeroTeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeroService
{
    private final HeroRepository heroRepository;
    private final HeroTeamRepository heroTeamRepository;

    public HeroListDTO getHeroList(Long heroTeamId, int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Hero> heroes = heroRepository.findByHeroTeamId(heroTeamId, pageable);
        List<HeroListDetailsDTO> heroListDetailsDTO = heroes.map(this::convertToDto).getContent();

        HeroListDTO heroListDTO = new HeroListDTO();
        heroListDTO.setList(heroListDetailsDTO);
        heroListDTO.setTotalPages(heroes.getTotalPages());

        return heroListDTO;

    }

    private HeroListDetailsDTO convertToDto(Hero hero)
    {
        HeroListDetailsDTO dto = new HeroListDetailsDTO(
                hero.getHeroId(),
                hero.getHeroClassName(),
                hero.getHeroLevel(),
                hero.getManaAmount(),
                hero.getHeroMainElement()
        );

        return dto;
    }

    public HeroDetailDTO getHero(Long id)
    {
       Hero hero = heroRepository.findByHeroId(id).orElse(null);
         if(hero == null)
         {
              return null;
         }
         HeroTeam heroTeam = heroTeamRepository.findByHeroTeamId(hero.getHeroTeam().getHeroTeamId()).orElse(null) ;
         if(heroTeam != null)
         {
             return new HeroDetailDTO(hero, heroTeam);

         }


        return new HeroDetailDTO(hero) ;

    }

    public void saveHero(HeroDetailDTO hero)
    {
        Hero heroValue = new Hero();
        heroValue.setHeroClassName(hero.getHeroClassName());
        heroValue.setHeroLevel(hero.getHeroLevel());
        heroValue.setManaAmount(hero.getManaAmount());
        heroValue.setAbilities(hero.getAbilities());
        heroValue.setHeroMainElement(hero.getHeroMainElement());


        heroValue.setHeroTeam(heroTeamRepository.findByHeroTeamId(hero.getHeroTeamId()).orElse(null));

        heroRepository.save(heroValue);
    }


    public HeroDetailDTO updateHero(Long id, HeroDetailDTO hero)
    {
        Hero heroValue = heroRepository.findByHeroId(id).orElse(null);

        if (heroValue == null)
        {
            return null;
        }

        heroValue.setHeroClassName(hero.getHeroClassName());
        heroValue.setHeroLevel(hero.getHeroLevel());
        heroValue.setManaAmount(hero.getManaAmount());
        heroValue.setAbilities(hero.getAbilities());
        heroValue.setHeroMainElement(hero.getHeroMainElement());
        heroValue.setHeroTeam(heroTeamRepository.findByHeroTeamId(hero.getHeroTeamId()).orElse(null));
        heroRepository.save(heroValue);
        HeroTeam heroTeam = heroValue.getHeroTeam();
        if(heroTeam != null)
        {
            return new HeroDetailDTO(heroValue, heroTeam);

        }


        return new HeroDetailDTO(heroValue);
    }

    public String deleteHero(Long id)
    {
        if(heroRepository.findByHeroId(id).orElse(null) == null)
        {
            return "Hero not found";
        }
        heroRepository.deleteById(id);
        return "Hero deleted";
    }
}
