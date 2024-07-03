package com.example.profitsoft_javaboot.service;

import com.example.profitsoft_javaboot.dto.HeroDetailDTO;
import com.example.profitsoft_javaboot.dto.HeroListDTO;
import com.example.profitsoft_javaboot.dto.HeroListDetailsDTO;
import com.example.profitsoft_javaboot.dto.HeroRequestList;
import com.example.profitsoft_javaboot.model.Hero;
import com.example.profitsoft_javaboot.model.HeroTeam;
import com.example.profitsoft_javaboot.repository.HeroRepository;
import com.example.profitsoft_javaboot.repository.HeroTeamRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service("heroService")
@RequiredArgsConstructor
public class HeroService
{

    private final HeroRepository heroRepository;
    private final HeroTeamRepository heroTeamRepository;
    private final Validator validator;

    public HeroListDTO getHeroList(HeroRequestList requestList)
    {
       Pageable pageable = PageRequest.of(requestList.getPage(), requestList.getSize());

        Specification<Hero> spec = Specification.where(null);

        if(requestList.getHeroTeamId() != null)
        {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("heroTeam").get("heroTeamId"), requestList.getHeroTeamId()));
        }
        if(requestList.getHeroTeamName() != null)
        {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("heroTeam").get("heroTeamName"), requestList.getHeroTeamName()));
        }
        if(requestList.getHeroClassName() != null)
        {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("heroClassName"), requestList.getHeroClassName()));
        }

        Page<Hero> heroes = heroRepository.findAll(spec, pageable);
        List<HeroListDetailsDTO> heroList = heroes.map(this::convertToDto).getContent();

        HeroListDTO heroListDTO = new HeroListDTO();
        heroListDTO.setList(heroList);
        heroListDTO.setTotalPages(heroes.getTotalPages());

        return heroListDTO;
    }

    private HeroListDetailsDTO convertToDto(Hero hero)
    {
        return new HeroListDetailsDTO(
                hero.getHeroId(),
                hero.getHeroClassName(),
                hero.getHeroLevel(),
                hero.getManaAmount(),
                hero.getHeroMainElement()
        );
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
             if(Objects.equals(heroTeam.getHeroTeamId(), hero.getHeroTeam().getHeroTeamId()))
                 return new HeroDetailDTO(hero, heroTeam, hero.getHeroTeam().getHeroTeamId());

             return new HeroDetailDTO(hero, heroTeam);
         }


        return new HeroDetailDTO(hero);

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
            if(Objects.equals(heroTeam.getHeroTeamId(), hero.getHeroTeamId()))
               return new HeroDetailDTO(heroValue,heroTeam, hero.getHeroTeamId());

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

    public void generateReport(HeroRequestList heroList, HttpServletResponse response)
    {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=hero_report.csv");
        try {
            StringBuilder sb = new StringBuilder();
            Specification<Hero> spec = Specification.where(null);

            if(heroList.getHeroTeamId() != null)
            {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("heroTeam").get("heroTeamId"), heroList.getHeroTeamId()));
            }
            if(heroList.getHeroTeamName() != null)
            {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("heroTeam").get("heroTeamName"), heroList.getHeroTeamName()));
            }
            if(heroList.getHeroClassName() != null)
            {
                spec = spec.and((root, query, cb) -> cb.equal(root.get("heroClassName"), heroList.getHeroClassName()));
            }

            List<Hero> heroes = heroRepository.findAll(spec);
            List<HeroListDetailsDTO> resultList = heroes.stream().map(this::convertToDto).toList();

            sb.append("Hero ID,Hero Class Name,Hero Level,Mana Amount,Hero Main Element\n");
            for(HeroListDetailsDTO hero : resultList)
            {
                sb.append(hero.getHeroId()).append(",")
                        .append(hero.getHeroClassName()).append(",")
                        .append(hero.getHeroLevel()).append(",")
                        .append(hero.getManaAmount()).append(",")
                        .append(hero.getHeroMainElement()).append("\n");
            }
            response.getOutputStream().write(sb.toString().getBytes());
            response.getOutputStream().flush();

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    public Map<String, Object> uploadFromFile(MultipartFile file)
    {
        Map<String, Object> response = new HashMap<>();
        try
        {
            Map<String, Object> result = processFile(file);
            response.put("success", result.get("success"));
            response.put("failed", result.get("fail"));
        }
        catch (Exception e)
        {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
            Integer failCount = (Integer) response.getOrDefault("failed", 0);
            response.put("failed", failCount + 1);
        }
        return response;
    }

    private Map<String, Object> processFile(MultipartFile file)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        int successCount = 0;
        int failCount = 0;

        try
        {
            ObjectReader reader = objectMapper.readerFor(Hero.class);
            MappingIterator<Hero> iterator = reader.readValues(file.getInputStream());

            while (iterator.hasNextValue())
            {
                try
                {
                    Hero hero = iterator.nextValue();

                    Set<ConstraintViolation<Hero>> violations = validator.validate(hero);
                    if (!violations.isEmpty())
                    {
                        failCount++;
                        continue;
                    }

                    Optional<Hero> existingHero = heroRepository.findByHeroId(hero.getHeroId());
                    if(existingHero.isEmpty())
                    {
                        Optional<HeroTeam> existingHeroTeam = heroTeamRepository.findById(hero.getHeroTeam().getHeroTeamId());
                        if(existingHeroTeam.isEmpty())
                        {
                            failCount++;
                        }
                        else
                        {
                            heroRepository.save(hero);
                            successCount++;
                        }
                    }
                    else
                    {
                        failCount++;
                    }
                }
                catch (Exception e)
                {
                    System.err.println("An error occurred while processing a record: " + e.getMessage());
                    failCount++;
                }
            }
        }
        catch (IOException e)
        {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
            failCount++;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success", successCount);
        result.put("fail", failCount);
        return result;
    }

}
