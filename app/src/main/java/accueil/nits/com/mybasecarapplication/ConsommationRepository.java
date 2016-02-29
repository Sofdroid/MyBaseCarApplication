package accueil.nits.com.mybasecarapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


public class ConsommationRepository extends Repository<Consommation> {
	
	// Version de la base de donn�es
	private static final int DATABASE_VERSION_C = 5;

	// Nom de la base
	private static final String CONSOMMATION_BASE_NAME = "consommation.db";
	
	public ConsommationRepository(Context context) {
		sqLiteOpenHelper_v = new DBapp(context, CONSOMMATION_BASE_NAME, null, DATABASE_VERSION_C);
	}

	/**
	 * Suppression d'une conso
	 * 
	 * @param id
	 */
	public void Deleteconsommation(int id) {
		maBDD_v.delete(DBapp.CONSOMMATION_TABLE_NAME,
				DBapp.COLUMN_ID_c + "=?",
				new String[] { String.valueOf(id) });
	}
	/**
	 *
	 */
	@Override
	public List<Consommation> GetAll() {
		//
		Cursor cursor = maBDD_v.query(DBapp.CONSOMMATION_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID_c,
				DBapp.COLUMN_PRIX_c,
				DBapp.COLUMN_QUANTITE_c,
				DBapp.COLUMN_KILOMETRAGE_c,
				DBapp.COLUMN_DATE_c,
				DBapp.COLUMN_VOITURE_c}, null, null, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	
	@Override
	public List<Consommation> GetAllbyid(int s) {
		//
		Cursor cursor = maBDD_v.query(DBapp.CONSOMMATION_TABLE_NAME,
				new String[] { DBapp.COLUMN_ID_c,
				DBapp.COLUMN_PRIX_c,
				DBapp.COLUMN_QUANTITE_c,
				DBapp.COLUMN_KILOMETRAGE_c,
				DBapp.COLUMN_DATE_c,
				DBapp.COLUMN_VOITURE_c}, DBapp.COLUMN_ID_c + "=?",
				new String[] { String.valueOf(s) }, null,null, null);
		return ConvertCursorToListObjectv(cursor);
	}
	
	/**
	 * Retourne une seul conso
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
	 * Enregistre conso dans la base
	 */
	@Override
	public void Save(Consommation entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_PRIX_c, entite.getprix());
		contentValues.put(DBapp.COLUMN_QUANTITE_c, entite.getquantite());
		contentValues.put(DBapp.COLUMN_KILOMETRAGE_c,entite.getkilometrage());
		contentValues.put(DBapp.COLUMN_DATE_c,entite.getdate());
		contentValues.put(DBapp.COLUMN_VOITURE_c,entite.getvoiture());
    		maBDD_v.insert(DBapp.CONSOMMATION_TABLE_NAME, null, contentValues);
	}
	
	/**
	 *
	 */
	@Override
	public void Update(int id,Consommation entite) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBapp.COLUMN_PRIX_c, entite.getprix());
		contentValues.put(DBapp.COLUMN_QUANTITE_c, entite.getquantite());
		contentValues.put(DBapp.COLUMN_KILOMETRAGE_c,entite.getkilometrage());
		contentValues.put(DBapp.COLUMN_DATE_c,entite.getdate());
		contentValues.put(DBapp.COLUMN_VOITURE_c,entite.getvoiture());
	    maBDD_v.update(DBapp.CONSOMMATION_TABLE_NAME, contentValues,
	    			DBapp.COLUMN_ID_c + "=?",
	    			new String[]{""+id});
	}

	/**
	 * Supprime une conso
	 */
	@Override
	public void Delete(int id) {
		maBDD_v.delete(DBapp.CONSOMMATION_TABLE_NAME,
				DBapp.COLUMN_ID_c + "=?",
				new String[] { String.valueOf(id) });
	}

	/**
	 * Converti un curseur en une liste de conso
	 */
	@Override
	public List<Consommation> ConvertCursorToListObjectv(Cursor c) {
		List<Consommation> liste = new ArrayList<Consommation>();
		// Si la liste est vide
		if (c.getCount() == 0)
			return liste;
		// position sur le premeir item
		c.moveToFirst();
		// Pour chaque item
		do {
			Consommation conso = ConvertCursorToObjectv(c);
			liste.add(conso);
		} while (c.moveToNext());
		// Fermeture du curseur
		c.close();
		return liste;
	}
	/**
	 *   ConvertCursorToObject et ConvertCursorToListObject
	 */
	@Override
	public Consommation ConvertCursorToObjectv(Cursor c) {

		Consommation co = new Consommation
		       (c.getInt(DBapp.NUM_COLUMN_PRIX_c),
		    	c.getInt(DBapp.NUM_COLUMN_QUANTITE_c),
				c.getInt(DBapp.NUM_COLUMN_KILOMETRAGE_c),
				c.getString(DBapp.NUM_COLUMN_DATE_c),
				c.getInt(DBapp.NUM_COLUMN_VOITURE_c));
		co.setId(c.getInt(DBapp.NUM_COLUMN_ID_c));

		return co;
	}

	/**
	 * Converti un curseur en  conso
	 */
	@Override
	public Consommation ConvertCursorToOneObjectv(Cursor c) {
		c.moveToFirst();
		Consommation con = ConvertCursorToObjectv(c);
		c.close();
		return con;
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
