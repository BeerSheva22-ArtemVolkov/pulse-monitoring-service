package telran.monitoring.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.entity.Doctor;
import telran.monitoring.entity.DoctorId;
import telran.monitoring.entity.Patient;
import telran.monitoring.repo.DoctorRepo;
import telran.monitoring.repo.PatientRepo;
import telran.monitoring.repo.VisitRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailDataServiceImpl implements EmailDataService {

	final PatientRepo patientRepo;
	final VisitRepo visitRepo;
	final DoctorRepo doctorRepo;

	@Override
	public EmailNotificationData getData(long patientId) {
		Patient patient = patientRepo.findById(patientId).orElseThrow(() -> new RuntimeException("patient not found"));
		DoctorId doctorId = visitRepo.findDoctorIdLastVisit(patientId);
		if (doctorId == null) {
			throw new RuntimeException("Doctor not found");
		}
		log.trace("DoctorId is {}", doctorId.getId());
		Doctor doctor = doctorRepo.findById(doctorId.getId())
				.orElseThrow(() -> new IllegalStateException("Wrong doctorId"));
		return new EmailNotificationData(doctor.getEmail(), doctor.getName(), patient.getName());
	}

}
