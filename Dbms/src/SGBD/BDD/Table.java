package SGBD.BDD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

import jus.util.assertion.Require;


public class Table {
	private String fichierIndex;
	private String fichierTable;
	private String nom;
	private Schema schema;
	private ArrayList<Index> listeIndexTuple;

	public Table(String nom) {
		this.nom = nom.toLowerCase();
		this.fichierTable = "src/"+nom+".txt";
		this.fichierIndex = "src/"+nom+"INDEX.txt";
		listeIndexTuple = new ArrayList<Index>();
		chargerFichier(fichierIndex);
		this.schema = new Schema("src/schema.xml",this.nom);
	}

	/**
	 * Methode qui recherche un tuple par rapport a sa cle dans la table
	 * @param id la cle du tuple a rechercher
	 * @return le tuple trouve
	 * @require id!=null
	 */
	public ArrayList<Donnee> rechercheTuple(String id) {
		if(id==null) throw new Require("Erreur id");
		for (Index i : listeIndexTuple) {
			if (i.cle().equals(id)) {
				return new Tuple(i,schema).recupererTuple("src/"+this.nom+".txt");
			}
		}
		return null;
	}


	/**
	 * charge la liste des index dans listeIndexTuple
	 * @param fichier l'url du fichier a charger
	 * @require fichier!=null
	 */
	public void chargerFichier(String fichier) {
		if (fichier==null) throw new Require("Erreur fichier");
		File f = new File(fichier);
		int i = 0, c = 0;	
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String ligne;
			while((ligne=br.readLine())!=null){
				String str[] =ligne.split(";");
				listeIndexTuple.add(new Index(Integer.parseInt(str[0]),str[1]));
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	/**
	 * sauve la liste des index en format texte
	 * @param fichier l'url du fichier de sauvegarde
	 * @require fichier!=null
	 */
	protected void sauverFichier(String fichier) {
		if (fichier==null) throw new Require("Erreur fichier");
		File f = new File(fichier);
		String chaine = "";
		if (listeIndexTuple != null) {			
			for (Index i : listeIndexTuple) {
				chaine += i.toString() + "\r\n";
			}
		}
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(chaine);	
			fw.close();
			if (chaine.length()!=f.length()) throw new Exception("FileLength");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return convertit la table en un string
	 */
	public String toString() {
		ArrayList<Donnee> tuple;
		String chaine="";
		chaine = "[";
		for (int i = 0; i < schema.attributs().size();i++) {
			if (i == schema.attributs().size()-1) chaine+=schema.attributs().get(i).nom()+"]\n";
			else chaine+=schema.attributs().get(i).nom()+",";
		}
		if (listeIndexTuple != null) {
			//chaine = "[";
			for (int i = 0; i<listeIndexTuple.size(); i++) {
				chaine+="[";
				tuple = listeIndexTuple.get(i).desindexDonnee(fichierTable);
				
				for (int j = 0; j<tuple.size(); j++) {
					//System.out.println(tuple.get(j).toString());
					if (j==tuple.size()-1) chaine+=tuple.get(j).toString()+"]\n";
					else chaine+=tuple.get(j).toString()+",";
				}
			}
		}	
		else chaine = "";
		//System.out.println(chaine);
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
		Table_tmp t = toTableTmp();
		return t.sort(a);
	}

	/**
	 * Compare deux tuples en fonction d'une colonne
	 * @param t1 le premier tuple a comparer
	 * @param t2 le deuxieme tuple a comparer
	 * @param indice le numero de la colonne a comparer
	 * @return une entier<0 si t1<t2, 0 si t1=t2, entier>0 si t1>t2
	 * @require t1!=null && t2!=null
	 */
	private int compareTuple(ArrayList<Donnee> t1, ArrayList<Donnee> t2, int indice) {
		if (t1==null || t2==null) throw new Require("Erreur tuple");
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
	 * Transforme la Table en Table_tmp
	 * @return la Table_tmp transformee
	 */
	public Table_tmp toTableTmp() {
		ArrayList<ArrayList<Donnee>> l = new ArrayList<ArrayList<Donnee>>();
		for (Index i : listeIndexTuple) l.add(i.desindexDonnee(fichierTable));
		return new Table_tmp(l,new Schema_tmp(schema.attributs()));
	}

	/**
	 * @return le schema de la table
	 */
	public Schema schema() { return this.schema; }

	/**
	 * @return le nom de la table
	 */
	public String nom() { return this.nom; }

	/**
	 * @return la liste d'index
	 */
	public ArrayList<Index> listeIndexTuple() { return this.listeIndexTuple; }

	/**
	 * @return le fichier d'index de la table
	 */
	public String fichierIndex() { return this.fichierIndex; }
}
