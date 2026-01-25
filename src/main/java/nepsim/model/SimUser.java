package nepsim.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data  // Generates getters & setters automatically
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
    private String dateOfBirth;
    private String birthPlace;
    private String password;
    private String simNumber;


}
