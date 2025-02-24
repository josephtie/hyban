package com.nectux.mizan.hyban.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;


public class Utils {
    
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
    static public Date addingMonth(Date date, int m){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, m);
        return cal.getTime();
    }
    
    // Adding Year to date
    static public Date addingYear(Date date, int y){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, y);
        return cal.getTime();
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
        Date date = Utils.stringToDate(dateEch, "dd/MM/yyyy");
        if(le != null && le !=0){
        String mmyyyy = dateEch.substring(2);
        String dd;
            if(le < 10)
                dd = "0"+le;
            else
                dd=""+le;
            date = Utils.stringToDate(dd+mmyyyy, "dd/MM/yyyy");
            if(Utils.stringToDate(dateEch, "dd/MM/yyyy").compareTo(date) > 0){
                date = Utils.addingDate(date, 30);
                mmyyyy = Utils.dateToString(date, "dd/MM/yyyy").substring(2);
                date = Utils.stringToDate(dd+mmyyyy, "dd/MM/yyyy");
            }
        } 
        return date;
    }
    public static String formatMillierhtml(String montant){
        String brtp,brtp1,brtp2,brtp3,output = null;int jo = 0;
        // if (montant.isEmpty() || montant == "") {
        jo = montant.length();
        if (jo <= 3) {
            output=montant;

        }

        if (jo > 4 && jo <= 5) {
            brtp = montant.substring(0, 1);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(2, 5);
            System.out.println("valuemtbrp" + brtp1);
            output = brtp + brtp1;
        }
        if (jo > 5 && jo <= 6) {
            brtp = montant.substring(0, 2);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(3, 6);
            System.out.println("valuemtbrp" + brtp1);
            output = brtp + brtp1;
        }
        if (jo > 6 && jo <= 7) {
            brtp = montant.substring(0, 3);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(4, 7);
            System.out.println("valuemtbrp" + brtp1);
            output = brtp + brtp1;
        }

        if (jo > 7 && jo <= 9) {
            brtp = montant.substring(0, 1);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(2, 5);
            brtp2 = montant.substring(6, 9);
            System.out.println("valuemtbrp" + brtp2);
            output = brtp + brtp1 + brtp2;
        }
        if (jo > 9 && jo <= 10) {
            brtp = montant.substring(0, 2);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(3, 6);
            System.out.println("valuemtbrp" + brtp1);
            brtp2 = montant.substring(7, 10);
            System.out.println("valuemtbrp" + brtp2);
            output = brtp + brtp1 + brtp2;
        }
        if (jo > 10 && jo <= 11) {
            brtp = montant.substring(0, 3);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(4, 7);
            System.out.println("valuemtbrp" + brtp1);
            brtp2 = montant.substring(8, 11);
            System.out.println("valuemtbrp" + brtp2);
            output = brtp + brtp1 + brtp2;
        }
        if (jo > 11 && jo <= 12) {
            brtp = montant.substring(0, 1);
            System.out.println("valuemtbrp" + brtp);
            brtp1 = montant.substring(3, 5);
            System.out.println("valuemtbrp" + brtp1);
            brtp2 = montant.substring(6, 9);
            System.out.println("valuemtbrp" + brtp2);
            brtp3 = montant.substring(10, 12);
            System.out.println("valuemtbrp" + brtp3);
            output = brtp + brtp1 + brtp2 + brtp3;
        }

        //  }
        return output;
    }
	public static String formattingAmount(Double money){
    	if(money == null) money = 0.0;
        Locale locale = new Locale("fr", "FR");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        String sMoney = numberFormat.format(money);
        return sMoney;
    }
	
//	public static void sendEmail(String receiver, String subject, String content) throws Exception {
//        final String username = new String("smartpaie@betheltechnolgies.com");
//        final String password = new String("aurielle80");
//
//        String smtpHost = "pro1.mail.ovh.net";
//        String from = "smartpaie@betheltechnolgies.com";
//
//        Properties props = new Properties();
//
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", new String("pro1.mail.ovh.net"));
//        props.put("mail.smtp.port", new String("587"));
//        props.put("mail.smtp.user", from);
//        props.put("mail.smtp.password", password);
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.debug", "true");
//
//	    Session session = Session.getDefaultInstance(props);
//	    session.setDebug(true);
//
//	    MimeMessage message = new MimeMessage(session);
//	    message.setFrom(new InternetAddress(from));
//	    message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
//	    message.setSubject(subject);
//	    message.setText(content);
//
//	    Transport tr = session.getTransport("smtp");
//	    tr.connect(smtpHost, username, password);
//	    message.saveChanges();
//
//	    // tr.send(message);
//	    /** Genere l'erreur. Avec l authentification, oblige d utiliser sendMessage meme pour une seule adresse... */
//
//	    tr.sendMessage(message,message.getAllRecipients());
//	    tr.close();
//	}

}
