package nepsim.pojo;
import jakarta.validation.constraints.NotBlank;

public class SignupRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Citizenship number is required")
    private String citizenshipNumber;

    @NotBlank(message = "Password is required")
    private String password;
   private String fatherName;
    private String motherName;
    private String place;
    private String spouse;
    private String dateOfBirth;
    private String birthPlace;

    // Getters and setters
    public String getFirstName() { return firstName; }
    
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCitizenshipNumber() { return citizenshipNumber; }
    
    public void setCitizenshipNumber(String citizenshipNumber) { this.citizenshipNumber = citizenshipNumber; }

    public String getPassword() { return password; }
    
    public void setPassword(String password) { this.password = password; }

    public String getFatherName() { return fatherName; }
    
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getPlace() { return place; }
    
    public void setPlace(String place) { this.place = place; }

    public String getSpouse() { return spouse; }
    
    public void setSpouse(String spouse) { this.spouse = spouse; }

    public String getDateOfBirth() { return dateOfBirth; }
    
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getBirthPlace() { return birthPlace; }
    
    public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }
    
}

