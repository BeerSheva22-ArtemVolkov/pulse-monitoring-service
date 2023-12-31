package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.*;
import telran.monitoring.service.JumpsDetectorService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JumpsDetectorAppl {

	final StreamBridge streamBridge;
	final JumpsDetectorService jumpsService;
	
	@Value("${app.jumps.binding.name}")
	String jumpsBindingName;
	
	public static void main(String[] args) {
		SpringApplication.run(JumpsDetectorAppl.class, args);
	}

	@Bean
	Consumer<PulseProbe> pulseProbeConsumerJumps() {
		return this::probeConsumer;
	}
	
	void probeConsumer(PulseProbe pulseProb) {
		log.trace("received {}", pulseProb);
		JumpPulse jumpPulse = jumpsService.processPulseProbe(pulseProb);
		if (jumpPulse != null) {
			streamBridge.send(jumpsBindingName, jumpPulse);
			log.debug("jump {} sent to {}", jumpPulse, jumpsBindingName);
		} else {
			log.trace("no jump sent");
		}
	}
	
}
