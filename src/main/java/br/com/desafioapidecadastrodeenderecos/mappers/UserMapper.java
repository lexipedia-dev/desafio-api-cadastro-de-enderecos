package br.com.desafioapidecadastrodeenderecos.mappers;

import br.com.desafioapidecadastrodeenderecos.dtos.request.UserCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.request.UserUpdateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseWithNoAdressesDto;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;


public class UserMapper {
    public static UserEntity userCreateRequestToEntity(UserCreateRequestDto dto) {
      return new UserEntity(dto.name(),
              dto.birthday());
    }
    public static UserResponseDto userEntityToResponse(UserEntity entity) {
        return new UserResponseDto(entity.getName(), entity.getBirthday(), AddressMapper.addressesEntitiesToResponse(entity.getAddresses()));
    }

    public static UserEntity updateUserData(UserEntity userEntity, UserUpdateRequestDto dto) {
        userEntity.setName(dto.name());
        userEntity.setBirthday(dto.birthday());
        userEntity.setBirthday(dto.birthday());
        return userEntity;
    }

    public static UserResponseWithNoAdressesDto userEntityToResponseWithNoAddressDto(UserEntity user) {
        return new UserResponseWithNoAdressesDto(user.getName(),
                user.getBirthday());
    }

}
