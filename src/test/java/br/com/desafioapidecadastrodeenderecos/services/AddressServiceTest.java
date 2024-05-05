package br.com.desafioapidecadastrodeenderecos.services;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressByUserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressReponseWithUserDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressResponseDto;
import br.com.desafioapidecadastrodeenderecos.dummies.AddressDummies;
import br.com.desafioapidecadastrodeenderecos.dummies.UserDummies;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import br.com.desafioapidecadastrodeenderecos.mappers.AddressMapper;
import br.com.desafioapidecadastrodeenderecos.repositories.AddressRepository;
import br.com.desafioapidecadastrodeenderecos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class AddressServiceTest {
    @Autowired
    private UserDummies userDummies;
    @Autowired
    private AddressDummies addressDummies;
    @Autowired
    private AddressService addressService;
    @MockBean
    private AddressRepository addressRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("should create a list of addressess successfully")
    void createAddresses_whenSendAValidRequest_shouldReturnAResponse() {
        List<AddressEntity> listOfAddresses = addressDummies.createAValidDummyListOfAddressEntities();
        listOfAddresses.get(0).setId(1);
        listOfAddresses.get(1).setId(2);
        when(addressRepository.saveAll(listOfAddresses)).thenReturn(listOfAddresses);
        addressService.createAddresses(listOfAddresses);
        verify(addressRepository, times(1)).saveAll(anyList());
    }

    @Test
    @DisplayName("should create a address successfully")
    void createAddress_whenSendAValidRequest_shouldReturnAResponse() {
        int userId = 1;
        AddressCreateRequestDto validAddressCreateRequestDto = addressDummies.createValidAddressCreateRequestDto();
        AddressEntity addressEntity = AddressMapper.addressCreateRequestToEntity(validAddressCreateRequestDto);
        addressEntity.setId(1);
        UserEntity dummyUser = userDummies.createADummyUserEntity();

        when(userRepository.findById(anyInt())).thenReturn(Optional.ofNullable(dummyUser));
        when(addressRepository.save(any())).thenReturn(addressEntity);

        addressService.createAddress(userId, validAddressCreateRequestDto);

        verify(userRepository, times(1)).findById(userId);
        verify(addressRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when send a user that does not exist")
    void createAddress_whenUserDoesNotExist_shouldThrowEntityNotFoundException() {
        int userId = 1;
        AddressCreateRequestDto validAddressCreateRequestDto = addressDummies.createValidAddressCreateRequestDto();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            addressService.createAddress(userId, validAddressCreateRequestDto);
        });
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("should update address successfully")
    void updateAddress_whenSendAValidRequest_shouldReturnAResponse() {
        int userId = 1;
        int addressId = 1;
        AddressEntity beforeUpdateAddressEntity = addressDummies.createAValidAddressEntity();
        AddressCreateRequestDto updatedAddressRequest = new AddressCreateRequestDto("altered street",
                "altered cep",
                "altered street number",
                "altered city",
                "altered state",
                false
        );
        when(addressRepository.findByAddressIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.ofNullable(beforeUpdateAddressEntity));
        when(addressRepository.save(any())).thenReturn(AddressMapper.addressCreateRequestToEntity(updatedAddressRequest));

        assertNotEquals(beforeUpdateAddressEntity.getStreet(), updatedAddressRequest.street());
        assertNotEquals(beforeUpdateAddressEntity.getCep(), updatedAddressRequest.cep());
        assertNotEquals(beforeUpdateAddressEntity.getStreetNumber(), updatedAddressRequest.streetNumber());
        assertNotEquals(beforeUpdateAddressEntity.getCity(), updatedAddressRequest.city());
        assertNotEquals(beforeUpdateAddressEntity.getState(), updatedAddressRequest.state());
        assertNotEquals(beforeUpdateAddressEntity.isMainAddress(), updatedAddressRequest.mainAddress());

        AddressResponseDto addressResponseDto = addressService.updateAddress(addressId, userId, updatedAddressRequest);

        assertEquals(beforeUpdateAddressEntity.getStreet(), addressResponseDto.street());
        assertEquals(beforeUpdateAddressEntity.getCep(), addressResponseDto.cep());
        assertEquals(beforeUpdateAddressEntity.getStreetNumber(), addressResponseDto.streetNumber());
        assertEquals(beforeUpdateAddressEntity.getCity(), addressResponseDto.city());
        assertEquals(beforeUpdateAddressEntity.getState(), addressResponseDto.state());
        assertEquals(beforeUpdateAddressEntity.isMainAddress(), addressResponseDto.mainAddress());

        verify(addressRepository, times(1)).findByAddressIdAndUserId(any(), any());
        verify(addressRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("should throw EntityNotFoundException when send a address or a user that does not exist")
    void updateAddress_whenAddressOrUserDoesNotExist_shouldThrowEntityNotFoundException() {
        int userId = 1;
        int addressId = 2;
        AddressCreateRequestDto validAddressCreateRequestDto = addressDummies.createValidAddressCreateRequestDto();
        when(addressRepository.findByAddressIdAndUserId(addressId, userId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            addressService.updateAddress(addressId, userId, validAddressCreateRequestDto);
        });
        verify(addressRepository, times(1)).findByAddressIdAndUserId(addressId, userId);
    }

    @Test
    @DisplayName("should find a address successfully")
    void findById_whenAddressExist_shouldReturnResponseData() {
        int addressId = 1;
        AddressEntity validAddressEntity = addressDummies.createAValidAddressEntity();
        validAddressEntity.setId(1);
        validAddressEntity.setUser(userDummies.createADummyUserEntity());
        when(addressRepository.findById(addressId)).thenReturn(Optional.ofNullable(validAddressEntity));
        AddressReponseWithUserDto response = addressService.findById(addressId);
        verify(addressRepository, times(1)).findById(addressId);
        assertEquals(validAddressEntity.getStreet(), response.street());
        assertEquals(validAddressEntity.getCep(), response.cep());
        assertEquals(validAddressEntity.getStreetNumber(), response.streetNumber());
        assertEquals(validAddressEntity.getCity(), response.city());
        assertEquals(validAddressEntity.getState(), response.state());
        assertEquals(validAddressEntity.isMainAddress(), response.mainAddress());
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when send a address that dont exist")
    void findById_whenAddressDontExist_shouldReturnEntityNotFoundException() {
        int addressId = 1;
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->{
            addressService.findById(addressId);
        });
        verify(addressRepository, times(1)).findById(addressId);
    }

    @Test
    @DisplayName("should find all user's address successfully")
    void findAllByUserId(){
        int userId=1;
        List<AddressEntity> dummiesAddresses = addressDummies.createAValidDummyListOfAddressEntities();
        when(addressRepository.findAllByUser(any())).thenReturn(dummiesAddresses);
        List<AddressByUserResponseDto> addresses = addressService.findAllByUserId(userId);
        verify(addressRepository, times(1)).findAllByUser(userId);
        IntStream.range(0, dummiesAddresses.size()).forEach(i -> {
            AddressEntity entityAddress = dummiesAddresses.get(i);
            AddressByUserResponseDto responseAddress = addresses.get(i);
            assertEquals(entityAddress.getStreet(), responseAddress.street());
            assertEquals(entityAddress.getCep(), responseAddress.cep());
            assertEquals(entityAddress.getStreetNumber(), responseAddress.streetNumber());
            assertEquals(entityAddress.getCity(), responseAddress.city());
            assertEquals(entityAddress.getState(), responseAddress.state());
            assertEquals(entityAddress.isMainAddress(), responseAddress.mainAddress());
        });
    }

}
