package br.com.desafioapidecadastrodeenderecos.services;

import br.com.desafioapidecadastrodeenderecos.dtos.request.UserCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.request.UserUpdateRequestDto;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import br.com.desafioapidecadastrodeenderecos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.desafioapidecadastrodeenderecos.mappers.AddressMapper.addressesCreateRequestToEntities;
import static br.com.desafioapidecadastrodeenderecos.mappers.UserMapper.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto){
        UserEntity userCreated = userRepository.save(userCreateRequestToEntity(userCreateRequestDto));
        List<AddressEntity> addresses = addressesCreateRequestToEntities(userCreateRequestDto.addresses(), userCreated);
        userCreated.setAddresses(addressService.createAddresses(addresses));
        return userEntityToResponse(userCreated);
    }

    @Transactional
    public UserResponseDto updateUser(Integer userId, UserUpdateRequestDto dto) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new EntityNotFoundException("user not found");
        UserEntity userUpdated = userRepository.save(updateUserData(userOptional.get(), dto));
        return userEntityToResponse(userUpdated);
    }

    @Transactional
    public UserResponseDto findUserById(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new EntityNotFoundException("user not found");
        return userEntityToResponse(userOptional.get());
    }
}
