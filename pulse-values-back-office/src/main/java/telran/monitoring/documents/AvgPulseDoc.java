package telran.monitoring.documents;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import telran.monitoring.api.ApiConstants;
import telran.monitoring.dto.PulseProbe;

@Document(collection = ApiConstants.avgValuesCollection)
@AllArgsConstructor
public class AvgPulseDoc {
	long patientId;
	int value;
	LocalDateTime dateTime;

	public static AvgPulseDoc of(PulseProbe pulseProbe) {
		return new AvgPulseDoc(pulseProbe.patientId(), pulseProbe.value(),
				LocalDateTime.ofInstant(Instant.ofEpochMilli(pulseProbe.timestamp()), ZoneId.systemDefault()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(patientId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvgPulseDoc other = (AvgPulseDoc) obj;
		return patientId == other.patientId && value == other.value;
	}

	public long getPatientId() {
		return patientId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public int getValue() {
		return value;
	}

}
