package SGBD.BDD;

import jus.util.assertion.Require;

public class Attribut {
	public enum CleOuNon {PK,FK,Other;}
	private String type;
	private String nom;
	private CleOuNon cleOuNon;
	
	public Attribut(String nom, String type, String cleOuNon) {
		this.nom = nom;
		if (type.equals("int")) this.type="TypeInt";
		if (type.equals("string")) this.type="TypeString";
		if (cleOuNon.equals("PK")) this.cleOuNon=CleOuNon.PK;
		else if (cleOuNon.equals("FK")) this.cleOuNon=CleOuNon.FK;
		else this.cleOuNon=CleOuNon.Other;
	}
	
	/**
	 * @return l'attribut sous forme de String
	 */
	public String toString() {
		return nom + " " + type + " " + cleOuNon.toString();
	}
	
	/**
	 * Modifie le nom de l'attribut
	 * @param n le nouveau nom
	 * @require n!=null
	 */
	public void setNom(String n) { if(n==null) throw new Require("Erreur chaine");this.nom=n; }
	
	/**
	 * @return le nom de l'attribut
	 */
	public String nom() { String s=this.nom; return s; }
	
	/**
	 * @return le type de l'attribu
	 */
	public String type() { String s=this.type; return s; }
	
	/**
	 * @return cleOuNon
	 */
	public String cleOuNon() { String s=this.cleOuNon.toString(); return s; }
}
