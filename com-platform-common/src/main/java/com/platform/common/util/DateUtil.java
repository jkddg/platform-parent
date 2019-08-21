package com.platform.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

/**
 * Created by Huangyonghao on 2019/5/10 18:50.
 */
@Slf4j
public class DateUtil {

    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateUtil() {
    }

    public static LocalDate dateStr2LocalDate(String dateStr) {
        return LocalDate.parse(dateStr);
    }

    public static String localDate2Str(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String localDateTime2Str(LocalDateTime localDateTime) {
        return localDateTime2Str(localDateTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static String localDateTime2Str(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate dateTimeStr2LocalDate(String dateTimeStr) {
        return LocalDate.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDateTime dateTimeStr2LocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static LocalDateTime parseDateTime(String dateTime, String format) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDateTime.parse(dateTime, formatter);
        } catch (Exception var3) {
            log.error("util > parse date time `{}` error:{}", new Object[]{dateTime, format, var3});
            return null;
        }
    }

    public static LocalDateTime dateStr2LocalDateTime(String dateStr) {
        LocalDate date = dateStr2LocalDate(dateStr);
        return LocalDateTime.of(date, LocalTime.of(0, 0, 0));
    }

    public static LocalDateTime dateStr2StartLocalDateTime(String dateStr) {
        LocalDate date = dateStr2LocalDate(dateStr);
        return LocalDateTime.of(date, LocalTime.of(0, 0, 0));
    }

    public static LocalDateTime dateStr2EndLocalDateTime(String dateStr) {
        LocalDate date = dateStr2LocalDate(dateStr);
        return LocalDateTime.of(date, LocalTime.of(23, 59, 59));
    }

    public static Integer dateTime2Number(LocalDateTime dateTime) {
        return Integer.valueOf(dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
    }

    public static Integer date2Number(LocalDate date) {
        return Integer.valueOf(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }

    public static Integer date2MonthNumber(LocalDate date) {
        return Integer.valueOf(date.format(DateTimeFormatter.ofPattern("yyyyMM")));
    }

    public static String number2LocalDateStr(Integer number) {
        return LocalDate.parse("" + number, DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
    }

    public static String numberMonth2DateStr(Integer number) {
        int year = number / 100;
        int month = number - number / 100 * 100;
        return year + "-" + (month < 10 ? "0" + month : month);
    }

    public static int getWeek(LocalDate date) {
        return date.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfYear());
    }

    public static LocalDate[] getWeekDays(LocalDate date) {
        return getWeekDays(getYearWeek(date));
    }

    public static int getYearWeek(LocalDate date) {
        int week = date.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfYear());
        Integer year = date.getYear();
        String yearWeek = week < 10 ? year + "0" + week : year + "" + week;
        return Integer.parseInt(yearWeek);
    }

    public static LocalDate[] getWeekDays(Integer yearWeek) {
        int year = yearWeek / 100;
        int weekNo = yearWeek - year * 100;
        LocalDate date = getFirstDayOfWeek(year, weekNo);
        LocalDate startDay;
        LocalDate endDay;
        int weekDay;
        if (1 == weekNo) {
            startDay = date.withDayOfYear(1);
            weekDay = startDay.getDayOfWeek().getValue();
            endDay = date.plusDays(7L - (long)weekDay);
        } else if (53 == weekNo) {
            if (12 == date.plusDays(6L).getMonthValue()) {
                startDay = date;
                endDay = date.plusDays(6L);
            } else {
                endDay = date.plusMonths(1L).withDayOfMonth(1).minusDays(1L);
                weekDay = endDay.getDayOfWeek().getValue();
                startDay = endDay.minusDays((long)weekDay).plusDays(1L);
            }
        } else {
            startDay = date;
            endDay = date.plusDays(6L);
        }

        return new LocalDate[]{startDay, endDay};
    }

    private static LocalDate getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(1, year);
        c.set(2, 0);
        c.set(5, 1);
        if (week == 1) {
            return LocalDate.of(year, Month.JANUARY, 1);
        } else {
            Calendar cal = (GregorianCalendar)c.clone();
            cal.add(5, (week - 1) * 7);
            Date d = getFirstDayOfWeek(cal.getTime());
            return dateStr2LocalDate((new SimpleDateFormat("yyyy-MM-dd")).format(d));
        }
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setTime(date);
        c.set(7, c.getFirstDayOfWeek());
        return c.getTime();
    }

    public static List<Integer> hoursBetween(LocalDateTime sDate, LocalDateTime eDate) {
        ArrayList list;
        for(list = new ArrayList(); sDate.isBefore(eDate) || sDate.equals(eDate); sDate = sDate.plusHours(1L)) {
            String hour = sDate.getHour() < 10 ? "0" + sDate.getHour() : String.valueOf(sDate.getHour());
            String day = sDate.getDayOfMonth() < 10 ? "0" + sDate.getDayOfMonth() : String.valueOf(sDate.getDayOfMonth());
            String month = sDate.getMonthValue() < 10 ? "0" + sDate.getMonthValue() : String.valueOf(sDate.getMonthValue());
            list.add(Integer.parseInt(sDate.getYear() + month + day + hour));
        }

        return list;
    }

    public static List<Integer> daysBetween(String sdate, String edate) {
        LocalDate sd = dateStr2LocalDate(sdate);
        LocalDate ed = dateStr2LocalDate(edate);
        return daysBetween(sd, ed);
    }

    public static List<Integer> daysBetween(LocalDate sdate, LocalDate edate) {
        ArrayList list;
        for(list = new ArrayList(); sdate.isBefore(edate) || sdate.equals(edate); sdate = sdate.plusDays(1L)) {
            String day = sdate.getDayOfMonth() < 10 ? "0" + sdate.getDayOfMonth() : String.valueOf(sdate.getDayOfMonth());
            String month = sdate.getMonthValue() < 10 ? "0" + sdate.getMonthValue() : String.valueOf(sdate.getMonthValue());
            list.add(Integer.parseInt(sdate.getYear() + month + day));
        }

        return list;
    }

    public static List<Integer> weeksBetween(String sdate, String edate) {
        return weeksBetween(dateStr2LocalDate(sdate), dateStr2LocalDate(edate));
    }

    public static List<Integer> weeksBetween(LocalDate sdate, LocalDate edate) {
        List<Integer> list = new ArrayList();
        Integer startYearWeek = getYearWeek(sdate);
        Integer endYearWeek = getYearWeek(edate);

        for(int i = startYearWeek; i <= endYearWeek; ++i) {
            list.add(i);
            int week = i - i / 100 * 100;
            if (week == 53) {
                i = i / 100 * 100 + 100;
            }
        }

        return list;
    }

    public static List<Integer> monthsBetween(String sdate, String edate) {
        return monthsBetween(dateStr2LocalDate(sdate), dateStr2LocalDate(edate));
    }

    public static List<Integer> monthsBetween(LocalDate sdate, LocalDate edate) {
        List<Integer> list = new ArrayList();
        Integer startMonth = date2MonthNumber(sdate);
        Integer endMonth = date2MonthNumber(edate);

        for(int i = startMonth; i <= endMonth; ++i) {
            list.add(i);
            int month = i - i / 100 * 100;
            if (month == 12) {
                i = i / 100 * 100 + 100;
            }
        }

        return list;
    }

    public static List<Integer> yearsBetween(LocalDate sdate, LocalDate edate) {
        return yearsBetween(sdate.getYear(), edate.getYear());
    }

    public static List<Integer> yearsBetween(Integer syear, Integer eyear) {
        List<Integer> list = new ArrayList();

        for(int i = syear; i <= eyear; ++i) {
            list.add(i);
        }

        return list;
    }

    public static List<Integer> yearsBetween(String sdate, String edate) {
        Integer startYear = dateStr2LocalDate(sdate).getYear();
        Integer endYear = dateStr2LocalDate(edate).getYear();
        return yearsBetween(startYear, endYear);
    }

    public static String numberDate2DateStr(Integer number) {
        int year = number / 10000;
        int month = (number - number / 10000 * 10000) / 100;
        int day = number - number / 10000 * 10000 - month * 100;
        return year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
    }
}
