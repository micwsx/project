package com.research.java8.newDateAndTime;

import org.springframework.data.mongodb.core.aggregation.DateOperators;

import java.time.*;
import java.time.chrono.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.time.zone.ZoneRules;
import java.util.*;

import static java.time.temporal.TemporalAdjusters.*;

/**
 * @author Michael
 * @create 11/20/2020 3:41 PM
 */
public class NewDateAndTime {
    public static void main(String[] args) {

//        Map map=new HashMap();// 无序，key-value是否允许null.
//        map=new Hashtable(); // 线程安全，key-value是否允许null.
//        map=new TreeMap();// 线程不安全，键值有序。



        LocalDate date = LocalDate.of(2020, 12, 04);
        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);

        Chronology japaneseChronology = Chronology.ofLocale(Locale.CHINA);
        ChronoLocalDate chronoLocalDate = japaneseChronology.dateNow();
        System.out.println(chronoLocalDate);

        MinguoDate date1 = MinguoDate.from(date);
        System.out.println(date1);


    }

    private static void ZoneIdAndZoneOff() {
        ZoneId rome = ZoneId.of("Europe/Rome");
//        LocalDate date = LocalDate.of(2020, Month.DECEMBER, 04);
//        ZonedDateTime zonedDateTime = date.atStartOfDay(rome);
//        System.out.println(zonedDateTime);//2020-12-04T00:00+01:00[Europe/Rome]
//
//        LocalDateTime dateTime = LocalDateTime.of(2020, Month.DECEMBER, 18, 13, 45);
//        ZonedDateTime zonedDateTime1 = dateTime.atZone(rome);
//        System.out.println(zonedDateTime1);//2020-12-18T13:45+01:00[Europe/Rome]
//        Instant now = Instant.now();
//
//        ZonedDateTime zonedDateTime2 = now.atZone(rome);
//        System.out.println(zonedDateTime2);//2020-12-04T07:48:17.504+01:00[Europe/Rome]

        LocalDateTime dateTime = LocalDateTime.of(2020, Month.DECEMBER, 18, 13, 45);
        Instant instant = dateTime.atZone(rome).toInstant();

        Instant instant2 = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant2, rome);
        System.out.println(localDateTime);

        ZoneOffset newYorkZoneOff = ZoneOffset.of("-05:00");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(dateTime, newYorkZoneOff);
        int hour = offsetDateTime.getHour();
        System.out.println(hour);
    }

    private static void usageDateTimeFormatter() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate parse = LocalDate.parse("2012/12/03", dateTimeFormatter);
        System.out.println(parse);

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("d.MMMM yyyy", Locale.ITALIAN);
        LocalDate date = LocalDate.of(2020, 12, 04);
        String format = date.format(dateTimeFormatter1);
        System.out.println(format);//4.dicembre 2020
        LocalDate date2 = LocalDate.parse(format, dateTimeFormatter1);
        System.out.println(date2);//2020-12-04

        DateTimeFormatter dateTimeFormatter2 = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(".")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
    }

    private static void formatAndParse() {
        LocalDate date = LocalDate.of(2020, 12, 04);
        String basic_iso_date = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        String iso_local_date = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(basic_iso_date);//20201204
        System.out.println(iso_local_date);//2020-12-04

        LocalDate parse_date1 = LocalDate.parse("20201204", DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate parse_date2 = LocalDate.parse("2020-12-04", DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(parse_date1);//2020-12-04
        System.out.println(parse_date2);//2020-12-04
    }

    private static void usageTemporalAdjuster() {
        LocalDate date = LocalDate.of(2014, 12, 4);
        LocalDate date1 = date.with(nextOrSame(DayOfWeek.SUNDAY));
        LocalDate date2 = date1.with(lastDayOfMonth());
        System.out.println(date1);//2014-03-23
        System.out.println(date2);//2014-03-31

        LocalDate nextWorkingDay = date.with(new NextWorkingDay());
        System.out.println(nextWorkingDay);
    }

    public static class NextWorkingDay implements TemporalAdjuster {
        @Override
        public Temporal adjustInto(Temporal temporal) {
            int i = temporal.get(ChronoField.DAY_OF_WEEK) + 1;
            if (i >= 1 && i <= 4) {
                return temporal.plus(1, ChronoUnit.DAYS);
            } else {
                return temporal.plus(8 - i, ChronoUnit.DAYS);
            }
        }
    }

    private static void manipulateDate() {
        try {

            LocalDate date = LocalDate.of(2014, 3, 18);
            date = date.with(ChronoField.MONTH_OF_YEAR, 9);//2014-09-18
            date = date.plusYears(2).minusDays(10);//2016-09-08
            date.withYear(2011);
            LocalDate date1 = date.with(ChronoField.MONTH_OF_YEAR, 9).plusYears(2).minusDays(10).withYear(2011);
            System.out.println(date);//2011-09-08

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void durationUsage() {
        // Time时间差
        LocalTime time1 = LocalTime.of(20, 12, 10);
        LocalTime time2 = LocalTime.of(20, 12, 40);
        Duration d1 = Duration.between(time1, time2);
        //Duration只能获取seconds或者nanoseconds
        System.out.println(d1.getSeconds());
        // 因为Duration只能获取seconds或nanoseconds，所以计算LocalDate时间差会报错。
        LocalDate date1 = LocalDate.of(2020, 11, 12);
        LocalDate date2 = LocalDate.of(2020, 11, 22);
        Duration d2 = Duration.between(date1, date2);
        System.out.println(d2.get(ChronoUnit.DAYS));
        // Instant时间差
        Instant instant1 = Instant.ofEpochSecond(3, 0);
        Instant instant2 = Instant.ofEpochSecond(400, 0);
        Duration d3 = Duration.between(instant1, instant2);
        System.out.println(d3.getSeconds());
        // Duration不能计算不同“级别”的时间。比如：time,dateTime，若获取年，月，日使用Period.
        Period period = Period.between(LocalDate.of(2020, 12, 2), LocalDate.of(2020, 12, 6));
        long l = period.get(ChronoUnit.DAYS);
        System.out.println(l);//4
        l = period.get(ChronoUnit.SECONDS);
        System.out.println(l);//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Seconds

        Duration threeMunites = Duration.ofMinutes(3);
        Duration threeMunites2 = Duration.of(3, ChronoUnit.MINUTES);

        Period tenDays = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);

    }

    private static void InstantUsage() {
        Instant instant = Instant.ofEpochSecond(3);
        System.out.println(instant); //1970-01-01T00:00:03Z
        Instant instant1 = Instant.ofEpochSecond(3, 0);
        System.out.println(instant1);//1970-01-01T00:00:03Z
        Instant instant2 = Instant.ofEpochSecond(2, 1_000_000_000);
        System.out.println(instant2);//1970-01-01T00:00:03Z
        Instant instant3 = Instant.ofEpochSecond(4, -1_000_000_000);
        System.out.println(instant3);//1970-01-01T00:00:03Z
        int nano = Instant.now().get(ChronoField.NANO_OF_SECOND);
        System.out.println(nano);//1970-01-01T00:00:03Z
        int day = Instant.now().get(ChronoField.DAY_OF_MONTH);
        System.out.println(day);// UnsupportedTemporalTypeException: Unsupported field: DayOfMonth
    }

    private static void LocalDateTimeUsage() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        LocalDateTime d1 = LocalDateTime.of(2014, Month.NOVEMBER, 19, 13, 45, 20);
        LocalDateTime d2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
    }

    private static void LocalTimeUsage() {
        LocalTime localTime = LocalTime.of(10, 9, 56);
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        int nano = localTime.getNano();
        LocalDate date = LocalDate.parse("2012-09-03");
        LocalTime time = LocalTime.parse("12:04:23");
        System.out.println(date);
        System.out.println(time);
    }

    private static void LocalDateUsage() {
        //        LocalDate date=LocalDate.of(2020,11,18);
//        int year = date.getYear();
//        Month month = date.getMonth();
//        int dayOfMonth = date.getDayOfMonth();
//        DayOfWeek dayOfWeek = date.getDayOfWeek();
//        int len = date.lengthOfMonth();
//        boolean leapYear = date.isLeapYear();
//        System.out.println(date.toString());

        LocalDate now = LocalDate.now();
        int year = now.get(ChronoField.YEAR);
        int month = now.get(ChronoField.MONTH_OF_YEAR);
        int day = now.get(ChronoField.DAY_OF_MONTH);
    }
}
