package cn.rushmedia.jay.tvshow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeDifference {
	
	 public String getTimeDiffence(long i) throws Exception{
			SimpleDateFormat sDateFormat= new  SimpleDateFormat("yyyy-MM-dd    hh:mm:ss");     
			Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
			String datetime = sDateFormat.format(dateAndTime.getTimeInMillis());
			long currentime =sDateFormat.parse(datetime).getTime();
			long timeDiffent =currentime -i;
			long day=timeDiffent/(24*60*60*1000);
			long hour=(timeDiffent/(60*60*1000)-day*24);
			long min=((timeDiffent/(60*1000))-day*24*60-hour*60);
			long s=(timeDiffent/1000-day*24*60*60-hour*60*60-min*60);
			if(day>0){
				      return ""+day+"天"+hour+"小时"+min+"分"+s+"秒"+"前";
			}else if(day<=0){
				
					  return ""+hour+"小时"+min+"分"+s+"秒"+"前";
			}
			 return null;
	 }
	
    
}
