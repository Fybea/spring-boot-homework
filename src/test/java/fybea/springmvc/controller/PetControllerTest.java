package fybea.springmvc.controller;

import fybea.springmvc.model.PetDto;
import fybea.springmvc.model.UserDto;
import fybea.springmvc.service.PetService;
import fybea.springmvc.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreatePet() throws Exception {

        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);

        var pet = new PetDto(
                null,
                "pet",
                1L
        );
        String json = objectMapper.writeValueAsString(pet);

        String createdPetJson = mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petDto = objectMapper.readValue(createdPetJson, PetDto.class);

        Assertions.assertNotNull(petDto);
        Assertions.assertEquals(pet.getName(), petDto.getName());
    }

    @Test
    void shouldNotCreatePet() throws Exception {
        var pet = new PetDto(
                null,
                null,
                2L
        );
        String json = objectMapper.writeValueAsString(pet);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(400));
    }


    @Test
    void shouldSuccessSearchPetById() throws Exception {
        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);

        var pet = new PetDto(
                null,
                "pet",
                1L
        );

        pet = petService.save(pet);

        String foundPetJson = mockMvc.perform(get("/api/pets/{id}", pet.getId()))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petDto = objectMapper.readValue(foundPetJson, PetDto.class);

        org.assertj.core.api.Assertions.assertThat(pet)
                .usingRecursiveComparison()
                .isEqualTo(petDto);
    }

    @Test
    void shouldSuccessUpdatePet() throws Exception {
        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);


        var pet = new PetDto(
                1L,
                "pet",
                1L
        );

        var savedPet = petService.save(pet);

        String updatedPetName = "updatedPet";

        savedPet.setName(updatedPetName);

        String json = objectMapper.writeValueAsString(savedPet);

        String petJson = mockMvc.perform(put("/api/pets/{id}", savedPet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        PetDto petDto = objectMapper.readValue(petJson, PetDto.class);

        Assertions.assertEquals(updatedPetName, petDto.getName());
        Assertions.assertEquals(savedPet.getId(), petDto.getId());
    }


    @Test
    void shouldSuccessDeletePet() throws Exception {
        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);


        var pet = new PetDto(
                1L,
                "pet",
                1L
        );

        petService.save(pet);


        mockMvc.perform(delete("/api/pets/{id}", pet.getId())).andExpect(status().is(200));

        Assertions.assertFalse(user.getPets().contains(pet));
    }

}