package br.com.desafioapidecadastrodeenderecos.dummies;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressByUserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressReponseWithUserDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressResponseDto;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AddressDummies {
    @Autowired
    private UserDummies userDummies;
    public AddressCreateRequestDto createACompleteMainAddress(){
        return new AddressCreateRequestDto(
                "123 Magic Lane",
                "12345-678",
                "1A",
                "Fairyland",
                "FL",
                true);
    }

    public AddressCreateRequestDto createACompleteNotMainAddress(){
        return  new AddressCreateRequestDto(
                "456 Enchanted Forest",
                "98765-432",
                "2B",
                "Neverland",
                "NV",
                false
        );
    }

    public List<AddressEntity> createAListOfDummyAddressesEntitiesFromAListOfAddressesCreateRequest(List<AddressCreateRequestDto> addressesRequest) {
        List<AddressEntity> addressesEntities = new ArrayList<>();
        for(AddressCreateRequestDto createRequestDto: addressesRequest){
            addressesEntities.add(new AddressEntity(createRequestDto));
        }
        return addressesEntities;
    }

    public List<AddressResponseDto> createAListOfDummyAddressResponse() {
        return Stream.of(
                new AddressResponseDto("a dummy street",
                        "a dummy cep",
                        "a dummy street number",
                        "a dummy city",
                        "a dummy state",
                        true),
                new AddressResponseDto("another dummy street",
                        "another dummy cep",
                        "another dummy street number",
                        "another dummy city",
                        "another dummy state",
                        false)
        ).collect(Collectors.toList());
    }

    public AddressCreateRequestDto createValidAddressCreateRequestDto() {
        return new AddressCreateRequestDto("another dummy street",
                "another dummy cep",
                "another dummy street number",
                "another dummy city",
                "another dummy state",
                false);
    }

    public AddressResponseDto createDummyAddressResponseDto(AddressCreateRequestDto validAddressCreateDto) {
        return new AddressResponseDto("another dummy street",
                "another dummy cep",
                "another dummy street number",
                "another dummy city",
                "another dummy state",
                false);
    }

    public AddressCreateRequestDto createInvalidAddressCreateRequestDto() {
        return new AddressCreateRequestDto("",
                "another dummy cep",
                "another dummy street number",
                "another dummy city",
                "another dummy state",
                false);
    }

    public AddressReponseWithUserDto createADummyAddressResponseWithUser() {
        return new AddressReponseWithUserDto("a dummy street",
                "another dummy cep",
                "another dummy street number",
                "another dummy city",
                "another dummy state",
                false,
                userDummies.createADummyUserResponseWithNoAddressDto());
    }

    public List<AddressByUserResponseDto> createAListOfDummyAddressesByUserResponseDto() {
        return Stream.of(
                new AddressByUserResponseDto("a dummy street",
                        "a dummy cep",
                        "a dummy street number",
                        "a dummy city",
                        "a dummy state",
                        true),
                new AddressByUserResponseDto("another dummy street",
                        "another dummy cep",
                        "another dummy street number",
                        "another dummy city",
                        "another dummy state",
                        true)
        ).collect(Collectors.toList());
    }

    public List<AddressEntity> createAListWithMultipleMainAddressesEntities(UserEntity entity) {
        return Stream.of(new AddressEntity("a dummy street",
                "a dummy cep",
                "a dummy street number",
                "a dummy city",
                "a dummy state",
                true,
                entity),
                new AddressEntity("a dummy street",
                        "a dummy cep",
                        "a dummy street number",
                        "a dummy city",
                        "a dummy state",
                        true,
                        entity)).collect(Collectors.toList());
    }

    public List<AddressEntity> createAValidDummyListOfAddressEntities() {
        return Stream.of(new AddressEntity("a dummy street",
                        "a dummy cep",
                        "a dummy street number",
                        "a dummy city",
                        "a dummy state",
                        true),
                new AddressEntity("another dummy street",
                        "another dummy cep",
                        "another dummy street number",
                        "another dummy city",
                        "another dummy state",
                        true)).collect(Collectors.toList());
    }

    public AddressEntity createAInvalidAddressEntity() {
        return new AddressEntity(null,null,null,null,null,null,null);
    }

    public AddressEntity createAValidAddressEntity() {
    return new AddressEntity("a dummy street",
            "a dummy cep",
            "a dummy street number",
            "a dummy city",
            "a dummy state",
            true);
    }
}
