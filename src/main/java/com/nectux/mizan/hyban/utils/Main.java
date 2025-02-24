package com.nectux.mizan.hyban.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
	//	String encryptAmount = SecurityService.encrypt("15000");
	//	String decryptAmount = SecurityService.decrypt(encryptAmount);
		
	//	System.out.println("ENCRYPTER : " + encryptAmount);
	//	System.out.println("DECRYPTER : " + decryptAmount);

		int compteur = 0;
        Calendar calendar1 = new GregorianCalendar();
        calendar1.set(Calendar.YEAR, 2016);
        calendar1.set(Calendar.MONTH, 8);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        Date date1 = calendar1.getTime();
 
        //2006-08-15
        Calendar calendar2 = new GregorianCalendar();
        calendar2.set(Calendar.YEAR, 2016);
        calendar2.set(Calendar.MONTH, 8);
        calendar2.set(Calendar.DAY_OF_MONTH, 8);
        Date date2 = calendar2.getTime();
 
        //Difference
		long CONST_DURATION_OF_DAY = 1000l * 60 * 60 * 24;
        long diff = Math.abs(date2.getTime() - date1.getTime());
        int numberOfDay = (int) ((long)diff/CONST_DURATION_OF_DAY)-1;
        System.out.println("Le nombre de jour est : " + numberOfDay);
         for(int i=0;i<=numberOfDay;i++){
             if(calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar1.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                 compteur++; 
             calendar1.add(Calendar.DAY_OF_MONTH, 1);
         }
        System.out.println("Le nombre de jour ouvres est : " + compteur);

	}

}
