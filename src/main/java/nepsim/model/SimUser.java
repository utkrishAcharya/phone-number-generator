package nepsim.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "simusers")
public class SimUser {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String fatherName;
    private String motherName;
    private String place;
    private String spouse;
    private String citizenshipNumber;

    private String simNumber;    // Generated like SIM-XXXX
    private String signupCode;   // Code provided by you

    private String dateOfBirth;
    private String birthPlace;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }

    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }

    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }

    public String getSpouse() { return spouse; }
    public void setSpouse(String spouse) { this.spouse = spouse; }

    public String getCitizenshipNumber() { return citizenshipNumber; }
    public void setCitizenshipNumber(String citizenshipNumber) { this.citizenshipNumber = citizenshipNumber; }

    public String getSimNumber() { return simNumber; }
    public void setSimNumber(String simNumber) { this.simNumber = simNumber; }

    public String getSignupCode() { return signupCode; }
    public void setSignupCode(String signupCode) { this.signupCode = signupCode; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getBirthPlace() { return birthPlace; }
    public void setBirthPlace(String birthPlace) { this.birthPlace = birthPlace; }
}

