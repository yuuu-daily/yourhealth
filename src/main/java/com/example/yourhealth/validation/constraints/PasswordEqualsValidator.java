package com.example.yourhealth.validation.constraints;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, Object> {

    private String field1;
    private String field2;
    private String message;

    @Override
    public void initialize(PasswordEquals annotation) {
        field1 = "password";
        field2 = "passwordConfirmation";
        message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        String field1Value = (String) beanWrapper.getPropertyValue(field1);
        String field2Value = (String) beanWrapper.getPropertyValue(field2);
        if ((field1Value.isEmpty() || field2Value.isEmpty()) || Objects.equals(field1Value, field2Value)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(field2).addConstraintViolation();
            return false;
        }
    }

}
