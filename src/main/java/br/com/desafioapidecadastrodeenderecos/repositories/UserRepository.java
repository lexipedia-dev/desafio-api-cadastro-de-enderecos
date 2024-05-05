package br.com.desafioapidecadastrodeenderecos.repositories;

import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
