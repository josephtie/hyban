/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nectux.mizan.hyban.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//import org.joda.time.DateTime;
//import org.joda.time.Months;

/**
 *
 * @author boris
 */
public class DateManager {
    
    // Date To String
    static public String dateToString(Date date, String sFormat){
    	String value = new String();
    	if(date != null)
    		value = new SimpleDateFormat(sFormat).format(date);
        return value;
    }

    // String To Date
    static public Date stringToDate(String sDate, String sFormat) throws Exception {
    	Date value = null;
    	if(sDate != null && sDate != "")
    		value = new SimpleDateFormat(sFormat).parse(sDate);
        return value;
    }
    
    // Adding date to current date
    static public Date addingDate(int d) throws Exception{
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, d);	//Adding d day to current date
        String newdate = dateformat.format(cal.getTime());
        return stringToDate(newdate, "dd/MM/yyyy");
    }
    
    // Adding date to date
    static public Date addingDate(Date date, int d) throws Exception{
    	SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, d);	//Adding d day to current date
        String newdate = dateformat.format(cal.getTime());
        return stringToDate(newdate, "dd/MM/yyyy");
    }
    
    // Adding Month to date
    static public Date addingMonth(Date date, int m) throws Exception{
    	SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, m);	//Adding d day to current date
        String newdate = dateformat.format(cal.getTime());
        return stringToDate(newdate, "dd/MM/yyyy");
    }
    
    // Adding Year to date
    static public Date addingYear(Date date, int y){
        return new Date();
    }
    
    // Get day in current date
    static public int getDay(){
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return localCalendar.get(Calendar.DATE);
    }
    
    // Get month in current date
    static public int getMonth(){
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return localCalendar.get(Calendar.MONTH) + 1;
    }
    
    // Get year in current date
    static public int getYear(){
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        return localCalendar.get(Calendar.YEAR);
    }
    
    // Count month beetween date
    /*static public int countMonthBetween2Date(Date startDate, Date endDate){
		DateTime start = new DateTime(startDate.getTime());
		DateTime end= new DateTime(endDate.getTime());
		return Months.monthsBetween(start, end).getMonths();
    }*/
    
    // Calculer date echeance
    public static Date calculerDateEcheance(String dateEch, Integer le) throws Exception{
        Date date = DateManager.stringToDate(dateEch, "dd/MM/yyyy");
        if(le != null && le !=0){
        String mmyyyy = dateEch.substring(2);
        String dd;
            if(le < 10)
                dd = "0"+le;
            else
                dd=""+le;
            date = DateManager.stringToDate(dd+mmyyyy, "dd/MM/yyyy");
            if(DateManager.stringToDate(dateEch, "dd/MM/yyyy").compareTo(date) > 0){
                date = DateManager.addingDate(date, 30);
                mmyyyy = DateManager.dateToString(date, "dd/MM/yyyy").substring(2);
                date = DateManager.stringToDate(dd+mmyyyy, "dd/MM/yyyy");
            }
        } 
        return date;
    }
    public static Date StringToDateWithouthour2(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.parse(dateString);

    }
    public static Date StringToDateWithouthour3(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(dateString);

    }

    public static int dateDifference(Date date1, Date date2){
		long CONST_DURATION_OF_DAY = 1000l * 60 * 60 * 24;
		long diff = Math.abs(date2.getTime() - date1.getTime());
		int numberOfDay = (int) ((long)diff/CONST_DURATION_OF_DAY) - 1;
		if(numberOfDay < 0) numberOfDay = 0;
		return numberOfDay;
	}
    
}
