package nepsim.pojo;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Password is required")
    private String password;

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
