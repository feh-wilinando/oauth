package br.com.caelum.oauth.commons.infra.validations;

import br.com.caelum.oauth.commons.infra.services.Reflections;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordAndRePasswordMatchingConstraint implements ConstraintValidator<PasswordAndRePasswordMatching, Object> {

    private String passwordFieldName;
    private String rePasswordFieldName;

    public void initialize(PasswordAndRePasswordMatching annotation) {
        passwordFieldName = annotation.passwordField();
        rePasswordFieldName = annotation.rePasswordField();
   }

   public boolean isValid(Object obj, ConstraintValidatorContext context) {

       Reflections reflections = new Reflections(obj);

       Object passwordValue = reflections.getFieldValueByName(passwordFieldName);
       Object rePasswordValue = reflections.getFieldValueByName(rePasswordFieldName);

       if (Objects.nonNull(passwordValue) && Objects.nonNull(rePasswordValue)) {
           return passwordValue.equals(rePasswordValue);
       }

       return false;
   }

}
