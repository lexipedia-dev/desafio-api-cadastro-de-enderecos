package br.com.desafioapidecadastrodeenderecos.controllers;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressByUserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressReponseWithUserDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.AddressResponseDto;
import br.com.desafioapidecadastrodeenderecos.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/{userId}")
    public ResponseEntity<AddressResponseDto> createAddress(@PathVariable Integer userId, @RequestBody @Valid AddressCreateRequestDto dto){
        AddressResponseDto addressResponse = addressService.createAddress(userId, dto);
        return ResponseEntity.created(URI.create("http://localhost:8080/address")).body(addressResponse);
    }

    @PutMapping("/{addressId}/{userId}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable Integer addressId,@PathVariable Integer userId, @RequestBody @Valid AddressCreateRequestDto dto){
        AddressResponseDto addressResponse = addressService.updateAddress(addressId, userId, dto);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressReponseWithUserDto> findAddress(@PathVariable Integer addressId){
        AddressReponseWithUserDto addressResponse = addressService.findById(addressId);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/find-all/{userId}")
    public ResponseEntity<List<AddressByUserResponseDto>> findAddressesByUserId(@PathVariable Integer userId){
        List<AddressByUserResponseDto> addressResponse = addressService.findAllByUserId(userId);
        return ResponseEntity.ok(addressResponse);
    }

}
