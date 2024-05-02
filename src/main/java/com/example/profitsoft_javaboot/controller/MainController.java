package com.example.profitsoft_javaboot.controller;

import com.example.profitsoft_javaboot.dto.HeroDetailDTO;
import com.example.profitsoft_javaboot.dto.HeroListDTO;
import com.example.profitsoft_javaboot.dto.HeroRequestList;
import com.example.profitsoft_javaboot.dto.HeroTeamDetailDTO;
import com.example.profitsoft_javaboot.model.HeroTeam;
import com.example.profitsoft_javaboot.service.HeroService;
import com.example.profitsoft_javaboot.service.HeroTeamService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
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
    public ResponseEntity<?> saveHero(@Valid @RequestBody HeroDetailDTO hero)
    {

        if(heroTeamService.getHeroTeam(hero.getHeroTeamId()) == null)
        {
            return new ResponseEntity<>("Hero team not found", HttpStatus.NOT_FOUND);
        }
        heroService.saveHero(hero);
        return new ResponseEntity<>(hero, HttpStatus.CREATED);
    }


    @PutMapping("/hero/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateHero(@PathVariable Long id,@Valid @RequestBody HeroDetailDTO hero)
    {
        if(id == null)
        {
            return new ResponseEntity<>("Hero id is mandatory", HttpStatus.BAD_REQUEST);
        }
        if(hero == null)
        {
            return new ResponseEntity<>("Hero details are mandatory", HttpStatus.BAD_REQUEST);
        }
        if (heroService.getHero(id) == null)
        {
            return new ResponseEntity<>("Hero not found", HttpStatus.NOT_FOUND);
        }
        if(heroTeamService.getHeroTeam(hero.getHeroTeamId()) == null)
        {
            return new ResponseEntity<>("Hero team not found", HttpStatus.NOT_FOUND);
        }

        HeroDetailDTO heroDetailDTO = heroService.updateHero(id, hero);

        return ResponseEntity.ok(heroDetailDTO);
    }

    @DeleteMapping("/hero/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteHero(@PathVariable Long id)
    {
        if(id == null)
        {
            return new ResponseEntity<>("Hero id is mandatory", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok( heroService.deleteHero(id));
    }

    @PostMapping("/hero/_list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HeroListDTO> getHeroList(@RequestBody HeroRequestList heroList)
    {

        HeroListDTO page = heroService.getHeroList(heroList);
        return ResponseEntity.ok(page);
    }

    @PostMapping(value = "/hero/_report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void generateReport(@RequestBody HeroRequestList heroRequestList, HttpServletResponse response)
    {
        heroService.generateReport(heroRequestList,response);
    }

    @PostMapping("/hero/upload")
    public ResponseEntity<Map<String, Object>> uploadFromFile(@Valid @RequestParam("file") MultipartFile file)
    {
        Map<String, Object> response = heroService.uploadFromFile(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/heroTeam")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HeroTeam>> getHeroTeam()
    {
        return ResponseEntity.ok(heroTeamService.getAllHeroTeams());
    }

    @PostMapping("/heroTeam")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveHeroTeam(@Valid @RequestBody HeroTeamDetailDTO heroTeam)
    {
        Optional<HeroTeam> existingHeroTeam = heroTeamService.findHeroTeamName(heroTeam.getHeroTeamName());

        if(existingHeroTeam.isPresent())
            return new ResponseEntity<>("HeroTeam name is not unique",HttpStatus.CONFLICT);

        heroTeamService.saveHeroTeam(heroTeam);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/heroTeam/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateHeroTeam(@PathVariable Long id, @RequestBody HeroTeamDetailDTO heroTeam)
    {
        Optional<HeroTeam> existingHeroTeam = heroTeamService.findHeroTeamName(heroTeam.getHeroTeamName());
        if(existingHeroTeam.isPresent())
            return new ResponseEntity<>("HeroTeam name is not unique",HttpStatus.CONFLICT);

        HeroTeamDetailDTO heroTeamDetailDTO = heroTeamService.updateHeroTeam(id, heroTeam);
        if(heroTeamDetailDTO == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(heroTeamDetailDTO);
    }
    @DeleteMapping("/heroTeam/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteHeroTeam(@PathVariable Long id)
    {
        if(id == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(heroTeamService.deleteHeroTeam(id));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleValidationException(
            MethodArgumentNotValidException exception
    )
    {
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = error.getObjectName();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        });
        return errors;
    }

}
