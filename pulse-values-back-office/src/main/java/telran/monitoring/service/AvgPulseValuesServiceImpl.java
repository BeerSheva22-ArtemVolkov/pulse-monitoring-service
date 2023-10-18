package telran.monitoring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import lombok.RequiredArgsConstructor;
import telran.monitoring.documents.AvgPulseDoc;
import telran.monitoring.repo.AvgPulseRepo;

@Service
@RequiredArgsConstructor
public class AvgPulseValuesServiceImpl implements AvgPulseValuesService {

	final AvgPulseRepo avgPulseRepo;
	final MongoTemplate mongoTemplate;

	@Override
	public int getAvgValue(long patientId, LocalDateTime from, LocalDateTime to) {
		MatchOperation matchOperation = match(
				Criteria.where("patientId").is(patientId).and("dateTime").gte(from).lte(to));
		GroupOperation groupOperation = group().avg("value").as("avg");
		Aggregation pipeLine = newAggregation(List.of(matchOperation, groupOperation));
		var aggregationResult = mongoTemplate.aggregate(pipeLine, AvgPulseDoc.class, Document.class);
		Document document = aggregationResult.getUniqueMappedResult();
		return document == null ? 0 : document.getDouble("avg").intValue();
	}

	@Override
	public int getMaxValue(long patientId, LocalDateTime from, LocalDateTime to) {
		AvgPulseDoc res = avgPulseRepo.findFirstByPatientIdAndDateTimeBetweenOrderByValueDesc(patientId, from, to);
		return res == null ? 0 : res.getValue();
	}

	@Override
	public List<Integer> getAllValues(long patientId, LocalDateTime from, LocalDateTime to) {
		List<AvgPulseDoc> values = avgPulseRepo.findByPatientIdAndDateTimeBetween(patientId, from, to);
		return values.stream().map(v -> v.getValue()).collect(Collectors.toList());
	}

}
