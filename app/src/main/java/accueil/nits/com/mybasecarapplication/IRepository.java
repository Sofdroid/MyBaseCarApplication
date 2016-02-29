package accueil.nits.com.mybasecarapplication;

import android.database.Cursor;

import java.util.List;

public interface IRepository<T> {

	public List<T> GetAll();
	
	public List<String> Getnom();
	List<T> GetAllbyid(int s);
	public void Save(T entite);
	public void Update(int id, T entite);
	public void Delete(int id);
	
	public List<T> ConvertCursorToListObjectv(Cursor c);
	public T ConvertCursorToObjectv(Cursor c);
	public T ConvertCursorToOneObjectv(Cursor c);
}
