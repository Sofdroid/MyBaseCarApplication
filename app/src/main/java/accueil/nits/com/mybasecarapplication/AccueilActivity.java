/**
* Nom de l'application : NitsCar
* Developpeur: AZOUZI sofien
* Societe:  Nearshore It Solutions(NITS)
* Ann�es : 2012/2013
*/




package accueil.nits.com.mybasecarapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class AccueilActivity extends  Activity implements OnClickListener{

	Button bvoiture,bservice,brappel,bcarburant;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accueil);

		bvoiture =((Button)this.findViewById(R.id.bvoiture));
		bservice =((Button)this.findViewById(R.id.bservice));
		brappel =((Button)this.findViewById(R.id.brappel));
		bcarburant =((Button)this.findViewById(R.id.bconsommation));

		TranslateAnimation trans1 = new TranslateAnimation(0, 0, 0, 0);
		trans1.setStartOffset(320);
		trans1.setFillAfter(true);
		trans1.setDuration(1000);
		this.findViewById(R.id.bvoiture).startAnimation(trans1);

		TranslateAnimation trans4 = new TranslateAnimation(0, 0, 480, 0);
		trans4.setStartOffset(320);
		trans4.setFillAfter(true);
		trans4.setDuration(1500);
		this.findViewById(R.id.bconsommation).startAnimation(trans4);

		TranslateAnimation trans2 = new TranslateAnimation(0,0,480,0);
		trans2.setStartOffset(320);
		trans2.setFillAfter(true);
		trans2.setDuration(2000);
		this.findViewById(R.id.bservice).startAnimation(trans2);

		TranslateAnimation trans3 = new TranslateAnimation(0, 0, 480, 0);
		trans3.setStartOffset(320);
		trans3.setFillAfter(true);
		trans3.setDuration(2500);
		this.findViewById(R.id.brappel).startAnimation(trans3);

		bvoiture.setOnClickListener(this);
		bservice.setOnClickListener(this);
		brappel.setOnClickListener(this);
		bcarburant.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.bvoiture) 
		{
			Intent intent1 = new Intent(this,ListeVoiture.class);
			this.startActivityForResult(intent1, 1000);	
		}
		else if (v.getId()==R.id.bservice) {
			Intent intent2 = new Intent (this,ListeService.class);
			this.startActivityForResult(intent2, 2000);	
		}
		else if (v.getId()==R.id.bconsommation) {
			Intent intent3 = new Intent (this,ListeConsommationDeCarburant.class);
			this.startActivityForResult(intent3, 3000);	
		}
		else 
		{
			Intent intent4 = new Intent (this,ListeRappel.class) ;
			this.startActivityForResult(intent4, 4000);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu mm = menu.addSubMenu(0,0,0, "SMS");
		SubMenu m1 = menu.addSubMenu(0,2,0, "Quitter");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{  	
		case 0: 	
			Intent intent11 = new Intent(this,Sms_vocale.class);
			this.startActivityForResult(intent11, 1003);
			break;

		case 2:
			System.exit(0);
			break;
		}
		return true;
	}
}
