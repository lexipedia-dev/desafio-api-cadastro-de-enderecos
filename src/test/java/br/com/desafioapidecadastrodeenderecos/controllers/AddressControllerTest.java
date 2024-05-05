package br.com.desafioapidecadastrodeenderecos.controllers;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressByUserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressReponseWithUserDto;
import br.com.desafioapidecadastrodeenderecos.dummies.AddressDummies;
import br.com.desafioapidecadastrodeenderecos.services.AddressService;
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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AddressDummies addressDummies;
    @MockBean
    private AddressService addressService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("should return status code 201 (created) and create the address successfully")
    void createAddress_whenSendAValidrequest_shouldReturnCreatedAddressInformation() throws Exception {
        AddressCreateRequestDto validAddressCreateDto = addressDummies.createValidAddressCreateRequestDto();
        int userId = 1;
        when(addressService.createAddress(userId, validAddressCreateDto))
                .thenReturn(addressDummies.createDummyAddressResponseDto(validAddressCreateDto));

        mockMvc.perform(post("/address/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAddressCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street").value(validAddressCreateDto.street()))
                .andExpect(jsonPath("$.cep").value(validAddressCreateDto.cep()))
                .andExpect(jsonPath("$.streetNumber").value(validAddressCreateDto.streetNumber()))
                .andExpect(jsonPath("$.city").value(validAddressCreateDto.city()))
                .andExpect(jsonPath("$.state").value(validAddressCreateDto.state()))
                .andExpect(jsonPath("$.mainAddress").value(validAddressCreateDto.mainAddress()));
    }

    @Test
    @DisplayName("should return status code 404 (not found) when user does not exist")
    void createAddress_whenSendANotFoundUser_shouldReturnNotFound() throws Exception {
        AddressCreateRequestDto validAddressCreateDto = addressDummies.createValidAddressCreateRequestDto();
        int userId = 1;
        when(addressService.createAddress(userId, validAddressCreateDto))
                .thenThrow(new EntityNotFoundException("user not found"));

        mockMvc.perform(post("/address/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAddressCreateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return status code 400 (bad equest) when send a invalid request")
    void createAddress_whenSendAInvalidRequest_shouldReturnBadRequest() throws Exception {
        AddressCreateRequestDto validAddressCreateDto = addressDummies.createInvalidAddressCreateRequestDto();
        int userId = 1;

        mockMvc.perform(post("/address/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAddressCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return status code 200 (ok) and update the address successfully")
    void updateAddress_whenSendAValidrequest_shouldReturnUpdatedAddressInformation() throws Exception {
        AddressCreateRequestDto validAddressCreateDto = addressDummies.createValidAddressCreateRequestDto();
        int userId = 1;
        int addressId = 2;
        when(addressService.updateAddress(addressId, userId, validAddressCreateDto))
                .thenReturn(addressDummies.createDummyAddressResponseDto(validAddressCreateDto));

        mockMvc.perform(put("/address/{addressId}/{userId}", addressId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAddressCreateDto)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.street").value(validAddressCreateDto.street()))
                .andExpect(jsonPath("$.cep").value(validAddressCreateDto.cep()))
                .andExpect(jsonPath("$.streetNumber").value(validAddressCreateDto.streetNumber()))
                .andExpect(jsonPath("$.city").value(validAddressCreateDto.city()))
                .andExpect(jsonPath("$.state").value(validAddressCreateDto.state()))
                .andExpect(jsonPath("$.mainAddress").value(validAddressCreateDto.mainAddress()));
    }

    @Test
    @DisplayName("should return status code 404 (not found) when user does not exist")
    void updateAddress_whenSendANotFoundUser_shouldReturnNotFound() throws Exception {
        AddressCreateRequestDto validAddressCreateDto = addressDummies.createValidAddressCreateRequestDto();
        int addressId = 1;
        int userId = 2;
        when(addressService.updateAddress(addressId, userId, validAddressCreateDto))
                .thenThrow(new EntityNotFoundException("user not found"));

        mockMvc.perform(put("/address/{addressId}/{userId}", addressId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAddressCreateDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return status code 400 (bad equest) when send a invalid request")
    void updateAddress_whenSendAInvalidRequest_shouldReturnBadRequest() throws Exception {
        AddressCreateRequestDto validAddressCreateDto = addressDummies.createInvalidAddressCreateRequestDto();
        int addressId = 1;
        int userId = 2;
        mockMvc.perform(put("/address/{addressId}/{userId}", addressId, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAddressCreateDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return status code 200 (ok) and address information when address is found")
    void findAddres_whenAddressExist_shouldReturnAddressInformation() throws Exception {
        int addressId = 1;
        AddressReponseWithUserDto expectedAddressResponseWithUserDto = addressDummies.createADummyAddressResponseWithUser();
        when(addressService.findById(addressId)).thenReturn(expectedAddressResponseWithUserDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/address/{addressId}", addressId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.street").value(expectedAddressResponseWithUserDto.street()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cep").value(expectedAddressResponseWithUserDto.cep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.streetNumber").value(expectedAddressResponseWithUserDto.streetNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value(expectedAddressResponseWithUserDto.city()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.state").value(expectedAddressResponseWithUserDto.state()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mainAddress").value(expectedAddressResponseWithUserDto.mainAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.name").value(expectedAddressResponseWithUserDto.user().name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.birthday").value(expectedAddressResponseWithUserDto.user().birthday().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @Test
    @DisplayName("should return status code 404 (not found) when address does not exist")
    void findAddres_whenAddressDoesNotExist_shouldReturnNotFound() throws Exception {
        int addressId = 1;
        when(addressService.findById(addressId)).thenThrow(new EntityNotFoundException("address not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/address/{addressId}", addressId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("should return status code 200 (ok) and addresses information when user exist")
    void findAddreses_whenUserExist_shouldReturnAddressesInformation() throws Exception {
        int userId = 1;
        List<AddressByUserResponseDto> expectedAddressesByUserResponsesDto = addressDummies.createAListOfDummyAddressesByUserResponseDto();
        when(addressService.findAllByUserId(userId)).thenReturn(expectedAddressesByUserResponsesDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/address/find-all/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].street").value(expectedAddressesByUserResponsesDto.get(0).street()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cep").value(expectedAddressesByUserResponsesDto.get(0).cep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].streetNumber").value(expectedAddressesByUserResponsesDto.get(0).streetNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].city").value(expectedAddressesByUserResponsesDto.get(0).city()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].state").value(expectedAddressesByUserResponsesDto.get(0).state()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].mainAddress").value(expectedAddressesByUserResponsesDto.get(0).mainAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].street").value(expectedAddressesByUserResponsesDto.get(1).street()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].cep").value(expectedAddressesByUserResponsesDto.get(1).cep()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].streetNumber").value(expectedAddressesByUserResponsesDto.get(1).streetNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].city").value(expectedAddressesByUserResponsesDto.get(1).city()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].state").value(expectedAddressesByUserResponsesDto.get(1).state()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].mainAddress").value(expectedAddressesByUserResponsesDto.get(1).mainAddress()));
    }

    @Test
    @DisplayName("should return status code 404 (not found) when user does not exist")
    void findAddreses_whenUserDoesNotExist_shouldReturnNotFound() throws Exception {
        int userId = 1;
        when(addressService.findAllByUserId(userId)).thenThrow(new EntityNotFoundException("user not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/address/find-all/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
