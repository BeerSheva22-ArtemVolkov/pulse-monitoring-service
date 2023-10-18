package telran.monitoring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Table(name = "doctors_patients")
@NoArgsConstructor
abstract public class Person {

	@Id
	long id;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "email")
	String email;
	
}
