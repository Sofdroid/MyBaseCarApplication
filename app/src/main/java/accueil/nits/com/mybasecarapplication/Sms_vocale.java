package accueil.nits.com.mybasecarapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;


@SuppressWarnings("deprecation")
public class Sms_vocale extends Activity implements OnItemSelectedListener, OnClickListener, OnInitListener {

	private Button 			mButton;
	private Spinner 		mSpinner;

	private TextToSpeech 	mTTS;

	String contenue_sms;

	public class Receiver extends BroadcastReceiver{

		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "SMS", Toast.LENGTH_SHORT).show();
			if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
				Bundle bundle = intent.getExtras();
				if(bundle != null){
					Object[] pdus = (Object[]) bundle.get("pdus");
					SmsMessage[] messages = new SmsMessage[pdus.length];
					for(int i = 0; i<pdus.length; i++)
						messages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
					for(SmsMessage message : messages){			
						// message.getOriginatingAddress() retourne num_tel
						// message.getMessageBody() retourne le contenue de sms
						contenue_sms = message.getMessageBody();
					}		
				}											
			}			
		}
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_vocale);

		SmsManager smsManager = SmsManager.getDefault();
		Receiver receiver = new Receiver();
		registerReceiver(receiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));

		mButton=(Button)findViewById(R.id.btnparler);
		mButton.setOnClickListener(this);

		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,Locale.getAvailableLocales());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		mSpinner=(Spinner)findViewById(R.id.spinnerlocale);
		mSpinner.setAdapter(adapter);
		mSpinner.setOnItemSelectedListener(this);

		Intent checkIntent  = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, 0);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0)
		{
			if(resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) 
			{
				mTTS=new TextToSpeech(this,this);
			}
			else{
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}

	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.btnparler:
			mTTS.speak(contenue_sms, TextToSpeech. QUEUE_ADD, null);
			break;
		}	
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if(mTTS!=null)
			mTTS.setLanguage(Locale.getAvailableLocales()[position]);	
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {}

	@Override
	public void onInit(int status) {
		mButton.setEnabled(true);
		mSpinner.setEnabled(true);
	}
}
