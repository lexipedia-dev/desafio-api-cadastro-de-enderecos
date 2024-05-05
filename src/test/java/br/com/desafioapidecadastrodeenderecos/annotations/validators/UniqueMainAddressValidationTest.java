package br.com.desafioapidecadastrodeenderecos.annotations.validators;

import br.com.desafioapidecadastrodeenderecos.dummies.UserDummies;
import br.com.desafioapidecadastrodeenderecos.entities.UserEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class UniqueMainAddressValidationTest {
    private Validator validator;
    @Autowired
    private UserDummies userDummies;

    @Test
    public void testingAnnotatino(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        UserEntity aDummyCreateUserRequestWithMultipleMainAddresses = userDummies.createADummyCreateUserRequestWithMultipleMainAddresses();
        Set<ConstraintViolation<UserEntity>> violations = validator.validate(aDummyCreateUserRequestWithMultipleMainAddresses);
        assertFalse(violations.isEmpty());
    }

}