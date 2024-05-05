package br.com.desafioapidecadastrodeenderecos.controllers;

import br.com.desafioapidecadastrodeenderecos.dtos.request.UserCreateRequestDto;
import br.com.desafioapidecadastrodeenderecos.dtos.response.UserResponseDto;
import br.com.desafioapidecadastrodeenderecos.dtos.request.UserUpdateRequestDto;
import br.com.desafioapidecadastrodeenderecos.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    private ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateRequestDto dto){
        UserResponseDto userResponse = userService.createUser(dto);
        return ResponseEntity.created(URI.create("http://localhost:8080/user")).body(userResponse);
    }

    @PutMapping("/{userId}")
    private ResponseEntity<UserResponseDto> updateUser(@PathVariable Integer userId, @RequestBody @Valid UserUpdateRequestDto dto){
        UserResponseDto userResponse = userService.updateUser(userId, dto);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    private ResponseEntity<UserResponseDto> findUser(@PathVariable Integer userId){
        UserResponseDto userResponse = userService.findUserById(userId);
        return ResponseEntity.ok(userResponse);
    }
}
