package com.nectux.mizan.hyban.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodsShared {
	
	public String dateToString(Date date, String sFormat){
		return new SimpleDateFormat(sFormat).format(date);
	}
	
	public Date stringToDate(String sDate, String sFormat) throws Exception {
        return new SimpleDateFormat(sFormat).parse(sDate);
	}
	
	public java.sql.Date stringToDateSql(String sDate, String sFormat) throws Exception {
		Date utilDate = new  SimpleDateFormat(sFormat).parse(sDate);
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		return sqlDate;
	}
	

}
