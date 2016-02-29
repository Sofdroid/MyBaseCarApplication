package accueil.nits.com.mybasecarapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ServiceRepository extends Repository<Service> {

	 
	VoitureRepository vr;
 	private static final int DATABASE_VERSION_S = 3;
	// Nom de la base
	private static final String SERVICE_BASE_NAME = "service.db";
	
	public ServiceRepository(Context context) {
		sqLiteOpenHelper_v = new DBapp(context, SERVICE_BASE_NAME, null, DATABASE_VERSION_S);
	}
	/**
 	 */
	@Override
	public List<Service> GetAll() {
 		Cursor cursor = maBDD_v.query(DBapp.SERVICE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID_s,
				DBapp.COLUMN_TYPE_s,
				DBapp.COLUMN_DATE_s,
				DBapp.COLUMN_PRIX_s,
				DBapp.COLUMN_NOTE_s,
				DBapp.COLUMN_VOITURE_s}, null, null, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	
	@Override
	public List<Service> GetAllbyid(int s) {
 		Cursor cursor = maBDD_v.query(DBapp.SERVICE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID_s,
				DBapp.COLUMN_TYPE_s,
				DBapp.COLUMN_DATE_s,
				DBapp.COLUMN_PRIX_s,
				DBapp.COLUMN_NOTE_s,
				DBapp.COLUMN_VOITURE_s}, DBapp.COLUMN_ID_s + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	/**
	 * Retourne un seul produit
	 */
	@Override
	public List<String> Getnom() {
		 List<String> labels = new ArrayList<String>();
		 String selectQuery = "SELECT  "+ DBapp.COLUMN_nom +" FROM " + DBapp.VOITURE_TABLE_NAME;
		 Cursor cursor = maBDD_v.rawQuery(selectQuery, null);
		 if (cursor.moveToFirst()) {
	            do {
	                labels.add(cursor.getString(1));
	            } while (cursor.moveToNext());
	        }
	        cursor.close();
	        return labels;
	}
	/**
	 * Enregistre en produit dans la base
	 */
	@Override
	public void Save(Service entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_TYPE_s, entite.gettype());
		contentValues.put(DBapp.COLUMN_DATE_s, entite.getdate());
		contentValues.put(DBapp.COLUMN_PRIX_s,entite.getprix());
		contentValues.put(DBapp.COLUMN_NOTE_s,entite.getnote());
		contentValues.put(DBapp.COLUMN_VOITURE_s,entite.getvoiture());
		maBDD_v.insert(DBapp.SERVICE_TABLE_NAME, null, contentValues);
	}
	/**
 	 */
	@Override
	public void Update(int id,Service entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_TYPE_s, entite.gettype());
		contentValues.put(DBapp.COLUMN_DATE_s, entite.getdate());
		contentValues.put(DBapp.COLUMN_PRIX_s,entite.getprix());
		contentValues.put(DBapp.COLUMN_NOTE_s,entite.getnote());
		contentValues.put(DBapp.COLUMN_VOITURE_s,entite.getvoiture());
		maBDD_v.update(DBapp.SERVICE_TABLE_NAME, contentValues,
				DBapp.COLUMN_ID_s + "=?",
				new String[]{""+id});
		
	}
	/**
	 * Supprime un produit
	 */
	@Override
	public void Delete(int id) {
		maBDD_v.delete(DBapp.SERVICE_TABLE_NAME,
				DBapp.COLUMN_ID_s + "=?",
				new String[] { String.valueOf(id) });
	}
	/**
	 * Converti un curseur en une liste de produits
	 */
	@Override
	public List<Service> ConvertCursorToListObjectv(Cursor c) {
		List<Service> liste = new ArrayList<Service>();
		// Si la liste est vide
		if (c.getCount() == 0)
			return liste;
		// position sur le premeir item
		c.moveToFirst();
		// Pour chaque item
		do {
			Service s = ConvertCursorToObjectv(c);
			liste.add(s);
		} while (c.moveToNext());
		// Fermeture du curseur
		c.close();
		return liste;
	}
	/**
 	 */
	@Override
	public Service ConvertCursorToObjectv(Cursor c) {

		Service ss = new Service(
				
				c.getString(DBapp.NUM_COLUMN_TYPE_s),
				c.getString(DBapp.NUM_COLUMN_DATE_s),
				c.getInt(DBapp.NUM_COLUMN_PRIX_s),
		        c.getString(DBapp.NUM_COLUMN_NOTE_s),
                c.getInt(DBapp.NUM_COLUMN_VOITURE_s));

		ss.setId(c.getInt(DBapp.NUM_COLUMN_ID_s));

		return ss;
	}
	/**
	 * Converti un curseur en un produit
	 */
	@Override
	public Service ConvertCursorToOneObjectv(Cursor c) {
		c.moveToFirst();

		Service s = ConvertCursorToObjectv(c);

		c.close();
		return s;
	}
	
	public int cleetrangere(String s)
	{
		int code_Affect = 0;
		Cursor cursor = maBDD_v.query(DBapp.VOITURE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID},
				DBapp.COLUMN_nom + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
		code_Affect=cursor.getInt(0);
		cursor.moveToNext();
		}
		return code_Affect;
	}
	
	
		
	
}
