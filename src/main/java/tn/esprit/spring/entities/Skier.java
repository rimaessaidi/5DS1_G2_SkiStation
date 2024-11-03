package tn.esprit.spring.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@Entity
@ToString
public class Skier implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long numSkier;
	String firstName;
	String lastName;
	LocalDate dateOfBirth;
	String city;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	Subscription subscription;

	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "excursion",
			joinColumns = @JoinColumn(name = "numSkier"),
			inverseJoinColumns = @JoinColumn(name = "numPiste"))
	private Set<Piste> pistes;


	@OneToMany(mappedBy = "skier")
	private Set<Registration> registrations;

	@Override
	public String toString() {
		return "Skier{" +
				"numSkier=" + numSkier +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", dateOfBirth=" + dateOfBirth +
				", city='" + city + '\'' +
				", subscription=" + subscription +
				'}';
	}





}
