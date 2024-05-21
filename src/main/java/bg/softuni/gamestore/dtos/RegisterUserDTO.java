package bg.softuni.gamestore.dtos;

import bg.softuni.gamestore.exceptions.ValidationException;

public class RegisterUserDTO {
    private String email;
    private String password;
    private String confirmPassword;
    private String fullName;

    public RegisterUserDTO(String email, String password, String confirmPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void validate() {
        if (!this.password.equals(this.confirmPassword)) {
            throw new ValidationException("Password and Confirm Password fields do not match.");
        }

        if (!this.email.matches(".+@.+\\..+")) {
            throw new ValidationException("Please enter a valid email address!");
        }

        if (this.password.length() < 6) {
            throw new ValidationException("Password must be at least 6 symbols long, containing " +
                    "at least 1 uppercase, 1 lowercase and 1 digit");
        }

        if (!this.password.matches(".*[A-Z].*")) {
            throw new ValidationException("Password must contain at least 1 uppercase");
        }

        if (!this.password.matches(".*[a-z].*")) {
            throw new ValidationException("Password must contain at least 1 lowercase");
        }

        if (!this.password.matches(".*\\d.*")) {
            throw new ValidationException("Password must contain at least 1 digit");
        }
    }
}
