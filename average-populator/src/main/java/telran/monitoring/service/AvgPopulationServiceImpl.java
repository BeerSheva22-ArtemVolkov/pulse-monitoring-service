package telran.monitoring.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.repo.AvgPulseRepo;

@RequiredArgsConstructor
@Service
@Slf4j
public class AvgPopulationServiceImpl implements AvgPopulationService {

	final AvgPulseRepo avgPulseRepo;

	@Override
	public void processPulseProbe(PulseProbe avgPulseProb) {
		log.info("Received PulseProbe {} ", avgPulseProb);
		AvgPulseDoc avgPulseDoc = AvgPulseDoc.of(avgPulseProb);
		avgPulseRepo.save(avgPulseDoc);
		log.debug("Added to Mongo {} ", avgPulseDoc);
	}

}
