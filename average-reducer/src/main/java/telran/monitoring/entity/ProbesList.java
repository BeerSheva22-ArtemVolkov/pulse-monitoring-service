package telran.monitoring.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class ProbesList {
	
	@Id
	long patientId;
	List<Integer> pulseValues = new ArrayList<>();

	public ProbesList(long patientId) {
		this.patientId = patientId;
	}	

	public long getPatientId() {
		return patientId;
	}

	public List<Integer> getPulseValues() {
		return pulseValues;
	}

}
