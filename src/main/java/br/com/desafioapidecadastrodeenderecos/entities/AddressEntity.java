package br.com.desafioapidecadastrodeenderecos.entities;

import br.com.desafioapidecadastrodeenderecos.dtos.request.AddressCreateRequestDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty
    private String street;
    @NotEmpty
    private String cep;
    @NotEmpty
    private String streetNumber;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotNull
    private Boolean mainAddress;
    @ManyToOne
    @JsonBackReference
    private UserEntity user;

    public AddressEntity(AddressCreateRequestDto addressCreateRequestDto) {
        this.street = addressCreateRequestDto.street();
        this.cep = addressCreateRequestDto.cep();
        this.streetNumber = addressCreateRequestDto.streetNumber();
        this.city = addressCreateRequestDto.city();
        this.state = addressCreateRequestDto.state();
        this.mainAddress = addressCreateRequestDto.mainAddress();
    }

    public AddressEntity(String street, String cep, String streetNumber, String city, String state, Boolean mainAddress) {
        this.street = street;
        this.cep = cep;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.mainAddress = mainAddress;
    }

    public AddressEntity(String street, String cep, String streetNumber, String city, String state, Boolean mainAddress, UserEntity user) {
        this.street = street;
        this.cep = cep;
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.mainAddress = mainAddress;
        this.user = user;
    }

    public AddressEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean isMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
