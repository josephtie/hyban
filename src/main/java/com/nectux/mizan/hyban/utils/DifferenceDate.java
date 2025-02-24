package com.nectux.mizan.hyban.utils;

import java.util.Calendar; 
import java.util.Date;

public class DifferenceDate {
	
	public static double valAge(Date dateJour, Date dateNaissance){ 
		double age = 0;
		 // date du jour
        Calendar aujourdhui = Calendar.getInstance();
        aujourdhui.setTime(dateJour);
     // obtention des éléments de la date du jour
        int  a_s = aujourdhui.get(Calendar.YEAR);
        int  m_s = aujourdhui.get(Calendar.MONTH);
        int j_s = aujourdhui.get(Calendar.DAY_OF_MONTH);
        //int num_jour = aujourdhui.get(Calendar.DAY_OF_WEEK);
     //  date de naissance
        Calendar datedenaissance = Calendar.getInstance();
        datedenaissance.setTime(dateNaissance);
     //  obtention des éléments de la date de naissance
        int a = datedenaissance.get(Calendar.YEAR);
        int m = datedenaissance.get(Calendar.MONTH);
        int j = datedenaissance.get(Calendar.DAY_OF_MONTH);
        //int num_jour_dn = datedenaissance.get(Calendar.DAY_OF_WEEK);
        age = age(a_s, m_s, j_s, a, m, j);
        System.out.println("########################### age ################ "+age);
		return age;
	}
	
	public static double age(int a_s, int m_s, int j_s,int a, int m, int j){ 
		//Calcul de l'age
		Calendar calendar_Sys = Calendar.getInstance () ; 
		Calendar calendar_naiss = Calendar.getInstance () ; 
		calendar_Sys.set ( a_s , m_s, j_s ) ; 
		calendar_naiss.set ( a , m , j ) ; 

		long milliseconds1 = calendar_Sys.getTimeInMillis () ; 
		long milliseconds2 = calendar_naiss.getTimeInMillis () ; 
		long diff = milliseconds1 - milliseconds2; 

		long diffDays = diff / ( 24 * 60 * 60 * 1000 ) ; 
		double age = diffDays / 365.00; 
		return age; 

	}
	public static long DifferenceHeure (Date startDate, Date endDate) {
		long different = endDate.getTime() - startDate.getTime();
		System.out.println("startDate : " + startDate);
		System.out.println("endDate : "+ endDate);
		System.out.println("different : " + different);
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;

		System.out.printf(
				"%d hours, %d minutes, %d seconds%n",
				elapsedHours, elapsedMinutes, elapsedSeconds);
			return elapsedHours;
	}

}
