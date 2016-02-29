package accueil.nits.com.mybasecarapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBapp extends SQLiteOpenHelper{

	public static final String VOITURE_TABLE_NAME = "Voiture";
	public static final String CONSOMMATION_TABLE_NAME = "Carburant";
	public static final String SERVICE_TABLE_NAME = "Service";
	public static final String RAPPEL_TABLE_NAME = "Rappel";

	public static final String COLUMN_ID = "ID";
	public static final int NUM_COLUMN_ID = 0;
	public static final String COLUMN_num_mat = "num_mat";
	public static final int NUM_COLUMN_num_mat = 1;
	public static final String COLUMN_nom = "nom";
	public static final int NUM_COLUMN_nom = 2;
	public static final String COLUMN_marque = "marque";
	public static final int NUM_COLUMN_marque = 3;
	public static final String COLUMN_type = "type";
	public static final int NUM_COLUMN_type = 4;
	public static final String COLUMN_note = "note";
	public static final int NUM_COLUMN_note = 5;

	public static final String COLUMN_ID_c = "ID";
	public static final int NUM_COLUMN_ID_c  = 0;
	public static final String COLUMN_PRIX_c  = "prix";
	public static final int NUM_COLUMN_PRIX_c  = 1;
	public static final String COLUMN_QUANTITE_c  = "quantite";
	public static final int NUM_COLUMN_QUANTITE_c  = 2;
	public static final String COLUMN_KILOMETRAGE_c  = "kilometrage";
	public static final int NUM_COLUMN_KILOMETRAGE_c  = 3;
	public static final String COLUMN_DATE_c  = "date";
	public static final int NUM_COLUMN_DATE_c  = 4;
	public static final String COLUMN_VOITURE_c = "voiture";
	public static final int NUM_COLUMN_VOITURE_c  = 5;


	public static final String COLUMN_ID_s = "ID";
	public static final int NUM_COLUMN_ID_s = 0;
	public static final String COLUMN_TYPE_s = "TYPE";
	public static final int NUM_COLUMN_TYPE_s = 1;
	public static final String COLUMN_DATE_s = "DATE";
	public static final int NUM_COLUMN_DATE_s = 2;
	public static final String COLUMN_PRIX_s = "PRIX";
	public static final int NUM_COLUMN_PRIX_s = 3;
	public static final String COLUMN_NOTE_s = "NOTE";
	public static final int NUM_COLUMN_NOTE_s = 4;
	public static final String COLUMN_VOITURE_s = "voiture";
	public static final int NUM_COLUMN_VOITURE_s = 5;


	public static final String COLUMN_ID_r = "ID";
	public static final int NUM_COLUMN_ID_r = 0;
	public static final String COLUMN_date_r = "date";
	public static final int NUM_COLUMN_DATE_r = 1;
	public static final String COLUMN_nom_r = "nom";
	public static final int NUM_COLUMN_NOM_r = 2;
	public static final String COLUMN_note_r = "note";
	public static final int NUM_COLUMN_NOTE_r = 3;
	public static final String COLUMN_VOITURE_r = "voiture";
	public static final int NUM_COLUMN_VOITURE_r = 4;


 	private static final String REQUETE_CREATION_BDD_V =
		"CREATE TABLE "+ VOITURE_TABLE_NAME + " ("   
		+ COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		+ COLUMN_num_mat+ " TEXT NOT NULL, " 
		+ COLUMN_nom + " TEXT NOT NULL, "
		+ COLUMN_marque + " TEXT NOT NULL, "
		+ COLUMN_type + " TEXT NOT NULL, "
		+ COLUMN_note + " TEXT NOT NULL);";

 	private static final String REQUETE_CREATION_BDD_SERVICE =
		"CREATE TABLE "+ SERVICE_TABLE_NAME 
		+ " (" + COLUMN_ID_s+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ COLUMN_TYPE_s+ " TEXT NOT NULL, " 
		+ COLUMN_DATE_s+ " TEXT NOT NULL, " 
		+ COLUMN_PRIX_s + " INTEGER NOT NULL, "
		+ COLUMN_NOTE_s + " TEXT,"
		+ COLUMN_VOITURE_s + " INTEGER,"
		+"UNIQUE ("+COLUMN_ID_s+"),"
		+"FOREIGN KEY ("+COLUMN_VOITURE_s+") references VOITURE_TABLE_NAME("+COLUMN_ID+"));";

 	private static final String REQUETE_CREATION_BDD_CONSOMMATION =
		"CREATE TABLE "+ CONSOMMATION_TABLE_NAME + " (" 
		+ COLUMN_ID_c+ " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		+ COLUMN_PRIX_c+ " INTEGER NOT NULL, " 
		+ COLUMN_QUANTITE_c + " INTEGER NOT NULL, "
		+ COLUMN_KILOMETRAGE_c + " INTEGER NOT NULL, "
		+ COLUMN_DATE_c + " TEXT,"
		+ COLUMN_VOITURE_c+ " INTEGER,"
		+"FOREIGN KEY ("+COLUMN_VOITURE_c+") references VOITURE_TABLE_NAME("+COLUMN_ID+"));";

 	private static final String CREATE_BDDrappel =
		"CREATE TABLE "+RAPPEL_TABLE_NAME+"("
		+COLUMN_ID_r+" INTEGER PRIMARY KEY AUTOINCREMENT,"
		+COLUMN_date_r+" TEXT ,"
		+COLUMN_nom_r+" TEXT ,"
		+COLUMN_note_r+" TEXT ,"
		+COLUMN_VOITURE_r+" INTEGER ," 
		+"FOREIGN KEY ("+COLUMN_VOITURE_r+") references VOITURE_TABLE_NAME("+COLUMN_ID+"));";

	public DBapp(Context context, String name, CursorFactory factory,int version)
	{
		super(context, name, factory, version);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {	
		db.execSQL(REQUETE_CREATION_BDD_V);
		db.execSQL(REQUETE_CREATION_BDD_CONSOMMATION);
		db.execSQL(REQUETE_CREATION_BDD_SERVICE);
		db.execSQL(CREATE_BDDrappel);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE " + VOITURE_TABLE_NAME + ";");
		db.execSQL("DROP TABLE " + CONSOMMATION_TABLE_NAME + ";");
		db.execSQL("DROP TABLE " + SERVICE_TABLE_NAME + ";");
		db.execSQL("DROP TABLE " + RAPPEL_TABLE_NAME + ";");
		onCreate(db);
	} 
}