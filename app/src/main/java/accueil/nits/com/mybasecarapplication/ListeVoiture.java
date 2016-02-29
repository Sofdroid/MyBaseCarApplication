package accueil.nits.com.mybasecarapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

public class ListeVoiture extends AppCompatActivity {
	private ListView listeview;
	private Button boutonAjouter;
	private VoitureAdapter adapter;
	private VoitureRepository voitureRepository;
	public Button buttonAdd;
	public Button buttonCancel;
	private List<Voiture> liste;
	private long rowID;
	String nummat;
	String nom ;
	String marque ;
	String type ;
	String note ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listvoiture);

		// Bouton
		boutonAjouter = (Button) findViewById(R.id.buttonAjoutervoiture);
		boutonAjouter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Ajoutervoiture();
			}
		});

		// Listview
		listeview = (ListView) findViewById(R.id.listViewvoiture);
		voitureRepository = new VoitureRepository(this);
		voitureRepository.Open();
		adapter = new VoitureAdapter(this, voitureRepository.GetAll());
		voitureRepository.Close();
		listeview.setAdapter(adapter);
		registerForContextMenu(listeview);
		listeview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 startActivity(new Intent(ListeVoiture.this, IconTextTabsActivity.class));
			}
		});
	}

	/**
	 * Ajout d'une voiture
	 */
	private void Ajoutervoiture() {
 		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialogaddvoiture);
		dialog.setTitle("  Ajouter Voiture");

		AutoCompleteTextView autocompletetextview;

		String[] datamarque  = {
				"Audi","BMW","Marcedes","Volkswagen","RENAULT","PEUGOT","NISSAN","TOYOTA",
				"LANDROVER","CITROEN","OPEL","FIAT","FORD","PORSCHE","BUGATTI","CHEVROLET",
				"Cadillac","VOLVO","Jeep","MAZDA","KIA","SUZUKI","HYUNDAI","DACIA",
				"CORVET","FERRARI","GMC","HONDA","IVECO","ISUZU","JAGUAR","Lamborghini",	
				"Lotus","Maserati","Mega","MINI","Maybach","SKODA",
		};

		autocompletetextview = (AutoCompleteTextView)findViewById(R.id.spinmarque);        
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,datamarque);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		//oautocompletetextview.setAdapter(adapter);  

		buttonAdd = (Button) dialog.findViewById(R.id.buttonAdd);
		buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);

		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Insertion du voiture
				voitureRepository.Open();

				nummat = ((EditText) dialog.findViewById(R.id.editTextnummat)).getText().toString();
				nom =(((EditText) dialog.findViewById(R.id.editTextnomvoiture)).getText().toString());
				marque =(((AutoCompleteTextView) dialog.findViewById(R.id.spinmarque)).getText().toString());
				type =(((EditText) dialog.findViewById(R.id.editTexttype)).getText().toString());
				note =(((EditText) dialog.findViewById(R.id.editTextnote)).getText().toString());

				if ((verifier(nummat))& (verifier(nom))){
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom et num�ro de matricule de la voiture !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (verifier(nom)){
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (verifier(nummat)){
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le num�ro de matricule !", Toast.LENGTH_LONG);
					zz.show();
				}
				
				else if ((voitureRepository.nummat_existe(nummat)==0)&(voitureRepository.cleetrangere(nom)==0)){
					if (voitureRepository.cleetrangere(nom)==0) {
					voitureRepository.Save(new Voiture(nummat, nom,marque,type,note));
					voitureRepository.Close();
					UpdateAdapter();
					dialog.dismiss();
				}}
				else {
					Toast zz=Toast.makeText(getApplicationContext(),"Voiture existe !", Toast.LENGTH_LONG);
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

	/**
	 * supprimer d'une voiture
	 */
	public void Supprimer_voiture(int id) {
		dialog_supprimer_voiture(id);
	}

	/**
	 * modifier d'une voiture
	 */
	private void Modifiervoiture(final int idm) {
 		final Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.dialogaddvoiture);
		dialog.setTitle("  Modifier Voiture");

		voitureRepository.Open();

		adapter = new VoitureAdapter(this, voitureRepository.GetAllbyid(idm));

		nummat = ((EditText) dialog.findViewById(R.id.editTextnummat)).getText().toString();
		nom =(((EditText) dialog.findViewById(R.id.editTextnomvoiture)).getText().toString());
		marque =(((AutoCompleteTextView) dialog.findViewById(R.id.spinmarque)).getText().toString());
		type =(((EditText) dialog.findViewById(R.id.editTexttype)).getText().toString());
		note =(((EditText) dialog.findViewById(R.id.editTextnote)).getText().toString()); 

		buttonAdd = (Button) dialog.findViewById(R.id.buttonAdd);
		buttonCancel = (Button) dialog.findViewById(R.id.buttonCancel);
		buttonAdd.setText("Modifier");
		buttonAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Insertion du voiture
				String nummat = ((EditText) dialog.findViewById(R.id.editTextnummat)).getText().toString();
				String nom =(((EditText) dialog.findViewById(R.id.editTextnomvoiture)).getText().toString());
				String marque =(((AutoCompleteTextView) dialog.findViewById(R.id.spinmarque)).getText().toString());
				String type =(((EditText) dialog.findViewById(R.id.editTexttype)).getText().toString());
				String note =(((EditText) dialog.findViewById(R.id.editTextnote)).getText().toString());

				voitureRepository.Open();

				if ((verifier(nummat))& (verifier(nom))){
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom et num�ro de matricule de la voiture !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (verifier(nom)){
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (verifier(nummat)){
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le numero de matricule !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if ((voitureRepository.nummat_existe(nummat)== idm)||(voitureRepository.nummat_existe(nummat) == 0)){
					voitureRepository.Open();
					voitureRepository.Update(idm ,new Voiture(nummat, nom,marque,type,note));
					UpdateAdapter();
					voitureRepository.Close();
					dialog.dismiss();
				}
				else {
					Toast zz=Toast.makeText(getApplicationContext(),"Voiture existe !", Toast.LENGTH_LONG);
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
//actiliser la listeview
	private void UpdateAdapter() {
		voitureRepository.Open();
		adapter.setvoiture(voitureRepository.GetAll());
		voitureRepository.Close();
		adapter.notifyDataSetChanged();
	}

	// Cr�ation de la alert  dialogue
	private void dialog_supprimer_voiture(final int id) {
		AlertDialog.Builder alert = new AlertDialog.Builder(ListeVoiture.this);
		alert.setTitle("Supprimer"); 
		alert.setMessage("Ce voiture sera supprimee d�finitivement ");
		alert.setPositiveButton("Supprimer",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int button) { 
				voitureRepository.Open();
				AsyncTask<Long, Object, Object> deleteTask =new AsyncTask<Long, Object, Object>() {
					@Override
					protected Object doInBackground(Long... params) {
						voitureRepository.Delete(id);
						return null;
					} 
					@Override
					protected void onPostExecute(Object result) {
						finish(); 
					}};
					deleteTask.execute(new Long[] { rowID });               
			}});
		alert.setNegativeButton("Annuler", null).show();
		voitureRepository.Close();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.menu_voiture, menu);
		menu.setHeaderIcon(android.R.drawable.ic_dialog_info);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.itemDelete:

			Supprimer_voiture((int) info.id);
			UpdateAdapter();
			break;
		case R.id.itemmodifvoit:
			Modifiervoiture((int) info.id);	
			break;
		}
		return super.onContextItemSelected(item);
	}

//	@Override
//	protected void onDestroy() {
//		voitureRepository.Close();
//		super.onDestroy();
//	}
}