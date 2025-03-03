package com.example.serviceYandexCalendar.service.Impl;

import com.example.serviceYandexCalendar.service.ServiceYandexCalendar;
import com.example.serviceYandexCalendar.util.AppConstant;
import com.github.caldav4j.CalDAVCollection;
import com.github.caldav4j.exceptions.CalDAV4JException;
import lombok.RequiredArgsConstructor;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ServiceYandexCalendarImpl implements ServiceYandexCalendar {

    private final CalDAVCollection calDAVCollection;

    private final HttpClient httpClient;

    @Override
    public void sendServiceYandexCalendar(LocalDateTime startDateTime, LocalDateTime endDateTime, String name, String description) throws CalDAV4JException {
        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone(AppConstant.TIME_ZONE);
        VTimeZone tz = timezone.getVTimeZone();
        java.util.Calendar startDate = createDate(startDateTime, timezone);
        java.util.Calendar endDate = createDate(endDateTime, timezone);

        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());
        String uniqueUID = UUID.randomUUID().toString();
        VEvent event = new VEvent(start, end, name);
        event.getProperties().add(new Uid(uniqueUID));
        VEvent meeting = new VEvent(start, end, name);

        meeting.getProperties().add(tz.getTimeZoneId());
        meeting.getProperties().add(new Description(description));

        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId(AppConstant.PROD_ID));
        icsCalendar.getComponents().add(meeting);
        calDAVCollection.add(httpClient, icsCalendar);

    }

    private Calendar createDate(LocalDateTime DateTime, TimeZone timezone) {
        java.util.Calendar Date = new GregorianCalendar();
        Date.setTimeZone(timezone);
        Date.set(java.util.Calendar.MONTH, DateTime.getMonthValue() - 1);
        Date.set(java.util.Calendar.DAY_OF_MONTH, DateTime.getDayOfMonth());
        Date.set(java.util.Calendar.YEAR, DateTime.getYear());
        Date.set(java.util.Calendar.HOUR_OF_DAY, DateTime.getHour());
        Date.set(java.util.Calendar.MINUTE, DateTime.getMinute());
        Date.set(java.util.Calendar.SECOND, DateTime.getSecond());
        return Date;
    }
}