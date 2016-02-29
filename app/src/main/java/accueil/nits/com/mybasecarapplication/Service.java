package accueil.nits.com.mybasecarapplication;

public class Service {

	private int id;
	private String type;
	private String date ;
	private int prix;
	private String note;
	private int voiture;

	
	public Service(String type, String date, int prix , String note,int voiture) {
		this.type = type;
		this.date = date;
		this.prix = prix;
		this.note = note;
		this.voiture = voiture;

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type = type;
	}
	
	public String getdate() {
		return date;
	}
	public void setdate(String date) {
		this.date = date;
	}
	
	public int getprix() {
		return prix;
	}
	public void setprix(int prix) {
		this.prix = prix;
	}
	
	
	public String getnote() {
		return note;
	}
	public void setnote(String note) {
		this.note = note;
	}
	
	public int getvoiture() {
		return voiture;
	}
	public void setvoiture(int voiture) {
		this.voiture = voiture;
	}
}
