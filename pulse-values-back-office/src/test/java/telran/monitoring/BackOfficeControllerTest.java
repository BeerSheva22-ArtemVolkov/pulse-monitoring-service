package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.controller.AvgPulseController;
import telran.monitoring.service.AvgPulseValuesService;

@WebMvcTest(AvgPulseController.class)
class BackOfficeControllerTest {

	private static final Integer AVG_VALUE_DATES = 90;
	private static final Integer MIN_VALUE_DATES = 80;
	private static final Integer MAX_VALUE_DATES = 100;

	@Autowired
	MockMvc mockMvc;
	@MockBean
	AvgPulseValuesService pulseService;

	@BeforeEach
	void setUp() {
		when(pulseService.getAvgValue(
				ArgumentMatchers.anyLong(), 
				ArgumentMatchers.any(LocalDateTime.class), 
				ArgumentMatchers.any(LocalDateTime.class)
				))
		.thenReturn(AVG_VALUE_DATES);
		when(pulseService.getMaxValue(
				ArgumentMatchers.anyLong(),				
				ArgumentMatchers.any(LocalDateTime.class),
				ArgumentMatchers.any(LocalDateTime.class)
				))
		.thenReturn(MAX_VALUE_DATES);
		when(pulseService.getAllValues(
				ArgumentMatchers.anyLong(),
				ArgumentMatchers.any(LocalDateTime.class),
				ArgumentMatchers.any(LocalDateTime.class)
				))
		.thenReturn(List.of(MIN_VALUE_DATES, AVG_VALUE_DATES, MAX_VALUE_DATES));
	}

	@Test
	void datesTest() throws UnsupportedEncodingException, Exception {
		String res = mockMvc
				.perform(get("/pulse/values/avg/123").param("from", "2023-01-01T12:00").param("to", "2023-03-01T12:00"))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(AVG_VALUE_DATES + "", res);
	}

	@Test
	void allValuesTest() throws UnsupportedEncodingException, Exception {
		String resJSON = mockMvc
				.perform(get("/pulse/values/all/123").param("from", "2023-01-01T12:00").param("to", "2023-03-01T12:00"))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Integer[] expected = { MIN_VALUE_DATES, AVG_VALUE_DATES, MAX_VALUE_DATES };
		ObjectMapper mapper = new ObjectMapper();
		assertArrayEquals(expected, mapper.readValue(resJSON, Integer[].class));

	}

//	@Test
//	void minDatesTest() throws UnsupportedEncodingException, Exception {
//		String res = mockMvc
//				.perform(get("/pulse/values/min/123").param("from", "2023-01-01T12:00").param("to", "2023-03-01T12:00"))
//				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//		assertEquals(MIN_VALUE_DATES + "", res);
//	}

	@Test
	void maxDatesTest() throws UnsupportedEncodingException, Exception {
		String res = mockMvc
				.perform(get("/pulse/values/max/123").param("from", "2023-01-01T12:00").param("to", "2023-03-01T12:00"))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		assertEquals(MAX_VALUE_DATES + "", res);
	}

	@Test
	void dateFromTest() throws UnsupportedEncodingException, Exception {
		String res = mockMvc.perform(get("/pulse/values/avg/123").param("from", "2023-01-01T12:00")).andDo(print())
				.andExpect(status().is4xxClientError()).andReturn().getResponse().getContentAsString();
//		assertEquals(AVG_VALUE_DATES + "", res);
	}

	@Test
	void dateToTest() throws UnsupportedEncodingException, Exception {
		String res = mockMvc.perform(get("/pulse/values/avg/123").param("to", "2023-01-01T12:00")).andDo(print())
				.andExpect(status().is4xxClientError()).andReturn().getResponse().getContentAsString();
//		assertEquals(AVG_VALUE_DATES + "", res);
	}

	@Test
	void noDatesTest() throws UnsupportedEncodingException, Exception {
		String res = mockMvc.perform(get("/pulse/values/avg/123")).andDo(print()).andExpect(status().is4xxClientError())
				.andReturn().getResponse().getContentAsString();
//		assertEquals(AVG_VALUE_DATES + "", res);
	}

	@Test
	void wrongToDateTest() throws UnsupportedEncodingException, Exception {
		mockMvc.perform(get("/pulse/values/avg/123").param("to", "2023-01")).andDo(print())
				.andExpect(status().isBadRequest());

	}

	@Test
	void wrongFromDateTest() throws UnsupportedEncodingException, Exception {
		mockMvc.perform(get("/pulse/values/avg/123").param("from", "2023-01")).andDo(print())
				.andExpect(status().isBadRequest());

	}

}
