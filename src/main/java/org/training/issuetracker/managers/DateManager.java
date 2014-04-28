package org.training.issuetracker.managers;

import java.util.Calendar;

/**
 * Class, used to operating with dates and time
 * @author Dzmitry Salnikau
 * @since 27.01.2014
 */
public final class DateManager {

	public final static int DAY = 0;
    public final static int MONTH = 1;
    public final static int YEAR = 2;

    public final static int MAX_MONTH = 12;

    public final static String DATE_SEPARATOR = ".";
    public final static String TIME_SEPARATOR = ":";

    /**
     * @return String, containing current date
     */
    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = 1 + calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return formatDateParts(day, month, year);
    }
    
    /**
     * @return String, containing current time
     */
    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        
        return formatTimeParts(hour, minute);
    }

    /**
     * Checks whether the new date is more than the old
     * @param newDate - String. date in format "MM/DD/YEAR"
     * @param lastDate - String. date in format "MM/DD/YEAR"
     * @return boolean - true, if newDate > lastDate
     */
    public static boolean isMoreThanLast(String newDate, String lastDate){
        boolean isMoreThanLast = false;

        if(!newDate.equals("") && lastDate.equals("")){
            isMoreThanLast = true;
        } else{
            String[] newDateParts = newDate.split(DATE_SEPARATOR);
            String[] lastDateParts = lastDate.split(DATE_SEPARATOR);

            int newYear = Integer.valueOf(newDateParts[YEAR]);
            int lastYear = Integer.valueOf(lastDateParts[YEAR]);
            if(newYear > lastYear){
                isMoreThanLast = true;
            } else if(newYear == lastYear){
                int newMonth = Integer.valueOf(newDateParts[MONTH]);
                int lastMonth = Integer.valueOf(lastDateParts[MONTH]);
                if(newMonth > lastMonth){
                    isMoreThanLast = true;
                } else if(newMonth == lastMonth){
                    int newDay = Integer.valueOf(newDateParts[DAY]);
                    int lastDay = Integer.valueOf(lastDateParts[DAY]);
                    if(newDay > lastDay){
                        isMoreThanLast = true;
                    }
                }
            }
        }

        return isMoreThanLast;
    }

    /**
     * Formats received parts of date to the format "DD.MM.YEAR"
     * @param intDay - day
     * @param intMonth - month
     * @param intYear - year
     * @return String - formatted date
     */
    public static String formatDateParts(int intDay, int intMonth, int intYear){
        String day = String.valueOf(intDay);
        String month = String.valueOf(intMonth);
        String year = String.valueOf(intYear);

        // Day should be in 01-31 format
        if(day.length() == 1){
            day = "0" + day;
        }

        // Month should be in 01-12 format
        if(month.length() == 1){
            month = "0" + month;
        }

        return day + DATE_SEPARATOR + month + DATE_SEPARATOR + year;
    }
    
    /**
     * Formats received parts of time to the format "HH:MM"
     * @param intHour - hour
     * @param intMinute - minute
     * @return String - formatted time
     */
    public static String formatTimeParts(int intHour, int intMinute){
        String hour = String.valueOf(intHour);
        String minute = String.valueOf(intMinute);

        // Hour should be in 01-24 format
        if(hour.length() == 1){
        	hour = "0" + hour;
        }

        // Minute should be in 01-59 format
        if(minute.length() == 1){
        	minute = "0" + minute;
        }

        return hour + TIME_SEPARATOR + minute;
    }
}

