package SGBD.BDD;

import java.util.ArrayList;

import jus.util.assertion.Require;

public class Table_tmp {
	private ArrayList<ArrayList<Donnee>> listeDonnee;
	private Schema_tmp schema;

	public Table_tmp(ArrayList<ArrayList<Donnee>> l, Schema_tmp schema) {
		this.listeDonnee=l;
		this.schema = schema;
	}
	public Table_tmp() {
		this.listeDonnee = new ArrayList<ArrayList<Donnee>>();
	}
	public Table_tmp(Schema_tmp s) {
		this.listeDonnee = new ArrayList<ArrayList<Donnee>>();
		this.schema = s;
	}
	public Table_tmp(Table_tmp tmp) {
		this.listeDonnee= tmp.listeDonnee();
		this.schema = tmp.schema();
	}
	
	/**
	 * @return une ArrayList<ArrayList<Donnee>>
	 */
	public ArrayList<ArrayList<Donnee>> listeDonnee() { ArrayList<ArrayList<Donnee>> l = this.listeDonnee;return l;  }

	/**
	 * ajoute le tuple l a la liste de donnee
	 * @param l une ArrayList<Donnee>
	 * @require l!=null
	 */
	public void ajouterTuple(ArrayList<Donnee> l) { if(l==null) throw new Require("Erreur liste");this.listeDonnee.add(l); }

	/**
	 * @return le schema_tmp de la table_tmp
	 */
	public Schema_tmp schema() { return this.schema; }

	/**
	 * Methode qui affiche le contenu de la table
	 */
	public void affichage() { 
		if (listeDonnee != null) System.out.println(listeDonnee);
		else System.out.println("");
	}

	/**
	 * Methode qui convertit la table en un string
	 */
	public String toString() {
		String chaine="";
		chaine = "[";
		for (int i = 0; i < schema.attributs().size();i++) {
			if (i == schema.attributs().size()-1) chaine+=schema.attributs().get(i).nom()+"]\n";
			else chaine+=schema.attributs().get(i).nom()+",";
		}
		if (listeDonnee != null) {
			//chaine = "[";
			for (int i = 0; i<listeDonnee.size(); i++) {
				chaine+="[";
				for (int j = 0; j<listeDonnee.get(i).size(); j++) {
					if (j==listeDonnee.get(i).size()-1) chaine+=listeDonnee.get(i).get(j).toString()+"]\n";
					else chaine+=listeDonnee.get(i).get(j).toString()+",";
				}
			}
		}	
		else chaine = "";
		return chaine;
	}

	/**
	 * Methode qui tri une table en fonction d'une colonne
	 * @param a la colonne que l'on doit trier
	 * @return une table triee en fonction de la colonne
	 * @require a!=null && a in this.schema
	 * @ensure tmp!=null && tmp.schema()!=null
	 */
	public Table_tmp sort(String a) {
		if (a == null) throw new Require("Erreur attribut");
		int indice = this.schema.indiceAttributs(a);
		if (indice==-1) throw new Require("Erreur attribut");
		boolean trouve;
		int j =0;
		for (int i = 0; i < this.listeDonnee().size(); i++) {
			trouve = false;
			j=i+1;
			while(j<this.listeDonnee().size() && !trouve) {
				if(compareTuple(this.listeDonnee().get(i),this.listeDonnee().get(j),indice)<=0) {
					this.listeDonnee().add(j, this.listeDonnee().get(i));
					this.listeDonnee().remove(i);
					trouve=true;
				}
				j++;
			}
			if (!trouve) {
				this.listeDonnee().add(j, this.listeDonnee().get(i));
				this.listeDonnee().remove(i);
			}
		}
		return this;
	}

	/**
	 * Compare deux tuples en fonction d'une colonne
	 * @param t1 le premier tuple a comparer
	 * @param t2 le deuxieme tuple a comparer
	 * @param indice le numero de la colonne a comparer
	 * @return une entier<0 si t1<t2, 0 si t1=t2, entier>0 si t1>t2
	 */
	private int compareTuple(ArrayList<Donnee> t1, ArrayList<Donnee> t2, int indice) {
		String type = this.schema.attributs().get(indice).type();
		if (type.equals("TypeInt") || type.equals("TypeDouble")) {
			return Integer.parseInt(t1.get(indice).toString())-Integer.parseInt(t2.get(indice).toString());
		}
		else if (type.equals("TypeString")) {
			return t1.get(indice).toString().compareTo(t2.get(indice).toString()); 
		}				
		return -1;
	}

	/**
	 * Methode qui verifie si un tuple est dans la table
	 * @param tuple
	 * @return true si le tuple est dans la table false sinon
	 * @require tuple!=null
	 */
	public boolean isin(ArrayList<Donnee> tuple){
		if(tuple==null) throw new Require("Erreur tuple");
		for(ArrayList<Donnee> tupleTab : this.listeDonnee){
			if(tupleTab.toString().equals(tuple.toString())){ //peut être un peu gourmand en ressources voir performance
				return true;
			}
		}
		return false;
	}

}
