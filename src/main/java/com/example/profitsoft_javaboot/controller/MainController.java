package com.example.profitsoft_javaboot.controller;

import com.example.profitsoft_javaboot.dto.HeroDetailDTO;
import com.example.profitsoft_javaboot.dto.HeroListDTO;
import com.example.profitsoft_javaboot.dto.HeroRequestList;
import com.example.profitsoft_javaboot.dto.HeroTeamDetailDTO;
import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroTeam;
import com.example.profitsoft_javaboot.service.HeroService;
import com.example.profitsoft_javaboot.service.HeroTeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController
{
    private final HeroService heroService;

    private final HeroTeamService heroTeamService;

    @GetMapping("/hero/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HeroDetailDTO> getHero(@PathVariable Long id)
    {
        HeroDetailDTO heroDetailDTO = heroService.getHero(id);
        if(heroDetailDTO == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(heroDetailDTO);
    }

    @PostMapping("/hero")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Hero> saveHero(@RequestBody HeroDetailDTO hero)
    {
        heroService.saveHero(hero);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/hero/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HeroDetailDTO> updateHero(@PathVariable Long id, @RequestBody HeroDetailDTO hero)
    {
        HeroDetailDTO heroDetailDTO = heroService.updateHero(id, hero);
        if(heroDetailDTO == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(heroDetailDTO);
    }

    @DeleteMapping("/hero/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteHero(@PathVariable Long id)
    {
        if(id == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( heroService.deleteHero(id));
    }

    @PostMapping("/hero/_list")
    public ResponseEntity<HeroListDTO> getHeroList(@RequestBody HeroRequestList heroList)
    {
        HeroListDTO page = heroService.getHeroList(heroList.getHeroTeamId(), heroList.getPage(), heroList.getSize());
        return ResponseEntity.ok(page);
    }

    @GetMapping("/heroTeam/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HeroTeam> getHeroTeam(@PathVariable Long id)
    {
        if(heroTeamService.getHeroTeam(id) == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(heroTeamService.getHeroTeam(id));
    }

    @PostMapping("/heroTeam")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HeroTeam> saveHeroTeam(@RequestBody HeroTeamDetailDTO heroTeam)
    {
        heroTeamService.saveHeroTeam(heroTeam);
        return ResponseEntity.ok().build();
    }

}
