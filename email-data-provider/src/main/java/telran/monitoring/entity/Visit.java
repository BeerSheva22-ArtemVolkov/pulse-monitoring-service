package telran.monitoring.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "visits")
@Getter
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	Doctor doctor;
	
	Date date;
	
}
