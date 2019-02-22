package rpc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {
	public static String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(new Date());
		return dateNowStr;
	}
}
