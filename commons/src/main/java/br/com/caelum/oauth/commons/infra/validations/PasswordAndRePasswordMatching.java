package br.com.caelum.oauth.commons.infra.validations;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordAndRePasswordMatchingConstraint.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordAndRePasswordMatching {

    String message() default "Password and Re-Password not matching";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String passwordField() default "password";
    String rePasswordField() default "repassword";
}
