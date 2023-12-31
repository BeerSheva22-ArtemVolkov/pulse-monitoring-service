package telran.monitoring.entity;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Doctor extends Person {
	public Doctor(long id, String email, String name) {
		super(id, name, email);
	}
}
