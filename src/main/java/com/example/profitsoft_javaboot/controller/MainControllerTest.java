package com.example.profitsoft_javaboot.controller;

import com.example.profitsoft_javaboot.dto.HeroDetailDTO;
import com.example.profitsoft_javaboot.dto.HeroListDTO;
import com.example.profitsoft_javaboot.dto.HeroRequestList;
import com.example.profitsoft_javaboot.dto.HeroTeamDetailDTO;
import com.example.profitsoft_javaboot.service.HeroService;
import com.example.profitsoft_javaboot.service.HeroTeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.profitsoft_javaboot.enums.HeroElements.FIRE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest
class MainControllerTest
{

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private MainController mainController;
    @MockBean
    private HeroService heroService;
    @MockBean
    private HeroTeamService heroTeamService;

    @BeforeEach
    public void setUp()
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetHero() throws Exception {
        mockMvc.perform(get("/api/hero/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteHero() throws Exception
    {
        mockMvc.perform(get("/api/hero/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveHero() throws Exception
    {
        HeroDetailDTO hero = new HeroDetailDTO();

        hero.setHeroClassName("Warrior");
        hero.setHeroLevel(1);
        hero.setManaAmount(100);
        hero.setHeroMainElement(FIRE);
        hero.setHeroTeamId(1L);

        doNothing().when(heroService).saveHero(any(HeroDetailDTO.class));

        mockMvc.perform(post("/api/hero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hero)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateHero() throws Exception
    {
        HeroDetailDTO hero = new HeroDetailDTO();

        hero.setHeroClassName("Warrior");
        hero.setHeroLevel(1);
        hero.setManaAmount(100);
        hero.setHeroMainElement(FIRE);
        hero.setHeroTeamId(1L);

        when(heroService.updateHero(anyLong(),any(HeroDetailDTO.class))).thenReturn(hero);

        mockMvc.perform(put("/api/hero/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hero)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetHeroList() throws Exception
    {
        HeroRequestList heroList = new HeroRequestList();
        HeroListDTO heroListDTO = new HeroListDTO();
        when(heroService.getHeroList(any(HeroRequestList.class))).thenReturn(heroListDTO);

        mockMvc.perform(post("/api/hero/_list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroList)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadFromFile() throws Exception
    {
        MockMultipartFile file = new MockMultipartFile("file",
                "test.json", "application/json",
                            "{\"name\":\"test\"}".getBytes());
        mockMvc.perform(multipart("/api/hero/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testGenerateReport() throws Exception
    {
        HeroRequestList heroRequestList = new HeroRequestList();
        doNothing().when(heroService).generateReport(any(HeroRequestList.class),any());

        mockMvc.perform(post("/api/hero/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(heroRequestList)))
                .andExpect(status().isCreated());
    }
    @Test
    public void testGetHeroTeams() throws Exception
    {
        mockMvc.perform(get("/api/heroTeam"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveHeroTeam() throws Exception
    {
        HeroTeamDetailDTO heroTeam = new HeroTeamDetailDTO();
        heroTeam.setHeroTeamName("Team1");
        heroTeam.setHeroTeamLevel(1);
        heroTeam.setHeroTeamSize(5);

        when(heroTeamService.findHeroTeamName(any(String.class))).thenReturn(null);
        doNothing().when(heroTeamService).saveHeroTeam(any(HeroTeamDetailDTO.class));
        mockMvc.perform(post("/api/heroTeam")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(heroTeam)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateHeroTeam() throws Exception
    {
        mockMvc.perform(put("/api/heroTeam/{id}",1)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(new HeroTeamDetailDTO())))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteHeroTeam() throws Exception
    {
        mockMvc.perform(delete("/api/heroTeam/{id}",1))
                .andExpect(status().isOk());
    }
}
