package com.example.profitsoft_javaboot.service;

import com.example.profitsoft_javaboot.dto.HeroTeamDetailDTO;
import com.example.profitsoft_javaboot.model.HeroTeam;
import com.example.profitsoft_javaboot.repository.HeroTeamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroTeamService
{
    private final HeroTeamRepository heroTeamRepository;

    public HeroTeam getHeroTeam(Long id)
    {
        return heroTeamRepository.findByHeroTeamId(id).orElse(null);
    }

    public void saveHeroTeam(HeroTeamDetailDTO heroTeam)
    {
        HeroTeam heroTeamValue = new HeroTeam();
        heroTeamValue.setHeroTeamName(heroTeam.getHeroTeamName());
        heroTeamValue.setHeroTeamLevel(heroTeam.getHeroTeamLevel());
        heroTeamValue.setHeroTeamSize(heroTeam.getHeroTeamSize());
        heroTeamValue.setHeroes(heroTeam.getHeroes());
        heroTeamRepository.save(heroTeamValue);
    }
}
