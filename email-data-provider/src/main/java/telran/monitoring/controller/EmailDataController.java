package telran.monitoring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.entity.Doctor;
import telran.monitoring.entity.Patient;
import telran.monitoring.repo.PatientRepo;
import telran.monitoring.repo.VisitRepo;
import telran.monitoring.service.EmailDataService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailDataController {

	final EmailDataService dataService;
	
	@GetMapping("data/{patientId}")
	public ResponseEntity<?> getData(@PathVariable long patientId) {
		try {
			EmailNotificationData res = dataService.getData(patientId);
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			String reason = e.getMessage();
			log.warn(reason);
			return ResponseEntity.status(404).body(reason);
		}
	}

}
