package com.example.profitsoft_javaboot.service;

import com.example.profitsoft_javaboot.dto.HeroTeamDetailDTO;
import com.example.profitsoft_javaboot.model.HeroTeam;
import com.example.profitsoft_javaboot.repository.HeroTeamRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("heroTeamService")
@RequiredArgsConstructor
public class HeroTeamService
{
    private final HeroTeamRepository heroTeamRepository;

    public List<HeroTeam> getAllHeroTeams()
    {
        return heroTeamRepository.findAll();
    }

    public HeroTeamDetailDTO getHeroTeam(Long id)
    {
        HeroTeam heroTeamValue = heroTeamRepository.findByHeroTeamId(id).orElse(null);
        if(heroTeamValue == null)
        {
            return null;
        }
        return new HeroTeamDetailDTO(heroTeamValue);
    }

    public void saveHeroTeam(HeroTeamDetailDTO heroTeam)
    {
        HeroTeam heroTeamValue = new HeroTeam();
        heroTeamValue.setHeroTeamName(heroTeam.getHeroTeamName());
        heroTeamValue.setHeroTeamLevel(heroTeam.getHeroTeamLevel());
        heroTeamValue.setHeroTeamSize(heroTeam.getHeroTeamSize());
        heroTeamRepository.save(heroTeamValue);
    }

    public String deleteHeroTeam(Long id)
    {
        if(heroTeamRepository.findByHeroTeamId(id).orElse(null) == null)
        {
            return "Hero not found";
        }
        heroTeamRepository.deleteById(id);
        return "Hero deleted";
    }

    public HeroTeamDetailDTO updateHeroTeam(Long id, HeroTeamDetailDTO heroTeam)
    {
        HeroTeam heroTeamValue = heroTeamRepository.findByHeroTeamId(id).orElse(null);

        if (heroTeamValue == null)
        {
            return null;
        }

        heroTeamValue.setHeroTeamName(heroTeam.getHeroTeamName());
        heroTeamValue.setHeroTeamLevel(heroTeam.getHeroTeamLevel());
        heroTeamValue.setHeroTeamSize(heroTeam.getHeroTeamSize());


        heroTeamRepository.save(heroTeamValue);


        return new HeroTeamDetailDTO(heroTeamValue);

    }


    public Optional<HeroTeam> findHeroTeamName(String heroTeamName)
    {
        return heroTeamRepository.findByHeroTeamName(heroTeamName);
    }
}
