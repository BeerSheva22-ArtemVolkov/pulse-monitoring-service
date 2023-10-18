package telran.monitoring.service;

import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.entity.Doctor;
import telran.monitoring.entity.Patient;

public interface EmailDataService {
	
	EmailNotificationData getData(long patientId);
}
