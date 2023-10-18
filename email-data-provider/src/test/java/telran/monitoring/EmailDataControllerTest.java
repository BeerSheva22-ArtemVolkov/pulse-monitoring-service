package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.monitoring.controller.EmailDataController;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.service.EmailDataService;

//@SpringBootTest
//@Sql(scripts = { "script.sql" })
//@AutoConfigureMockMvc
@WebMvcTest(value = { EmailDataController.class })
class EmailDataControllerTest {

	private int DOCTOR_ID_1 = 100;
	private int DOCTOR_ID_2 = 101;
	private int DOCTOR_ID_3 = 102;
	private int DOCTOR_ID_4 = 103;
	private String DOCTOR_NAME_1 = "Doctor1";
	private String DOCTOR_NAME_2 = "Doctor2";
	private String DOCTOR_NAME_3 = "Doctor3";
	private String DOCTOR_NAME_4 = "Doctor4";
	private String DOCTOR_EMAIL_1 = "d1.100@gmail.com";
	private String DOCTOR_EMAIL_2 = "d2.101@gmail.com";
	private String DOCTOR_EMAIL_3 = "d3.102@gmail.com";
	private String DOCTOR_EMAIL_4 = "d4.103@gmail.com";
	private int PATIENT_ID_1 = 200;
	private int PATIENT_ID_2 = 201;
	private int PATIENT_ID_3 = 202;
	private int PATIENT_ID_4 = 203;
	private String PATIENT_NAME_1 = "Patient1";
	private String PATIENT_NAME_2 = "Patient2";
	private String PATIENT_NAME_3 = "Patient3";
	private String PATIENT_NAME_4 = "Patient4";
	private String PATIENT_EMAIL_1 = "p1.200@gmail.com";
	private String PATIENT_EMAIL_2 = "p2.201@gmail.com";
	private String PATIENT_EMAIL_3 = "p3.202@gmail.com";
	private String PATIENT_EMAIL_4 = "p4.203@gmail.com";
	
	static final String ERROR_RESPONSE = "patient not found";

	@Autowired
	EmailDataService service;

	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("service returns data")
	void dataExistFlow() throws Exception {
		String jsonResponse = mockMvc.perform(get("/data/" + PATIENT_ID_2)).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		ObjectMapper mapper = new ObjectMapper();
		EmailNotificationData resData = mapper.readValue(jsonResponse, EmailNotificationData.class);
		assertEquals(PATIENT_NAME_2, resData.patientName());
		assertEquals(DOCTOR_NAME_1, resData.doctorName());
		assertEquals(DOCTOR_EMAIL_1, resData.doctorMail());
	}
	
	@Test
	@DisplayName("service throws exception")
	void noDataFlow() throws Exception {
		String response = mockMvc.perform(get("http://localhost:8080/data/" + 10000)).andDo(print()).andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString();
		assertEquals(response, ERROR_RESPONSE);
	}

}
