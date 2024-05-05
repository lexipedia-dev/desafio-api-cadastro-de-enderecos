package br.com.desafioapidecadastrodeenderecos.controllers;

import br.com.desafioapidecadastrodeenderecos.dtos.request.UserCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.request.UserUpdateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseDto;
import br.com.desafioapidecadastrodeenderecos.services.UserService;
import br.com.desafioapidecadastrodeenderecos.dummies.UserDummies;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDummies userDummies;
    @MockBean
    private UserService userService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("should return status code 201 (created) and create the user successfully")
    void createUser_whenSendAValidRequest_shouldReturnCreatedUserInformation() throws Exception {
        UserCreateRequestDto validUserCreateDto = userDummies.createADummyValidCreateUserRequestDto();
        when(userService.createUser(any(UserCreateRequestDto.class))).thenReturn(userDummies.createADummyUserResponseFromADummyCreateUserRequest(validUserCreateDto));
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(validUserCreateDto.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value(validUserCreateDto.birthday().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @Test
    @DisplayName("should return status code 400 (bad request) when send a invalid request")
    void createUser_whenSendAnInvalidRequest_shouldReturnBadRequest() throws Exception {
        UserCreateRequestDto validUserCreateDto = userDummies.createAInvalidDummyCreateUserRequestDto();
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("should return status code 200 (ok) and update successfully a user")
    void updateUser_whenSendAValidRequest_shoudReturnUserUpdatedInformation() throws Exception {
        UserCreateRequestDto validUserCreateDto = userDummies.createADummyValidCreateUserRequestDto();
        when(userService.updateUser(any(Integer.class), any(UserUpdateRequestDto.class))).thenReturn(userDummies.createADummyUserResponseFromADummyCreateUserRequest(validUserCreateDto));
        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserCreateDto)))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(validUserCreateDto.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value(validUserCreateDto.birthday().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }


    @Test
    @DisplayName("Should return status code 404 (not found) when user does not exist")
    void updateUser_whenUserIsNotFound_shouldReturnNotFound() throws Exception {
        UserUpdateRequestDto validUserUpdateDto = userDummies.createADummyUserUpdateDto();
        when(userService.updateUser(2, validUserUpdateDto)).thenThrow(new EntityNotFoundException("user not found"));
        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Should return status code 400 (bad request) when request body is invalid")
    void updateUser_whenRequestIsInvalid_shouldReturnBadRequest() throws Exception {
        UserUpdateRequestDto validUserUpdateDto = userDummies.createAInvalidDummyUserUpdateDto();
        mockMvc.perform(MockMvcRequestBuilders.put("/user/" + 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("should return status code 200 (ok) and user information when user is found")
    void findUser_whenUserExist_shouldReturnUserInformation() throws Exception {
        int userId = 1;
        UserResponseDto expectedUserResponseDto = userDummies.createADummyUserResponseDto();
        when(userService.findUserById(userId)).thenReturn(expectedUserResponseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedUserResponseDto.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthday").value(expectedUserResponseDto.birthday().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @Test
    @DisplayName("should return status code 404 (not found) when user does not exist")
    void findUser_whenUserDoesNotExist_shouldReturnNotFound() throws Exception {
        int userId = 1;
        when(userService.findUserById(userId)).thenThrow(new EntityNotFoundException("user not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
