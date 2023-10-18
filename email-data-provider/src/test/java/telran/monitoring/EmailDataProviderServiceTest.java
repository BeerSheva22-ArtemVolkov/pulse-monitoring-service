package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.service.EmailDataService;

@SpringBootTest
@Sql(scripts = "script.sql")
class EmailDataProviderServiceTest {

	private static final long PATIENT_ID = 200;
	private static final long PATIENT_ID_NO_DOCTOR = 204;
	private static final EmailNotificationData DATA = new EmailNotificationData("d2.101@gmail.com", "Doctor2", "Patient1");
	
	@Autowired
	EmailDataService service;
	
	@Test
	void doctorPatientExistTest() {
		assertEquals(DATA, service.getData(PATIENT_ID));
	}

	@Test
	void patientNotFoundTest() {
		assertThrowsExactly(RuntimeException.class, () -> service.getData(10000), "patient not found");
	}

	@Test
	void doctorNotFoundTest() {
		assertThrowsExactly(RuntimeException.class, () -> service.getData(PATIENT_ID_NO_DOCTOR),
				"doctor not found");
	}

}
