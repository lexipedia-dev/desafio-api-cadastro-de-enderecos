package br.com.desafioapidecadastrodeenderecos.entities;

import br.com.desafioapidecadastrodeenderecos.annotations.UniqueMainAdress;
import br.com.desafioapidecadastrodeenderecos.config.serialization.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime birthday;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @UniqueMainAdress
    private List<AddressEntity> addresses;

    public UserEntity() {
    }

    public UserEntity(String name, LocalDateTime birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public UserEntity(String name, LocalDateTime birthday, List<AddressEntity> addresses) {
        this.name = name;
        this.birthday = birthday;
        this.addresses = addresses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public List<AddressEntity> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressEntity> addresses) {
        this.addresses = addresses;
    }
}
