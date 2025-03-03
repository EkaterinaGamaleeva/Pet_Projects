package com.example.serviceYandexCalendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceYandexCalendarApplication {

	public static void main(String[] args) {
		System.setProperty("net.fortuna.ical4j.timezone.cache.impl", "net.fortuna.ical4j.util.MapTimeZoneCache");
		SpringApplication.run(ServiceYandexCalendarApplication.class, args);
	}

}
