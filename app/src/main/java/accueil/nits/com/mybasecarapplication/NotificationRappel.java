package accueil.nits.com.mybasecarapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class NotificationRappel extends Activity {
	
	DatePicker dp;
	TimePicker tp;
	Calendar cal;
	Sms_vocale.Receiver reciver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notificationrappel);
		
	}
	public void datetime(DatePicker dp2, TimePicker tp2) {
		cal = Calendar.getInstance(); 			
		cal.set(Calendar.MONTH, dp2.getMonth());
		cal.set(Calendar.YEAR, dp2.getYear());				
		cal.set(Calendar.DAY_OF_MONTH, dp2.getDayOfMonth());
		cal.set(Calendar.HOUR_OF_DAY, tp2.getCurrentHour());
		cal.set(Calendar.MINUTE, tp2.getCurrentMinute());
		  Intent alarmintent = new Intent(getApplicationContext(), RappelReceiver.class);
		  alarmintent.putExtra("title","Title of our Notification");
		  alarmintent.putExtra("note","Description of our  Notification");
		  PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0,
		  alarmintent,PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
		  AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		  am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);		
	}
}
