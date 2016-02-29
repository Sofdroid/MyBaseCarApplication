package accueil.nits.com.mybasecarapplication;

public class Voiture {

	private int id;
	private String num_mat;
	private String nom;
	private String marque;
	private String type;
	private String note;
		
	public Voiture(String num_mat, String nom, String marque ,String type , String note) {
		this.num_mat = num_mat;
		this.nom = nom;
		this.marque = marque;
		this.type = type;
		this.note = note;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getnum_mat() {
		return num_mat;
	}
	public void setnum_mat(String num_mat) {
		this.num_mat = num_mat;
	}
	public String getnom() {
		return nom;
	}
	public void setnom(String nom) {
		this.nom = nom;
	}
	public String getmarque() {
		return marque;
	}
	public void setmarque(String marque) {
		this.marque = marque;
	}
	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.marque = type;
	}
	public String getnote() {
		return note;
	}
	public void setnote(String note) {
		this.note = note;
	}
	public String toString(){
		return "num_mat  : "+num_mat+"\n nom : "+nom+"\nmarque : "+marque+"\ntype : "+type+"\nnote : "
				+note;
	}
	
}
