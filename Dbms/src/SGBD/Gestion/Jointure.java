package SGBD.Gestion;

import java.util.ArrayList;

import SGBD.BDD.Attribut;
import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import jus.util.assertion.Require;

public class Jointure {

	
	/**
	 * Methode qui realise la jointure JOIN ON
	 * @param t1 La premiere table
	 * @param t2 La deuxieme table
	 * @param a1 La colonne de t1
	 * @param a2 La colonne de t2
	 * @return la nouvelle table ou la jointure a ete effectue
	 * @require t1!=null && t2!=null && t1.schema()!=null && t2.schema()!=null && t1.listeIndexTuple()!=null && t2.listeIndexTuple()!=null && a1!=null && a1 in t1.schema() && a2!=null && a2 in t2.schema()
	 * @ensure tmp!=null && tmp.schema()!=null && tmp la jointure de t1 t2
	 */
	public static Table_tmp join_on(Table t1, Table t2, String a1, String a2) {
		if(t1==null || t2==null || t1.schema()==null || t2.schema()==null || t1.listeIndexTuple()==null || t2.listeIndexTuple()==null) throw new Require("Erreur table");
		if(a1==null || a2==null) throw new Require("Erreur attribut");
		String[] at = a1.split("\\.");
		String[] at2 = a2.split("\\.");
		if (t1.schema().indiceAttributs(at[1])==-1 || t2.schema().indiceAttributs(at2[1])==-1) throw new Require("Erreur attribut");
		
		Table_tmp tmp = Produit_Cartesien.operation(t1, t2);
		tmp = Selection.operationTmpCol(tmp,a1,"=",a2);
		ArrayList<String> colonne = new ArrayList<String>();
		for (Attribut a : tmp.schema().attributs()) {
			//String[] at;
			if (!a.nom().toUpperCase().equals(a2.toUpperCase())) {
				at = a.nom().split("\\.");
				colonne.add(at[1]);
				a.setNom(at[1]);
			}
		}
		tmp = Projection.operationTable(colonne, tmp);
		return tmp;
	}
	
	/**
	 * Methode qui realise la jointure NATURAL JOIN
	 * @param t1 La premiere table
	 * @param t2 La deuxieme table
	 * @return la nouvelle table ou la jointure a ete effectuee
	 * @require t1!=null && t2!=null && t1.schema()!=null && t2.schema()!=null && t1.listeIndexTuple()!=null && t2.listeIndexTuple()!=null
	 * @ensure tmp!=null && tmp.schema()!=null && tmp la jointure de t1 t2
	 */
	public static Table_tmp natural_join(Table t1, Table t2) {
		boolean trouve = false;
		int i = 0, j;
		String a1="",a2="";
	
		if(t1==null || t2==null || t1.schema()==null || t2.schema()==null || t1.listeIndexTuple()==null || t2.listeIndexTuple()==null) throw new Require("Erreur table");
		
		//Tant qu on a pas trouve le meme attribut et/ou que la taille n est pas depasse
		while(!trouve && i<t1.schema().attributs().size()) {
			a1 = t1.schema().attributs().get(i).nom().toUpperCase();
			j=0;
			while(!trouve && j<t2.schema().attributs().size()) {
				a2 = t2.schema().attributs().get(j).nom().toUpperCase();
				if (a1.equals(a2)) trouve=true;
				j++;
			}
			i++;
		}
		//Si on a trouve on utilise Join_On sinon on renvoit le produit cartesien
		if (trouve) return join_on(t1,t2,t1.nom()+"."+a1,t2.nom()+"."+a2);
		else return Produit_Cartesien.operation(t1, t2);
	}
}
