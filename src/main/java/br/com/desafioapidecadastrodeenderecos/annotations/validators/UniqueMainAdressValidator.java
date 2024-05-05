package br.com.desafioapidecadastrodeenderecos.annotations.validators;

import br.com.desafioapidecadastrodeenderecos.annotations.UniqueMainAdress;
import br.com.desafioapidecadastrodeenderecos.entities.AddressEntity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class UniqueMainAdressValidator implements ConstraintValidator<UniqueMainAdress, List<AddressEntity>> {

    @Override
    public boolean isValid(List<AddressEntity> addresses, ConstraintValidatorContext context) {
        if (addresses == null) {
            return true;
        }

        long count = addresses.stream()
                .filter(AddressEntity::isMainAddress)
                .count();

        return count == 1;
    }
}
