package telran.monitoring;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.monitoring.dto.EmailNotificationData;
import telran.monitoring.dto.JumpPulse;
import telran.monitoring.service.EmailDataProvider;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class JumpsEmailNotifierAppl {

	final JavaMailSender mailSender;
	final EmailDataProvider dataProvider;

	@Value("${app.email.service.address:hospitalalert@gmail.com}")
	private String hospitalServiceEmail;
	@Value("${app.email.service.name:Hospital Alert Service}")
	private String hospitalServiceName;
	@Value("${app.email.subject:Pulse Jump }")
	private String subject;

	public static void main(String[] args) {
		SpringApplication.run(JumpsEmailNotifierAppl.class, args);
	}

	@Bean
	Consumer<JumpPulse> jumpConsumer() {
		return this::jumpProcessing;
	}

	void jumpProcessing(JumpPulse jumpPulse) {
		log.trace("Received: {}", jumpPulse);
		sendMail(jumpPulse);
	}

	private void sendMail(JumpPulse jumpPulse) {
		long patientId = jumpPulse.patientId();
		EmailNotificationData emailNotificationData = dataProvider.getData(patientId);
		if (emailNotificationData == null) {
			log.warn("email data have not been received");
			emailNotificationData = new EmailNotificationData(hospitalServiceEmail, hospitalServiceName,
					"" + patientId);
		}
		log.trace("email data: doctor email: {}, doctor name: {}, patient name: {}", emailNotificationData.doctorMail(),
				emailNotificationData.doctorName(), emailNotificationData.patientName());
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setTo(emailNotificationData.doctorMail());
		smm.setSubject(subject + patientId);
		String text = getText(jumpPulse, emailNotificationData);
		smm.setText(text);
		mailSender.send(smm);
		log.trace("sent: {}", text);
	}

	private String getText(JumpPulse jumpPulse, EmailNotificationData data) {
		return String.format(
				"Dear %s\n" + "Your patient (%s) has the pulse jump\n" + "previous value: %d\n" + "current value: %d\n",
				data.doctorName(), data.patientName(), jumpPulse.prevValue(), jumpPulse.currentValue());
	}

}
