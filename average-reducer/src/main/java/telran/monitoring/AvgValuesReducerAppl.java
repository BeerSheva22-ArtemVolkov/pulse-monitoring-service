package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.service.AvgReducerService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AvgValuesReducerAppl {

	final AvgReducerService service;
	final StreamBridge streamBridge; // для продьюсинга в месседж брокер
	@Value("${app.avg.binding.name}")
	String bindingName;

	public static void main(String[] args) {
		SpringApplication.run(AvgValuesReducerAppl.class, args);
	}

	@Bean
	Consumer<PulseProbe> pulseProbeConsumerAvg() {
		return this::processPulseProbe;
	}

	void processPulseProbe(PulseProbe probe) {
		log.trace("{} accepted", probe);
		long patientId = probe.patientId();
		Integer avgValue = service.reduce(probe);
		if (avgValue != null) {
			log.debug("for patient {} avg value is {}", patientId, avgValue);
			// Посылает ответ обратно тому кто запрашивал?
			streamBridge.send(bindingName, new PulseProbe(patientId, avgValue, System.currentTimeMillis(), 0));
		} else {
			log.trace("for patient {} no avg value yet", patientId);
		}
	}

}
