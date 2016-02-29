package accueil.nits.com.mybasecarapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class OneFragment extends Fragment {
    private ListView listeViewc;
    private Button boutonAjouter;
    private ConsommationAdapter adapter;
    private ConsommationRepository cRepository;
    public int prix ;
    public int qtt ;
    public int kilo ;
    public String voiture;
    public Button buttonAdd;
    public Button buttonCancel;
    private VoitureRepository voitRepository;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.listeconsommation, container, false);

        // Bouton
        boutonAjouter = (Button) view.findViewById(R.id.bntajouterc);
        boutonAjouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajouter_cons();
            }
        });

        // Listview

        listeViewc = (ListView) view.findViewById(R.id.listViewconsommation);
        cRepository = new ConsommationRepository(getActivity());
        voitRepository = new VoitureRepository(getActivity());
        cRepository.Open();
        adapter = new ConsommationAdapter(getActivity(),cRepository.GetAll());
        cRepository.Close();

        listeViewc.setAdapter(adapter);

        registerForContextMenu(listeViewc);

        return view;
    }

    /**
     * Ajouter  consommation
     */
    private void Ajouter_cons() {

        // Cr�ation de la boite de dialogue
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialogaddconsommation);
        dialog.setTitle(" Ajouter Consommation");

        buttonAdd = (Button) dialog.findViewById(R.id.buttonenregistrerconsommation);
        buttonCancel = (Button) dialog.findViewById(R.id.buttonannulerconsommation);

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
                String dateNow = formatter.format(currentDate.getTime());

                // Insertion du consommation
                voitRepository.Open();
                String voiture =((EditText) dialog.findViewById(R.id.editTextconsovoiture)).getText().toString();
                int s = voitRepository.cleetrangere(voiture);
                voitRepository.Close();


                int prix = Integer.parseInt(((EditText) dialog.findViewById(R.id.editprixconsommation)).getText().toString());
                int qtt =Integer.parseInt(((EditText) dialog.findViewById(R.id.editqttconsommation)).getText().toString());
                int kilo =Integer.parseInt(((EditText) dialog.findViewById(R.id.editkiloconsommation)).getText().toString());


                if ((voiture.trim().length() <= 0)&(prix <= 0) ){

                    Toast zz=Toast.makeText(getActivity(),"Entrer la voiture et le prix de conso !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else if (voiture.trim().length() <= 0) {
                    Toast zz=Toast.makeText(getActivity(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else if (prix <= 0) {
                    Toast zz=Toast.makeText(getActivity(),"Entrer le prix !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else if (s == 0) {
                    Toast zz=Toast.makeText(getActivity(),"Cette voiture n'existe pas  !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else {
                    cRepository.Open();
                    cRepository.Save(new Consommation(prix, qtt,kilo,dateNow,s));
                    UpdateAdapter();
                    dialog.dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
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
     * Modifer  conso
     */
    private void Modifier_carburant(final int idm) {

        // Cr�ation de la boite de dialogue
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.dialogaddconsommation);
        dialog.setTitle(" Modifier Consommation");

        buttonAdd = (Button) dialog.findViewById(R.id.buttonenregistrerconsommation);
        buttonCancel = (Button) dialog.findViewById(R.id.buttonannulerconsommation);

        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar currentDate = Calendar.getInstance();
                SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
                String dateNow = formatter.format(currentDate.getTime());

                // Insertion du voiture
                voitRepository.Open();
                String voiture =((EditText) dialog.findViewById(R.id.editTextconsovoiture)).getText().toString();
                int s = voitRepository.cleetrangere(voiture);
                voitRepository.Close();

                int prix = Integer.parseInt(((EditText) dialog.findViewById(R.id.editprixconsommation)).getText().toString());
                int qtt =Integer.parseInt(((EditText) dialog.findViewById(R.id.editqttconsommation)).getText().toString());
                int kilo =Integer.parseInt(((EditText) dialog.findViewById(R.id.editkiloconsommation)).getText().toString());

                if ((voiture.trim().length() <= 0)&(prix <= 0) ){

                    Toast zz=Toast.makeText(getActivity(),"Entrer la voiture et le prix de conso !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else if (voiture.trim().length() <= 0) {
                    Toast zz=Toast.makeText(getActivity(),"Entrer le nom de la voiture !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else if (prix <= 0) {
                    Toast zz=Toast.makeText(getActivity(),"Entrer le prix !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else if (s == 0) {
                    Toast zz=Toast.makeText(getActivity(),"Cette voiture n'existe pas  !", Toast.LENGTH_LONG);
                    zz.show();
                }
                else {

                    cRepository.Open();
                    cRepository.Update(idm,new Consommation(prix, qtt,kilo,dateNow,s));
                    UpdateAdapter();
                    dialog.dismiss();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clic sur le bouton annuler
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    // actiliser la listeview
    private void UpdateAdapter() {
        cRepository.Open();
        adapter.setconsommation(cRepository.GetAll());
        adapter.notifyDataSetChanged();
    }


    public void DeleteItem(int id) {
        cRepository.Open();
        cRepository.Delete(id);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        SubMenu m1 = menu.addSubMenu(0,2,0, "Modifier");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 2:
                Modifier_carburant((int) info.id);
                break;
        }
        return super.onContextItemSelected(item);


    }
    @Override
    public void onDestroy() {
        cRepository.Close();
        super.onDestroy();
    }
}
