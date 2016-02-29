package accueil.nits.com.mybasecarapplication;
public class Consommation {

	private   int id;
	private   int prix;
	private   int quantite;
	private   int kilometrage;
	private   String date;
    private   int voiture;
	
	public Consommation (int prix, int quantite,int kilometrage,String date,int voiture){
		this.setprix(prix);
		this.setquantite(quantite);
		this.setkilometrage(kilometrage);
		this.setdate(date);
		this.setvoiture(voiture);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public void setprix(int prix) {
		this.prix = prix;
	}
	public  int getprix() {
		return prix;
	}
	
	public void setquantite(int quantite) {
		this.quantite = quantite;
	}
	public  int getquantite() {
		return quantite;
	}
	
	public void setkilometrage(int kilometrage) {
		this.kilometrage =kilometrage;
	}
	public  int getkilometrage() {
		return kilometrage;
	}

	public void setdate(String date) {
		this.date =date;
	}
	public  String getdate() {
		return date;
	}
	
	public void setvoiture(int voiture) {
		this.voiture =voiture;
	}
	public  int getvoiture() {
		return voiture;
	}
	
}
