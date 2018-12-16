package SGBD.Gestion;

import java.util.ArrayList;
import jus.util.assertion.*;

import SGBD.BDD.Donnee;
import SGBD.BDD.Index;
import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import SGBD.BDD.Tuple;

public class SQL_Fonction {

	/**
	 * Retourne la somme d'une colonne
	 * @param t la table ou realise la somme
	 * @param a la colonne ou realise la somme
	 * @return la valeur de la somme sous forme de string
	 * @require t.schema()!=null && t.listeIndexTuple()!=null && t!=null && a!=null && a in t.schema().attributs() && colonne a int
	 * @ensure sum==la somme de la colonne a && sum!=null
	 */
	public static String sum(Table t, String a) {
		ArrayList<Donnee> tuple;
		int sum=0;
		int i = t.schema().indiceAttributs(a);
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		if (i==-1 || a==null || !t.schema().attributs().get(i).type().equals("TypeInt")) throw new Require("Erreur Mauvais attribut");
		tuple = new Tuple(t.listeIndexTuple().get(0),t.schema()).recupererTuple("src/"+t.nom()+".txt");
		for (Index index : t.listeIndexTuple()) {
			tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			sum+=Integer.parseInt(tuple.get(i).toString());
		}
		return sum+"";
	}

	/**
	 * Retourne la somme d'une colonne
	 * @param t la table ou realise la somme
	 * @param a la colonne ou realise la somme
	 * @return la valeur de la somme sous forme de string
	 * @require t.schema()!=null && t.listeDonee()!=null && t!=null && a!=null && a in t.schema().attributs() && colonne a int
	 * @ensure sum==la somme de la colonne a && sum!=null
	 */
	public static String sum(Table_tmp t, String a) {
		ArrayList<Donnee> tuple;
		int sum=0;
		int i = t.schema().indiceAttributs(a);	
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		if (i==-1 || a==null || !t.schema().attributs().get(i).type().equals("TypeInt")) throw new Require("Erreur Mauvais attribut");
		for (ArrayList<Donnee> tuple1 : t.listeDonnee()) {
			sum+=Integer.parseInt(tuple1.get(i).toString());
		}
		return sum+"";
	}

	/**
	 * Retourne le max d'une colonne
	 * @param t la table ou realise le max
	 * @param a la colonne ou realise le max
	 * @return la valeur du max sous forme de string
	 * @require t.schema()!=null && t.listeIndexTuple()!=null && t!=null && a!=null && a in t.schema().attributs() && colonne a int
	 * @ensure max==le max de la colonne a && max!=null
	 */
	public static String max(Table t, String a) {
		ArrayList<Donnee> tuple;
		int v;
		int i = t.schema().indiceAttributs(a);
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		if (i==-1 || a==null || !t.schema().attributs().get(i).type().equals("TypeInt")) throw new Require("Erreur Mauvais attribut");
		tuple = new Tuple(t.listeIndexTuple().get(0),t.schema()).recupererTuple("src/"+t.nom()+".txt");
		int max = Integer.parseInt(tuple.get(i).toString());
		for (Index index : t.listeIndexTuple()) {
			tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			v=Integer.parseInt(tuple.get(i).toString());
			if (max < v) max=v;
		}
		return max+"";
	}

	/**
	 * Retourne le max d'une colonne
	 * @param t la table ou realise le max
	 * @param a la colonne ou realise le max
	 * @return la valeur du max sous forme de string
	 * @require t.schema()!=null && t.listeIndexTuple()!=null && t!=null && a!=null && a in t.schema().attributs() && colonne a int
	 * @ensure max==le max de la colonne a && max!=null
	 */
	public static String max(Table_tmp t, String a) {
		ArrayList<Donnee> tuple;
		int v;
		int i = t.schema().indiceAttributs(a);	
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		if (i==-1 || a==null || !t.schema().attributs().get(i).type().equals("TypeInt")) throw new Require("Erreur Mauvais attribut");
		int max = Integer.parseInt(t.listeDonnee().get(0).toString());
		for (ArrayList<Donnee> tuple1 : t.listeDonnee()) {
			v=Integer.parseInt(tuple1.get(i).toString());
			if (max > v) max=v;
		}
		return max+"";
	}

	/**
	 * Retourne le min d'une colonne
	 * @param t la table ou realise le min
	 * @param a la colonne ou realise le min
	 * @return la valeur du min sous forme de string
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null && a!=null && a in t.schema().attributs()
	 * @ensure min==le min de la colonne a && min!=null
	 */
	public static String min(Table t, String a) {
		ArrayList<Donnee> tuple;
		int v;
		int i = t.schema().indiceAttributs(a);
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		if (i==-1 || a==null || !t.schema().attributs().get(i).type().equals("TypeInt")) throw new Require("Erreur Mauvais attribut");
		tuple = new Tuple(t.listeIndexTuple().get(0),t.schema()).recupererTuple("src/"+t.nom()+".txt");
		int min = Integer.parseInt(tuple.get(i).toString());
		for (Index index : t.listeIndexTuple()) {
			tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			v=Integer.parseInt(tuple.get(i).toString());
			if (min > v) min=v;
		}
		return min+"";
	}

	/**
	 * Retourne le min d'une colonne
	 * @param t la table ou realise le min
	 * @param a la colonne ou realise le min
	 * @return la valeur du min sous forme de string
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null && a!=null && a in t.schema().attributs() && colonne a int
	 * @ensure min==le min de la colonne a && min!=null
	 */
	public static String min(Table_tmp t, String a) {
		ArrayList<Donnee> tuple;
		int v;
		int i = t.schema().indiceAttributs(a);	
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		if (i==-1 || a==null || !t.schema().attributs().get(i).type().equals("TypeInt")) throw new Require("Erreur Mauvais attribut");
		int min = Integer.parseInt(t.listeDonnee().get(0).toString());
		for (ArrayList<Donnee> tuple1 : t.listeDonnee()) {
			v=Integer.parseInt(tuple1.get(i).toString());
			if (min > v) min=v;
		}
		return min+"";
	}

	/**
	 * @return le nombre de ligne de la table
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null
	 * @ensure count==le nombre de ligne de t && count!=null
	 */
	public static String count(Table t) {
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		return t.listeIndexTuple().size()+"";
	}

	/**
	 * @return le nombre de ligne de la table
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null
	 * @ensure count==le nombre de ligne de t && count!=null
	 */
	public static String count(Table_tmp t) {
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		return t.listeDonnee().size()+"";
	}

	/**
	 * 
	 * @param t la table ou effectuer la moyenne
	 * @param a la colonne ou effectuer la moyenne
	 * @return la moyenne de la colonne a
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null && a!=null && a in t.schema().attributs() && colonne a int
	 * @ensure avg==la moyenne de la colonne a && avg!=null
	 */
	public static String avg (Table_tmp t, String a) {
		if (t.schema()==null || t==null) throw new Ensure("Erreur mauvaise table");
		return Integer.parseInt(sum(t,a))/Integer.parseInt(count(t))+"";
	}
}
