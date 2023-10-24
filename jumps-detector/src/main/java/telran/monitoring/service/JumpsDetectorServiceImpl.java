package telran.monitoring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.JumpPulse;
import telran.monitoring.dto.PulseProbe;
import telran.monitoring.entity.LastPulseValue;
import telran.monitoring.repo.LastPulseValueRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class JumpsDetectorServiceImpl implements JumpsDetectorService {

	final LastPulseValueRepo lastPulseValueRepo;
	@Value("${app.jumps.threshold:0.3}")
	double jumpThreshold;
	@Value("${app.jumps.period:300000}")
	private long jumpPeriod;

	@Override
	public JumpPulse processPulseProbe(PulseProbe pulseProb) {
		LastPulseValue lastPulseValue = lastPulseValueRepo.findById(pulseProb.patientId()).orElse(null);
		JumpPulse res = null;
		if (lastPulseValue != null && isJump(pulseProb.value(), lastPulseValue)) {
			// Если скачок
			res = new JumpPulse(pulseProb.patientId(), lastPulseValue.getValue(), pulseProb.value());
		} else if (lastPulseValue == null) {
			log.debug("no record in redis");
		} else {
			log.trace("record in redis exists, but no jump");
		}
		lastPulseValue = new LastPulseValue(pulseProb.patientId(), pulseProb.value(), System.currentTimeMillis());
		lastPulseValueRepo.save(lastPulseValue);
		return res;
	}

	private boolean isJump(int currentValue, LastPulseValue lastPulseValue) {
		int prevValue = lastPulseValue.getValue();
		int delta = Math.abs(currentValue - prevValue);
		long timestamp = lastPulseValue.getTimestamp();
		return System.currentTimeMillis() - timestamp <= jumpPeriod && prevValue * jumpThreshold <= delta;
	}

}
