package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConversionDate {
	public java.sql.Date conversion(String str) throws ParseException {

		java.sql.Date date = null;

		 if (!(str.equals(""))) {
	            DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
	            // 日付解析を厳密に行う設定にする
	            df1.setLenient(false);
	            java.util.Date day1 = df1.parse(str);
	            date = new java.sql.Date(day1.getTime());
	        }else {
	            return null;
	        }

		return date;

	}
}
