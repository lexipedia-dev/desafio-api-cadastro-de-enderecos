package br.com.desafioapidecadastrodeenderecos.dummies;

import br.com.desafioapidecadastrodeenderecos.dtos.request.UserCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.request.UserUpdateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseWithNoAdressesDto;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import br.com.desafioapidecadastrodeenderecos.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserDummies {
    @Autowired
    private AddressDummies addressDummies;

    public UserCreateRequestDto createADummyValidCreateUserRequestDto(){
        return new UserCreateRequestDto(
                "Alice Wonderland",
                LocalDateTime.parse("1999-08-10T08:00:00"),
                List.of(addressDummies.createACompleteMainAddress(),
                        addressDummies.createACompleteNotMainAddress())
        );
    }

    public UserCreateRequestDto createAInvalidDummyCreateUserRequestDto(){
        return new UserCreateRequestDto(
                "",
                LocalDateTime.parse("1999-08-10T08:00:00"),
                List.of(addressDummies.createACompleteMainAddress(),
                        addressDummies.createACompleteNotMainAddress())
        );
    }

    public UserResponseDto createADummyUserResponseFromADummyCreateUserRequest(UserCreateRequestDto userCreateRequestDto) {
        UserEntity dummyUserEntity = new UserEntity();
        dummyUserEntity.setId(1);
        dummyUserEntity.setName(userCreateRequestDto.name());
        dummyUserEntity.setBirthday(userCreateRequestDto.birthday());
        dummyUserEntity.setAddresses(addressDummies.createAListOfDummyAddressesEntitiesFromAListOfAddressesCreateRequest(userCreateRequestDto.addresses()));
        return UserMapper.userEntityToResponse(dummyUserEntity);
    }

    public UserResponseDto createADummyUserResponseDto() {
        return new UserResponseDto("dummy user to create", LocalDateTime.parse("1999-08-10T08:00:00"), addressDummies.createAListOfDummyAddressResponse());
    }

    public UserUpdateRequestDto createADummyUserUpdateDto(){
        return new UserUpdateRequestDto("dummy user to update", LocalDateTime.parse("1999-08-10T08:00:00"));

    }


    public UserUpdateRequestDto createAInvalidDummyUserUpdateDto(){
        return new UserUpdateRequestDto("", LocalDateTime.parse("1999-08-10T08:00:00"));

    }

    public UserResponseWithNoAdressesDto createADummyUserResponseWithNoAddressDto() {
        return new UserResponseWithNoAdressesDto("a dummy name for a dummy address",
                LocalDateTime.parse("1999-08-10T08:00:00"));
    }

    public UserEntity createADummyCreateUserRequestWithMultipleMainAddresses() {
        UserEntity entity = new UserEntity("Alice Wonderland",
                LocalDateTime.parse("1999-08-10T08:00:00"));
        entity.setAddresses(addressDummies.createAListWithMultipleMainAddressesEntities(entity));
        return entity;
    }

    public UserEntity createADummyEntityFromADummyUpdateRequest(UserUpdateRequestDto userUpdateRequest) {
        return new UserEntity(userUpdateRequest.name(), userUpdateRequest.birthday());
    }

    public UserEntity createADummyUserEntity() {
        return new UserEntity("a dummy name", LocalDateTime.parse("1999-08-10T08:00:00"));
    }

    public UserCreateRequestDto createAInvalidCreateUserRequestDto(){
        return new UserCreateRequestDto("", null, null);
    }
}
