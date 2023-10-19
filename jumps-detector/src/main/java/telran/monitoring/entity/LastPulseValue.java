package telran.monitoring.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

// api - дто (не спринг) те не сервис а библиотека. Этими дто будут пользоваться все 
// service - 

@RedisHash // в принципе это HashMap, доступный для всех экземпляров микросервиса (REmote DIctionary Service)
@Getter
@AllArgsConstructor
public class LastPulseValue {
	
	@Id
	long patientId;
	int value;
}
