package fybea.springmvc.controller;

import fybea.springmvc.model.PetDto;
import fybea.springmvc.model.UserDto;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreateUser() throws Exception {

        var user = new UserDto(
                null,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        String json = objectMapper.writeValueAsString(user);

        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userResponse = objectMapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(user.getName(), userResponse.getName());
    }

    @Test
    void shouldNotCreateUser() throws Exception {
        var user = new UserDto(
                null,
                null,
                "test@mail.com",
                10,
                new ArrayList<>()
        );
        String json = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(400));
    }

    @Test
    void shouldSuccessSearchUserById() throws Exception {
        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);

        user = userService.save(user);

        String foundUserJson = mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDto = objectMapper.readValue(foundUserJson, UserDto.class);

        org.assertj.core.api.Assertions.assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(userDto);
    }

    @Test
    void shouldSuccessUpdateUser() throws Exception {
        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);


        var savedUser = userService.save(user);

        String updatedUsername = "updatedUsername";

        savedUser.setName(updatedUsername);

        String json = objectMapper.writeValueAsString(savedUser);

        String userJson = mockMvc.perform(put("/api/users/{id}", savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDto = objectMapper.readValue(userJson, UserDto.class);

        Assertions.assertEquals(updatedUsername, userDto.getName());
        Assertions.assertEquals(savedUser.getId(), userDto.getId());
    }


    @Test
    void shouldSuccessDeleteUser() throws Exception {
        var user = new UserDto(
                1L,
                "some-name",
                "test@mail.com",
                10,
                new ArrayList<>()
        );

        userService.save(user);

        mockMvc.perform(delete("/api/users/{id}", user.getId())).andExpect(status().is(200));
    }
}