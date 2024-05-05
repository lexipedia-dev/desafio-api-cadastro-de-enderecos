package br.com.desafioapidecadastrodeenderecos.services;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressByUserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressReponseWithUserDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressResponseDto;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import br.com.desafioapidecadastrodeenderecos.mappers.AddressMapper;
import br.com.desafioapidecadastrodeenderecos.repositories.AddressRepository;
import br.com.desafioapidecadastrodeenderecos.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.desafioapidecadastrodeenderecos.mappers.AddressMapper.*;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<AddressEntity> createAddresses(List<AddressEntity> addressEntities) {
        return addressRepository.saveAll(addressEntities);
    }

    @Transactional
    public AddressResponseDto createAddress(Integer userId, AddressCreateRequestDto dto) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()) throw new EntityNotFoundException("user not found");
        AddressEntity addressToSave = addressCreateRequestToEntity(dto);
        addressToSave.setUser(userOptional.get());
        return addressEntityToResponse(addressRepository.save(addressToSave));
    }

    @Transactional
    public AddressResponseDto updateAddress(Integer addressId, Integer userId, AddressCreateRequestDto dto) {
        Optional<AddressEntity> addressOptional = addressRepository.findByAddressIdAndUserId(addressId, userId);
        if(addressOptional.isEmpty()) throw new EntityNotFoundException("address or user not found");
        AddressEntity addressToUpdate = updateAddressData(addressOptional.get(), dto);
         AddressEntity updatedAddress = addressRepository.save(addressToUpdate);
        return addressEntityToResponse(updatedAddress);
    }

    @Transactional
    public AddressReponseWithUserDto findById(Integer addressId){
        Optional<AddressEntity> addressOptional = addressRepository.findById(addressId);
        if(addressOptional.isEmpty()) throw new EntityNotFoundException("address or user not found");
        return addressEntityToResponseWithUser(addressOptional.get());
    }

    public List<AddressByUserResponseDto> findAllByUserId(Integer userId) {
        List<AddressEntity> addressesByUserId = addressRepository.findAllByUser(userId);
        return AddressMapper.entitiesToAddressesByUserDto(addressesByUserId);
    }
}
