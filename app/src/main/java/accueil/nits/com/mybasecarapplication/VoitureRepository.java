package accueil.nits.com.mybasecarapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;


public class VoitureRepository extends Repository<Voiture> {
	
	
 	private static final int DATABASE_VERSIONV = 6;
	// Nom de la base
	private static final String VOITURE_BASE_NAME = "voiture.db";
	
	public VoitureRepository(Context context) {
		sqLiteOpenHelper_v = new DBapp(context, VOITURE_BASE_NAME, null,  DATABASE_VERSIONV);	
		}
	/**
	 * Suppression d'une voiture
	 * 
	 * @param id
	 */
	
	public void Deletevoiture(int id) {
		maBDD_v.delete(DBapp.VOITURE_TABLE_NAME,DBapp.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}
	/**
 	 */
	@Override
	public List<Voiture> GetAll() {
		// R�cup�ration de la liste des courses
		Cursor cursor = maBDD_v.query(DBapp.VOITURE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID,
				DBapp.COLUMN_num_mat,
				DBapp.COLUMN_nom,
				DBapp.COLUMN_marque,
				DBapp.COLUMN_type,
				DBapp.COLUMN_note }, null, null, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}

	@Override
	public List<Voiture> GetAllbyid(int s) {
 		Cursor cursor = maBDD_v.query(DBapp.VOITURE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID,
				DBapp.COLUMN_num_mat,
				DBapp.COLUMN_nom,
				DBapp.COLUMN_marque,
				DBapp.COLUMN_type,
				DBapp.COLUMN_note },DBapp.COLUMN_ID + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	@Override
	public List<String> Getnom(){
		 List<String> labels = new ArrayList<String>();	
		 Cursor cursor = maBDD_v.query(DBapp.VOITURE_TABLE_NAME, 
				 new String[] { DBapp.COLUMN_nom},
				 null, null, null,null, null);
		
		 cursor.moveToFirst();
		 while (!cursor.isAfterLast())
			{
			 List<String> lab = new ArrayList<String>();	
			 lab.add(cursor.getString(2));
	             labels = lab;
	            cursor.moveToNext();
			}
	        cursor.close();
	        maBDD_v.close();
	        return labels;
	}
	
	/**
 	 */
	@Override
	public void Update(int id ,Voiture entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_num_mat, entite.getnum_mat());
		contentValues.put(DBapp.COLUMN_nom,entite.getnom());
		contentValues.put(DBapp.COLUMN_marque,entite.getmarque());
		contentValues.put(DBapp.COLUMN_type,entite.gettype());
		contentValues.put(DBapp.COLUMN_note,entite.getnote());
		maBDD_v.update(DBapp.VOITURE_TABLE_NAME, contentValues,DBapp.COLUMN_ID + "=?",
				new String[]{""+id});
	}
	
	/**
	 * Enregistre en voiture dans la base
	 */
	@Override
	public void Save(Voiture entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_num_mat, entite.getnum_mat());
		contentValues.put(DBapp.COLUMN_nom,entite.getnom());
		contentValues.put(DBapp.COLUMN_marque,entite.getmarque());
		contentValues.put(DBapp.COLUMN_type,entite.gettype());
		contentValues.put(DBapp.COLUMN_note,entite.getnote());
		maBDD_v.insert(DBapp.VOITURE_TABLE_NAME, null, contentValues);
	}
	/**
	 * Supprime une voiture
	 */
	@Override
	public void Delete(int id) {
		maBDD_v.delete(DBapp.VOITURE_TABLE_NAME,
				DBapp.COLUMN_ID + "=?",
				new String[] { String.valueOf(id) });
	}
	/**
	 * Converti un curseur en une liste de voitures
	 */
	@Override
	public List<Voiture> ConvertCursorToListObjectv(Cursor c) {
		List<Voiture> liste = new ArrayList<Voiture>();
		// Si la liste est vide
		if (c.getCount() == 0)
			return liste;
		// position sur le premeir item
		c.moveToFirst();
		// Pour chaque item
		do {
			Voiture voiture = ConvertCursorToObjectv(c);
			liste.add(voiture);
		} while (c.moveToNext());
		// Fermeture du curseur
		c.close();
		return liste;
	}
	/**
 	 */
	@Override
	public Voiture ConvertCursorToObjectv(Cursor c) {
		Voiture voiture = new Voiture
		       (c.getString(DBapp.NUM_COLUMN_num_mat),
				c.getString(DBapp.NUM_COLUMN_nom),
				c.getString(DBapp.NUM_COLUMN_marque),  	
				c.getString(DBapp.NUM_COLUMN_type),
				c.getString(DBapp.NUM_COLUMN_note));
		voiture.setId(c.getInt(DBapp.NUM_COLUMN_ID));
		return voiture;
	}
	/**
	 * Converti un curseur en une voiture
	 */
	@Override
	public Voiture ConvertCursorToOneObjectv(Cursor c) {
		c.moveToFirst();
		Voiture course = ConvertCursorToObjectv(c);
		c.close();
		return course;
	}
	
	public int cleetrangere(String s)
	{
		int code = 0;
		Cursor cursor = maBDD_v.query(DBapp.VOITURE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID},
				DBapp.COLUMN_nom + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
		code=cursor.getInt(0);
		cursor.moveToNext();
		}
		return code;
	}
	public int nummat_existe(String s)
	{
		int code = 0;
		Cursor cursor = maBDD_v.query(DBapp.VOITURE_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID},
				DBapp.COLUMN_num_mat + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
		code=cursor.getInt(0);
		cursor.moveToNext();
		}
		return code;
	}
}
