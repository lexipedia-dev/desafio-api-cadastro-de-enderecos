package br.com.desafioapidecadastrodeenderecos.services;

import br.com.desafioapidecadastrodeenderecos.dtos.request.UserCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.request.UserUpdateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dummies.AddressDummies;
import br.com.desafioapidecadastrodeenderecos.dummies.UserDummies;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import br.com.desafioapidecadastrodeenderecos.mappers.UserMapper;
import br.com.desafioapidecadastrodeenderecos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDummies userDummies;
    @Autowired
    private AddressDummies addressDummies;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AddressService addressService;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("should create user successfully")
    void createUser_whenSendAValidRequest_shouldReturnAResponse() {
        UserCreateRequestDto userCreateRequestDto = userDummies.createADummyValidCreateUserRequestDto();
        UserEntity savedUser = UserMapper.userCreateRequestToEntity(userCreateRequestDto);
        savedUser.setId(1);

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);
        List<AddressEntity> expectedAddresses = addressDummies.createAListOfDummyAddressesEntitiesFromAListOfAddressesCreateRequest(userCreateRequestDto.addresses());

        when(addressService.createAddresses(anyList())).thenReturn(expectedAddresses);
        UserResponseDto responseDto = userService.createUser(userCreateRequestDto);

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(addressService, times(1)).createAddresses(anyList());

        assertEquals(savedUser.getId(), 1);
        assertEquals(savedUser.getBirthday(), userCreateRequestDto.birthday());
        IntStream.range(0, expectedAddresses.size()).forEach(i -> {
            AddressEntity expectedAddress = expectedAddresses.get(i);
            AddressResponseDto actualAddress = responseDto.addresses().get(i);
            assertEquals(expectedAddress.getStreet(), actualAddress.street());
            assertEquals(expectedAddress.getCep(), actualAddress.cep());
            assertEquals(expectedAddress.getStreetNumber(), actualAddress.streetNumber());
            assertEquals(expectedAddress.getCity(), actualAddress.city());
            assertEquals(expectedAddress.getState(), actualAddress.state());
            assertEquals(expectedAddress.isMainAddress(), actualAddress.mainAddress());
        });
    }

    @Test
    @DisplayName("should update user successfully")
    void updateUser_whenSendAValidRequest_shouldReturnAResponse() {
        int userId = 1;
        UserEntity beforeUpdateUserEntity = userDummies.createADummyUserEntity();
        beforeUpdateUserEntity.setAddresses(addressDummies.createAValidDummyListOfAddressEntities());
        UserUpdateRequestDto updatedUserRequest = new UserUpdateRequestDto("another dummy name", LocalDateTime.parse("1991-05-12T08:00:00"));
        UserEntity updatedUserEntity = new UserEntity("another dummy name", LocalDateTime.parse("1991-05-12T08:00:00"), addressDummies.createAValidDummyListOfAddressEntities());
        when(userRepository.findById(userId)).thenReturn(Optional.of(beforeUpdateUserEntity));
        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        assertNotEquals(beforeUpdateUserEntity.getName(), updatedUserRequest.name());
        assertNotEquals(beforeUpdateUserEntity.getBirthday(), updatedUserRequest.birthday());

        UserResponseDto userResponseDto = userService.updateUser(userId, updatedUserRequest);

        assertEquals(beforeUpdateUserEntity.getName(), userResponseDto.name());
        assertEquals(beforeUpdateUserEntity.getBirthday(), userResponseDto.birthday());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when send a user that does not exist")
    void updateUser_whenUserDoesExist_shouldThrowEntitiyNotFoundException() {
        UserUpdateRequestDto userUpdateRequest = userDummies.createADummyUserUpdateDto();
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            userService.updateUser(userId, userUpdateRequest);
        });
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("should find a user succesfully")
    void findUserById_whenUserExist_shouldReturnAResponse(){
        int userId = 1;
        UserEntity userEntity = userDummies.createADummyUserEntity();
        List<AddressEntity> addressesEntities = addressDummies.createAValidDummyListOfAddressEntities();
        userEntity.setAddresses(addressesEntities);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        UserResponseDto userById = userService.findUserById(userId);
        assertEquals(userById.name(), userEntity.getName());
        assertEquals(userById.birthday(), userEntity.getBirthday());
        verify(userRepository, times(1)).findById(userId);

        IntStream.range(0, userEntity.getAddresses().size()).forEach(i -> {
            AddressEntity expectedAddress = userEntity.getAddresses().get(i);
            AddressResponseDto actualAddress = userById.addresses().get(i);
            assertEquals(expectedAddress.getStreet(), actualAddress.street());
            assertEquals(expectedAddress.getCep(), actualAddress.cep());
            assertEquals(expectedAddress.getStreetNumber(), actualAddress.streetNumber());
            assertEquals(expectedAddress.getCity(), actualAddress.city());
            assertEquals(expectedAddress.getState(), actualAddress.state());
            assertEquals(expectedAddress.isMainAddress(), actualAddress.mainAddress());
        });
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when send a user that does not exist")
    void findUserById_whenUserDoesNotExist_shouldThrowEntitiyNotFoundException() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            userService.findUserById(userId);
        });
        verify(userRepository, times(1)).findById(userId);
    }
}
