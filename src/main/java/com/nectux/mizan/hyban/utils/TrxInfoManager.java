package com.nectux.mizan.hyban.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class TrxInfoManager {
	
	public static String formattingAmount(Double money){
    	if(money == null) money = 0.0;
        Locale locale = new Locale("fr", "FR");
        NumberFormat numberFormat = NumberFormat.getInstance(locale);
        String sMoney = numberFormat.format(money);
        return sMoney;
    }

}
