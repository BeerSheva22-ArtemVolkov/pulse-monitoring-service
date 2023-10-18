package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.dto.Value;
import telran.monitoring.repo.AvgPulseRepo;

@SpringBootTest
class BackOfficeRepositoryTest {

	@Autowired
	AvgPulseRepo avgPulseRepo;

	static final long PATIENT_ID = 123l;
	static final int VALUE_1 = 120;
	static final int VALUE_2 = 130;
	static final int VALUE_3 = 140;
	static final int VALUE_4 = 150;
	static final int VALUE_5 = 160;
	static final LocalDateTime DATE_1 = LocalDateTime.of(2023, 1, 1, 0, 0);
	static final LocalDateTime DATE_2 = LocalDateTime.of(2023, 1, 2, 0, 0);
	static final LocalDateTime DATE_3 = LocalDateTime.of(2023, 1, 3, 0, 0);
	static final LocalDateTime DATE_4 = LocalDateTime.of(2023, 1, 4, 0, 0);
	static final LocalDateTime DATE_5 = LocalDateTime.of(2023, 1, 5, 0, 0);
	static final LocalDateTime DATE_FROM = DATE_1.minusDays(1);
	static final LocalDateTime DATE_TO = DATE_3.plusDays(1);
	
	@BeforeEach
	void beforeEach() {
		avgPulseRepo.deleteAll();
	}
	
	@Test
	void maxTest() {
		List<AvgPulseDoc> res1 = avgPulseRepo.findAll();
		assertEquals(0, res1.size());
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_1, DATE_1));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_2, DATE_2));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_3, DATE_3));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_4, DATE_4));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_5, DATE_5));
		List<AvgPulseDoc> res2 = avgPulseRepo.findAll();
		assertEquals(5, res2.size());
		AvgPulseDoc res = avgPulseRepo.findFirstByPatientIdAndDateTimeBetweenOrderByValueDesc(PATIENT_ID, DATE_FROM, DATE_TO);
		assertEquals(VALUE_3, res.getValue());
	}
	
	@Test
	void allTest() {
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_1, DATE_1));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_2, DATE_2));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_3, DATE_3));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_4, DATE_4));
		avgPulseRepo.insert(new AvgPulseDoc(PATIENT_ID, VALUE_5, DATE_5));
		List<AvgPulseDoc> res = avgPulseRepo.findByPatientIdAndDateTimeBetween(PATIENT_ID, DATE_FROM, DATE_TO);
		assertEquals(3, res.size());
	}

}
