package nepsim.pojo;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Citizenship number is required")
    private String citizenshipNumber;

    @NotBlank(message = "Password is required")
    private String password;

    public String getCitizenshipNumber() { return citizenshipNumber; }
    public void setCitizenshipNumber(String citizenshipNumber) { this.citizenshipNumber = citizenshipNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
