package accueil.nits.com.mybasecarapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ListeRappel extends Activity {
	DatePicker dp;
	private ListView listeView;
	private Button boutonAjouter;
	private RappelAdapter adapter;
	private RappelRepository rappelRepository;
	String nom ;
	String note ;
	String voiture ;
	public Button buttonAdd;
	public Button buttonCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listerappel);

		// Bouton
		boutonAjouter = (Button) findViewById(R.id.bntajouterrappel);
		boutonAjouter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Ajouterrappel();
			}
		});

		// Listview
		listeView = (ListView) findViewById(R.id.listViewrappel);
		rappelRepository = new RappelRepository(this);
		voitRepository = new VoitureRepository(this);
		rappelRepository.Open();
		adapter = new RappelAdapter(this, rappelRepository.GetAll());
		rappelRepository.Close();
		listeView.setAdapter(adapter);
		registerForContextMenu(listeView);
	}

	NotificationManager notificationManager;
	AlarmManager alarms;
	Calendar cal,cal2;
	private VoitureRepository voitRepository;

	/**
	 * Ajout d'un rappel
	 */
	private void Ajouterrappel() {

 		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialogaddrappel);
		dialog.setTitle(" Ajouter Rappel");

		buttonAdd = (Button) dialog.findViewById(R.id.buttonenregistrerrappel);
		buttonCancel = (Button) dialog.findViewById(R.id.buttonannulerrappel);
		notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancelAll();  
		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dp = (DatePicker) dialog.findViewById(R.id.datePickerdaterappel);
				cal = Calendar.getInstance(); 			
				cal.set(Calendar.MONTH,  dp.getMonth());
				cal.set(Calendar.YEAR, dp.getYear());				
				cal.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());

				Calendar cal2 = Calendar.getInstance();

				if (cal.getTimeInMillis() > cal2.getTimeInMillis()) {

					SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
					String dateNow = formatter.format(cal.getTime());

					// Insertion du voiture
					voitRepository.Open();
					String voiture =(((EditText) dialog.findViewById(R.id.editTextrappelvoiture)).getText().toString());
					int s = voitRepository.cleetrangere(voiture);
					voitRepository.Close();

					String nom = ((EditText) dialog.findViewById(R.id.editnomrappel)).getText().toString();
					String note =(((EditText) dialog.findViewById(R.id.editnoterappel)).getText().toString());

					if (voiture.trim().length() <= 0){
						Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
						zz.show();
					}
					else if (s == 0) {
						Toast zz=Toast.makeText(getApplicationContext(),"Cette voiture n'existe pas !", Toast.LENGTH_LONG);
						zz.show();
					}
					else {
						rappelRepository.Open();
						rappelRepository.Save(new Rappel(dateNow,nom,note,s));
						datetime(dp);
						// not();
						UpdateAdapter();
						dialog.dismiss();
					}
				}
				else {
					Toast zz=Toast.makeText(getApplicationContext(),"Date non valide !", Toast.LENGTH_LONG);
					zz.show();
				}
			}
		});

		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clic sur le bouton annuler
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void datetime(DatePicker dp2) { 
		cal = Calendar.getInstance(); 			
		cal.set(Calendar.MONTH,  dp2.getMonth());
		cal.set(Calendar.YEAR, dp2.getYear());				
		cal.set(Calendar.DAY_OF_MONTH, dp2.getDayOfMonth());
		Intent alarmintent = new Intent(getApplicationContext(), RappelReceiver.class);
		alarmintent.putExtra("title","Title of our Notification");
		alarmintent.putExtra("note","Description of our  Notification");
		PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), 0,
				alarmintent,PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), sender);
	}
	void not() {
		long ii = System.currentTimeMillis();
		@SuppressWarnings("deprecation")
		Notification nf = new Notification(android.R.drawable.ic_lock_idle_alarm,"Rappel",ii);
		Toast zz=Toast.makeText(getApplicationContext(),"Rappel" , Toast.LENGTH_LONG);
		zz.show();
		nf.contentView = new RemoteViews(getPackageName(),R.layout.notificationrappel);
		nf.vibrate = new long[]{0,100,25,100};
		nf.sound = Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.good);
		nf.ledOffMS = 25;
		nf.ledOnMS  = 100;
		nf.ledARGB  = Color.RED;
		nf.flags    = nf.flags | Notification.FLAG_SHOW_LIGHTS;
		Intent activity = new Intent(this,NotificationRappel.class);
		PendingIntent pendingintent = PendingIntent.getActivity(this, 0, activity, 0);
		nf.contentIntent = pendingintent;
		notificationManager.notify(-1, nf);
	}

	/**
	 * Modifier d'un rappel
	 */
	private void ModifierItem(final int idm) {

 		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialogaddrappel);
		dialog.setTitle("Modifier Rappel");
		buttonAdd = (Button) dialog.findViewById(R.id.buttonenregistrerrappel);
		buttonCancel = (Button) dialog.findViewById(R.id.buttonannulerrappel);

		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dp = (DatePicker) dialog.findViewById(R.id.datePickerdaterappel);
				cal = Calendar.getInstance(); 			
				cal.set(Calendar.MONTH,  dp.getMonth());
				cal.set(Calendar.YEAR, dp.getYear());				
				cal.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());

				datetime(dp);

				Calendar cal2 = Calendar.getInstance();

				if (cal.getTimeInMillis() > cal2.getTimeInMillis()) {

					SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
					String dateNow = formatter.format(cal.getTime());

					// Insertion du voiture
					voitRepository.Open();
					String voiture =(((EditText) dialog.findViewById(R.id.editTextrappelvoiture)).getText().toString());
					int s = voitRepository.cleetrangere(voiture);
					voitRepository.Close();

					String nom = ((EditText) dialog.findViewById(R.id.editnomrappel)).getText().toString();
					String note =(((EditText) dialog.findViewById(R.id.editnoterappel)).getText().toString());

					if (voiture.trim().length() <= 0){

						Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
						zz.show();
					}
					else if (s == 0) {
						Toast zz=Toast.makeText(getApplicationContext(),"Cette voiture n'existe pas !", Toast.LENGTH_LONG);
						zz.show();
					}
					else {
						rappelRepository.Open();
						rappelRepository.Update(idm,new Rappel(dateNow,nom,note,s));
						datetime(dp);
						//not();
						UpdateAdapter();
						dialog.dismiss();
					}
				}
				else {
					Toast zz=Toast.makeText(getApplicationContext(),"Date non valide !", Toast.LENGTH_LONG);
					zz.show();
				}
			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clic sur le bouton annuler
				dialog.dismiss();
			}
		});

		dialog.show();
	}
 

	boolean verifier (String s) {
		if (s.trim().length() <=0)
			return true;
		else
			return false;
	}
	
	private void UpdateAdapter() {
		rappelRepository.Open();
		adapter.setrappel(rappelRepository.GetAll());
		rappelRepository.Close();
		adapter.notifyDataSetChanged();
	}

	public void DeleteItem(int id) {
		rappelRepository.Open();
		rappelRepository.Delete(id);
		rappelRepository.Close();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.menu_rappel, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {

		case R.id.itemDeleterappel:
			DeleteItem((int) info.id);
			UpdateAdapter();
			Toast.makeText(this, "Rappel supprim", Toast.LENGTH_SHORT).show();
			break;
		case R.id.itemmodifierrappel:
			ModifierItem((int) info.id);	
			break;
		}
		return super.onContextItemSelected(item);
	}
	@Override
	protected void onDestroy() {
		rappelRepository.Close();
		super.onDestroy();
	}	
}