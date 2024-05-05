package br.com.desafioapidecadastrodeenderecos.mappers;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressByUserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressReponseWithUserDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressResponseDto;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

import static br.com.desafioapidecadastrodeenderecos.mappers.UserMapper.userEntityToResponseWithNoAddressDto;

public class AddressMapper {

    public static AddressEntity addressCreateRequestToEntity (AddressCreateRequestDto addressCreateRequestDto){
        return new AddressEntity(addressCreateRequestDto);
    }

    public static List<AddressEntity> addressesCreateRequestToEntities(List<AddressCreateRequestDto> dtos, UserEntity user){
        List<AddressEntity> entities = new ArrayList<>();
        for (AddressCreateRequestDto dto: dtos) {
            entities.add(new AddressEntity(dto.street(),
                    dto.cep(),
                    dto.streetNumber(),
                    dto.city(),
                    dto.state(),
                    dto.mainAddress(),
                    user));
        }
        return entities;
    }

    public static List<AddressResponseDto> addressesEntitiesToResponse(List<AddressEntity> addresses) {
        List<AddressResponseDto> responses = new ArrayList<>();
        for (AddressEntity address: addresses){
            responses.add(new AddressResponseDto(address.getStreet(),
                    address.getCep(),
                    address.getStreetNumber(),
                    address.getCity(),
                    address.getState(),
                    address.isMainAddress()));
        }
        return responses;
    }

    public static AddressResponseDto addressEntityToResponse(AddressEntity userSaved) {
        AddressResponseDto addressResponseDto = new AddressResponseDto(userSaved.getStreet(),
                userSaved.getCep(),
                userSaved.getStreetNumber(),
                userSaved.getCity(),
                userSaved.getState(),
                userSaved.isMainAddress());
        return addressResponseDto;
    }

    public static AddressEntity updateAddressData(AddressEntity addressEntity, AddressCreateRequestDto dto) {
        addressEntity.setStreet(dto.street());
        addressEntity.setCep(dto.cep());
        addressEntity.setStreetNumber(dto.streetNumber());
        addressEntity.setCity(dto.city());
        addressEntity.setState(dto.state());
        addressEntity.setMainAddress(dto.mainAddress());
        return addressEntity;
    }

    public static AddressReponseWithUserDto addressEntityToResponseWithUser(AddressEntity addressEntity) {
        AddressReponseWithUserDto addressResponseDto = new AddressReponseWithUserDto(addressEntity.getStreet(),
                addressEntity.getCep(),
                addressEntity.getStreetNumber(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.isMainAddress(),
                userEntityToResponseWithNoAddressDto(addressEntity.getUser()));
        return addressResponseDto;
    }

    public static List<AddressByUserResponseDto> entitiesToAddressesByUserDto(List<AddressEntity> addressesEntities) {
        List<AddressByUserResponseDto> response = new ArrayList<>();
        for(AddressEntity address: addressesEntities){
            response.add(new AddressByUserResponseDto(address.getStreet(),
                    address.getCep(),
                    address.getStreetNumber(),
                    address.getCity(),
                    address.getState(),
                    address.isMainAddress()));
        }
        return response;
    }

}
