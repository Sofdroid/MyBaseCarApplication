package accueil.nits.com.mybasecarapplication;

public class Rappel {

	private int id;
	private   String date;
	private  String nom;
	private   String note;
	private   int voiture;
	
	public Rappel( String date, String nom , String note,int voiture)
	{
		
		this.date = date;
		this.nom = nom;
		this.note = note;
		this.voiture = voiture;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setdate(String date) {
		this.date = date;
	}
	public  String getdate() {
		return date;
	}
	
	public void setnom(String nom) {
		this.nom = nom;
	}
	public  String getnom() {
		return nom;
	}
	
	public void setnote(String note) {
		this.note = note;
	}
	public  String getnote() {
		return note;
	}
	
	public void setvoiture(int voiture) {
		this.voiture =voiture;
	}
	public  int getvoiture() {
		return voiture;
	}
	
}
