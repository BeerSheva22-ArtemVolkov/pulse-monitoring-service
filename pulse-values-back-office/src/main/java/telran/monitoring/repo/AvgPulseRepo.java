package telran.monitoring.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.dto.Value;

public interface AvgPulseRepo extends MongoRepository<AvgPulseDoc, ObjectId> {

//	final String getValues = "{'patientId': ?0, 'dateTime': {$gte: ?1, $lte: ?2}}";
	
//	Int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to);
//	@Aggregation(pipeline = {
//			"{ '$match': " + getValues + " }, " + "{ $group: { _id: null, value: { $avg: '$value' } } }" })
//	AvgPulseDoc getAvgValue(long patientId, LocalDateTime from, LocalDateTime to);

//	Int getMaxValue(long patientId, LocalDateTime from, LocalDateTime to);
	AvgPulseDoc findFirstByPatientIdAndDateTimeBetweenOrderByValueDesc(long id, LocalDateTime from, LocalDateTime to);

	List<AvgPulseDoc> findByPatientIdAndDateTimeBetween(long patientId, LocalDateTime from, LocalDateTime to);

}
