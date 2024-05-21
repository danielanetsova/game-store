package bg.softuni.gamestore.validators;

import bg.softuni.gamestore.customAnnotations.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int minLength;
    private boolean containsDigits;
    private boolean containsUpperCase;
    private boolean containsLowerCase;


    @Override
    public void initialize(Password constraintAnnotation) {
        this.minLength = constraintAnnotation.minLength();
        this.containsDigits = constraintAnnotation.containsDigits();
        this.containsUpperCase = constraintAnnotation.containsUpperCase();
        this.containsLowerCase = constraintAnnotation.containsLowerCase();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password.length() < minLength) {
            return false;
        }

        if (containsDigits && !password.matches(".*\\d.*")) {
            return false;
        }

        if (containsUpperCase && !password.matches(".*[A-Z].*")) {
            return false;
        }

        if (containsLowerCase && !password.matches(".*[a-z].*")) {
            return false;
        }

        return true;
    }
}
