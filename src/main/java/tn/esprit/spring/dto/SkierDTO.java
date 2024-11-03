package tn.esprit.spring.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
public class SkierDTO {
    Long numSkier; // Optional, if you want to expose it on creation/update
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String city;
    // You can include other fields as necessary, such as Subscription details or any additional properties.
}
