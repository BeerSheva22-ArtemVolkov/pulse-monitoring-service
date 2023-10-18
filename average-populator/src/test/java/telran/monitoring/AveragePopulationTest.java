package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.repo.AvgPulseRepo;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class AveragePopulationTest {

	private static final long PATIENT_ID1 = 123;
	private static final int VALUE1 = 70;
	private static final long PATIENT_ID2 = 124;
	private static final int VALUE2 = 75;
	
	@Autowired
	InputDestination producer; // мок это продюсер, тк присылает

	@Autowired
	AvgPulseRepo avgPulseRepo;
	
	PulseProbe pulseProbe1 = new PulseProbe(PATIENT_ID1, VALUE1, 0, 0);
	PulseProbe pulseProbe2 = new PulseProbe(PATIENT_ID2, VALUE2, 0, 0);
	AvgPulseDoc doc1 = AvgPulseDoc.of(pulseProbe1);
	AvgPulseDoc doc2 = AvgPulseDoc.of(pulseProbe2);
	
	String consumerBindingName = "avgPulseConsumer-in-0";
	
	@Test
	void test() {
		List<AvgPulseDoc> res = avgPulseRepo.findAll();
		assertEquals(0, res.size());
		producer.send(new GenericMessage<PulseProbe>(pulseProbe1), consumerBindingName);
		List<AvgPulseDoc> res1 = avgPulseRepo.findAll();
		assertEquals(1, res1.size());
		producer.send(new GenericMessage<PulseProbe>(pulseProbe2), consumerBindingName);
		List<AvgPulseDoc> documents  = avgPulseRepo.findAll();
		List<AvgPulseDoc> expected = Arrays.asList(doc1, doc2);
		assertIterableEquals(expected, documents);
	}
}
