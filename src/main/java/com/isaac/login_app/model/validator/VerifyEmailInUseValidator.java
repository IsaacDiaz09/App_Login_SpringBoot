package com.isaac.login_app.model.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.isaac.login_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class VerifyEmailInUseValidator implements ConstraintValidator<VerifyEmailInUse, String> {

    @Autowired
    private UserRepository repo;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (Objects.isNull(repo.findByEmail(email))) {
            return true;
        }
        return false;
    }

}
