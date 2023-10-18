package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.service.AvgPopulationService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AveragePopulationAppl {

	final AvgPopulationService avgPopulationService;
	
	public static void main(String[] args) {
		SpringApplication.run(AveragePopulationAppl.class, args);
	}
	
	@Bean
	Consumer<PulseProbe> avgPulseConsumer() {
		return this::probeConsumer;
	}
	
	void probeConsumer(PulseProbe avgPulseProb) {
		log.trace("received {}", avgPulseProb);
		avgPopulationService.processPulseProbe(avgPulseProb);;
	}
	
}
