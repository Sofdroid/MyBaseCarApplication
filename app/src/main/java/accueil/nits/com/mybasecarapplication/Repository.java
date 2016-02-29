package accueil.nits.com.mybasecarapplication;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class Repository<T> implements IRepository<T> {
 	protected SQLiteDatabase maBDD_v;
	protected SQLiteOpenHelper sqLiteOpenHelper_v;
	/**
 	 */
	public Repository() {
		
	}
	/**
	 * Ouverture de la connection
	 */
	public void Open() {
		maBDD_v = sqLiteOpenHelper_v.getWritableDatabase();
		maBDD_v = sqLiteOpenHelper_v.getReadableDatabase();
	}
	/**
	 * Fermeture de la connection
	 */
	public void Close() {
		maBDD_v.close();
	}
}