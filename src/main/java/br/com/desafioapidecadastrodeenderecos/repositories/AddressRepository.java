package br.com.desafioapidecadastrodeenderecos.repositories;

import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    @Query(nativeQuery = true,
            value = "select tb_address.* from tb_address  \n" +
                    "inner join tb_user\n" +
                    "on tb_user.id = tb_address.user_id\n" +
                    "where tb_user.id=:userId and tb_address.id=:addressId")
    Optional<AddressEntity> findByAddressIdAndUserId(Integer addressId, Integer userId);

    @Query(nativeQuery = true,
            value = "select * from tb_address " +
                    "where user_id = :userId")
    List<AddressEntity> findAllByUser(Integer userId);

}
