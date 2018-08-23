package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ConversionDate {
	public java.sql.Date conversion(String str) {

		java.sql.Date date = null;

		if (!(str.equals(""))) {
			DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			// 日付解析を厳密に行う設定にする
			df1.setLenient(false);
			try {
				df1.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}

			//発売日・左をDate型に変換
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");

			try {
				java.util.Date day1 = sdf1.parse(str);
				date = new java.sql.Date(day1.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else {
			return null;
		}

		return date;

	}
}
