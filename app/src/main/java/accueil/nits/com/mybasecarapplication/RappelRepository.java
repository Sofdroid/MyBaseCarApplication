package accueil.nits.com.mybasecarapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class RappelRepository extends Repository<Rappel>{
	
 	private static final int VERSION_BDDR = 1;
	
	// Nom de la base
	private static final String NOM_BDDR = "rappel.db";
	

	public RappelRepository(Context context) {
		sqLiteOpenHelper_v = new DBapp(context, NOM_BDDR, null,  VERSION_BDDR);	
		}
	
	
	/**
	 * Suppression d'une voiture
	 * 
	 * @param id
	 */
	
	public void Deleterappel(int id) {
		maBDD_v.delete(DBapp.RAPPEL_TABLE_NAME,DBapp.COLUMN_ID_r + "=?",
				new String[] { String.valueOf(id) });
	}
	/**
 	 */
	@Override
	public List<Rappel> GetAll() {
 		Cursor cursor = maBDD_v.query(DBapp.RAPPEL_TABLE_NAME,
				new String[] { 
				DBapp.COLUMN_ID_r,
				DBapp.COLUMN_date_r,
				DBapp.COLUMN_nom_r,
				DBapp.COLUMN_note_r,
				DBapp.COLUMN_VOITURE_r }, null, null, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	
	@Override
	public List<Rappel> GetAllbyid(int s) {
 		Cursor cursor = maBDD_v.query(DBapp.RAPPEL_TABLE_NAME,
				new String[] { 
				DBapp.COLUMN_ID_r,
				DBapp.COLUMN_date_r,
				DBapp.COLUMN_nom_r,
				DBapp.COLUMN_note_r,
				DBapp.COLUMN_VOITURE_r }, DBapp.COLUMN_ID_r + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	/**
	 * Retourne une seul voiture
	 */
	@Override
	public List<String>  Getnom() {
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
	 * Enregistre en voiture dans la base
	 */
	@Override
	public void Save(Rappel entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_date_r,entite.getdate());
		contentValues.put(DBapp.COLUMN_nom_r,entite.getnom());
		contentValues.put(DBapp.COLUMN_note_r,entite.getnote());
		contentValues.put(DBapp.COLUMN_VOITURE_r,entite.getvoiture());
		maBDD_v.insert(DBapp.RAPPEL_TABLE_NAME, null, contentValues);
	}
	/**
 	 */
	@Override
	public void Update(int id,Rappel entite) {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(DBapp.COLUMN_date_r,entite.getdate());
		contentValues.put(DBapp.COLUMN_nom_r,entite.getnom());
		contentValues.put(DBapp.COLUMN_note_r,entite.getnote());
		contentValues.put(DBapp.COLUMN_VOITURE_r,entite.getvoiture());
		maBDD_v.update(DBapp.RAPPEL_TABLE_NAME, contentValues,
				DBapp.COLUMN_ID_r+ "=?",
				new String[]{""+id});
	}
	/**
	 * Supprime une voiture
	 */
	@Override
	public void Delete(int id) {
		maBDD_v.delete(DBapp.RAPPEL_TABLE_NAME,
				DBapp.COLUMN_ID_r + "=?",
				new String[] { String.valueOf(id) });
	}
	/**
	 * Converti un curseur en une liste de voitures
	 */
	@Override
	public List<Rappel> ConvertCursorToListObjectv(Cursor c) {
		List<Rappel> liste = new ArrayList<Rappel>();
		// Si la liste est vide
		if (c.getCount() == 0)
			return liste;
		// position sur le premeir item
		c.moveToFirst();
		// Pour chaque item
		do {
			Rappel r = ConvertCursorToObjectv(c);
			liste.add(r);
		} while (c.moveToNext());
		// Fermeture du curseur
		c.close();
		return liste;
	}
	/**
 	 */
	@Override
	public Rappel ConvertCursorToObjectv(Cursor c) {

		Rappel rr = new Rappel
		       ( c.getString(DBapp.NUM_COLUMN_DATE_r),
				c.getString(DBapp.NUM_COLUMN_NOM_r),
				c.getString(DBapp.NUM_COLUMN_NOTE_r),
				c.getInt(DBapp.NUM_COLUMN_VOITURE_r));
		rr.setId(c.getInt(DBapp.NUM_COLUMN_ID_r));

		return rr;
	}
	/**
	 * Converti un curseur en une voiture
	 */
	@Override
	public Rappel ConvertCursorToOneObjectv(Cursor c) {
		c.moveToFirst();
		Rappel r = ConvertCursorToObjectv(c);
		c.close();
		return r;
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
