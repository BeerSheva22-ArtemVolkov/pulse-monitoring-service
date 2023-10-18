package telran.monitoring.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.service.AvgPulseValuesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pulse/values")
@Slf4j
public class AvgPulseController {

	final AvgPulseValuesService backOfficeService;

	@GetMapping("/avg/{id}")
	public int getAvg(@PathVariable("id") long patientId, @RequestParam(required = true) LocalDateTime from,
			@RequestParam(required = true) LocalDateTime to) {
		return backOfficeService.getAvgValue(patientId, from.minusSeconds(1), to.plusSeconds(1));
	}

	@GetMapping("/max/{id}")
	public int getMax(@PathVariable("id") long patientId, @RequestParam(required = true) LocalDateTime from,
			@RequestParam(required = true) LocalDateTime to) {
		return backOfficeService.getMaxValue(patientId, from.minusSeconds(1), to.plusSeconds(1));
	}

	@GetMapping("/all/{id}")
	public List<Integer> getAll(@PathVariable("id") long patientId, @RequestParam(required = true) LocalDateTime from,
			@RequestParam(required = true) LocalDateTime to) {
		return backOfficeService.getAllValues(patientId, from.minusSeconds(1), to.plusSeconds(1));
	}

}
