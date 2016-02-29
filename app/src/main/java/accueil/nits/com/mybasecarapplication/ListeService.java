package accueil.nits.com.mybasecarapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ListeService extends Activity  {

	private ListView listeViewservice;
	private Button boutonAjouter;
	private ServiceAdapter adapter;
	private ServiceRepository serviceRepository;
	public Button buttonAdd;
	public Button buttonCancel;
	public String type;
	public int prix ;
	public String note ;
	public String voiture;


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listeservice);
		// Bouton
		boutonAjouter = (Button) findViewById(R.id.bntajouterservice);
		boutonAjouter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Ajouterservice();
			}
		});
		// Listview
		listeViewservice = (ListView) findViewById(R.id.listViewservice);
		serviceRepository = new ServiceRepository(this);
		voitRepository = new VoitureRepository(this);
		serviceRepository.Open();
		adapter = new ServiceAdapter(this, serviceRepository.GetAll());
		serviceRepository.Close();
		listeViewservice.setAdapter(adapter);
		registerForContextMenu(listeViewservice);
	}
	private VoitureRepository voitRepository;

	/**
	 * Ajout service
	 */
	private void Ajouterservice() {

 		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialogaddservice);
		dialog.setTitle("Ajouter  Service");
		buttonAdd = (Button) dialog.findViewById(R.id.buttonenregistrerservice);
		buttonCancel = (Button) dialog.findViewById(R.id.buttonannulerservice);
		buttonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
				String dateNow = formatter.format(currentDate.getTime());
				
				// Insertion du service
				voitRepository.Open();
				 voiture =(((EditText) dialog.findViewById(R.id.editTextservicevoiture)).getText().toString());
				int s = voitRepository.cleetrangere(voiture);
				voitRepository.Close();
				 type = ((EditText) dialog.findViewById(R.id.edittypeservice)).getText().toString();
			     prix =  Integer.parseInt(((EditText) dialog.findViewById(R.id.editprixservice)).getText().toString());
				 note =(((EditText) dialog.findViewById(R.id.editnoteservice)).getText().toString());

				if ((voiture.trim().length() <= 0)&(type.trim().length() <= 0) ){

					Toast zz=Toast.makeText(getApplicationContext(),"Entrer la voiture et le type de service !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (voiture.trim().length() <= 0) {
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (type.trim().length() <= 0) {
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le type de service !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (s == 0) {
					Toast zz=Toast.makeText(getApplicationContext(),"Cette voiture n'existe pas !", Toast.LENGTH_LONG);
					zz.show();
				}
				else {
					serviceRepository.Open();
					serviceRepository.Save(new Service(type,dateNow,prix,note,s));
					UpdateAdapter();
					dialog.dismiss();
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
	 * Modifier service
	 */
	private void Modifierservice(final int idm) {
 		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialogaddservice);
		dialog.setTitle("Modifier  Service");
		
		buttonAdd = (Button) dialog.findViewById(R.id.buttonenregistrerservice);
		buttonCancel = (Button) dialog.findViewById(R.id.buttonannulerservice);
		buttonAdd.setText("Modifier");
		buttonAdd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
				String dateNow = formatter.format(currentDate.getTime());
				// Modification du service
				voitRepository.Open();
				 voiture =(((EditText) dialog.findViewById(R.id.editTextservicevoiture)).getText().toString());
				int s = voitRepository.cleetrangere(voiture);
				voitRepository.Close();
				 type = ((EditText) dialog.findViewById(R.id.edittypeservice)).getText().toString();
				int prix =  Integer.parseInt(((EditText) dialog.findViewById(R.id.editprixservice)).getText().toString());
				 note =(((EditText) dialog.findViewById(R.id.editnoteservice)).getText().toString());

				if ((voiture.trim().length() <= 0)&(type.trim().length() <= 0) ){

					Toast zz=Toast.makeText(getApplicationContext(),"Entrer la voiture et le type de service !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (voiture.trim().length() <= 0) {
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (type.trim().length() <= 0) {
					Toast zz=Toast.makeText(getApplicationContext(),"Entrer le type de service !", Toast.LENGTH_LONG);
					zz.show();
				}
				else if (s == 0) {
					Toast zz=Toast.makeText(getApplicationContext(),"Cette voiture n'existe pas !", Toast.LENGTH_LONG);
					zz.show();
				}
				else {
					serviceRepository.Open();
					serviceRepository.Update(idm,new Service(type,dateNow,prix,note,s));
					UpdateAdapter();
					dialog.dismiss();
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

	private void UpdateAdapter() {
		serviceRepository.Open();
		adapter.setCourses(serviceRepository.GetAll());
		serviceRepository.Close();
		adapter.notifyDataSetChanged();
	}

	public void DeleteItem(int id) {
		serviceRepository.Open();
		serviceRepository.Delete(id);
		serviceRepository.Close();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		SubMenu m1 = menu.addSubMenu(0,2,0, "Modifier");

	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

		switch (item.getItemId()) {
		case 2:
			Modifierservice((int) info.id);
			break;
		}
		return super.onContextItemSelected(item);		
	}

	@Override
	protected void onDestroy() {
		serviceRepository.Close();
		super.onDestroy();
	}
}