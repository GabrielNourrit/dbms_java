package SGBD.Gestion;

import java.util.ArrayList;

import SGBD.BDD.Attribut;
import SGBD.BDD.Donnee;
import SGBD.BDD.Index;
import SGBD.BDD.Schema_tmp;
import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import SGBD.BDD.Tuple;
import jus.util.assertion.Require;

public class Projection {
	//private Table table;

	/**
	 * Methode qui effectue la projection sur une Table
	 * @param colonne les colonnes à garder
	 * @param t la Table ou effectuer la projection
	 * @return une table_tmp ou a ete effectue la projection
	 * @require colonne!=null && t.schema()!=null && t.listeIndexTuple()!=null
	 * @ensure tmp!=null && tmp.schema()!=null && tmp la projection
	 */
	public static Table_tmp operationTable(ArrayList<String> colonne, Table t) {
		ArrayList<Integer> aGarder = new ArrayList<Integer>();
		ArrayList<Attribut> attributTmp = new ArrayList<Attribut>();
		int i = 0;

		if (colonne==null) throw new Require("Erreur colonne"); 
		if(t.schema()==null && t.listeIndexTuple()==null) throw new Require("Erreur Table"); 

		if (colonne.size()==1 && colonne.get(0).equals("*")) return t.toTableTmp();
		for (String c : colonne) if (t.schema().indiceAttributs(c)==-1) throw new Require("L'attribut " + c + " n'existe pas ici");
		for(Attribut a : t.schema().attributs()) {
			for (String s : colonne) {	
				if (a.nom().toUpperCase().equals(s.toUpperCase())) {
					aGarder.add(i);
					attributTmp.add(t.schema().attributs().get(i));
				}
			}
			i++;	
		}
		i=0;
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		ArrayList<Donnee> tuple;
		ArrayList<Donnee> nouveauTuple = new ArrayList<Donnee>();	
		for (Index index : t.listeIndexTuple()) {
			tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			for (Donnee d : tuple) {
				if(isIn(aGarder, i)) {
					nouveauTuple.add(d);

				}
				i++;				
			}
			i=0;
			nouvelleListe.add(nouveauTuple);
			nouveauTuple = new ArrayList<Donnee>();
		}
		Schema_tmp schema = new Schema_tmp(attributTmp);
		Table_tmp tmp = new Table_tmp(nouvelleListe, schema);
		return tmp;
	}

	/**
	 * Methode qui effectue la projection sur une Table_tmp
	 * @param colonne les colonnes à garder
	 * @param t la Table_tmp ou effectuer la projection
	 * @return une table_tmp ou a ete effectue la projection
	 * @require colonne!=null && t.schema()!=null && t.listeDonnee()!=null
	 * @ensure tmp!=null && tmp.schema()!=null && tmp la projection
	 */
	public static Table_tmp operationTable(ArrayList<String> colonne, Table_tmp t) {
		ArrayList<Integer> aGarder = new ArrayList<Integer>();
		ArrayList<Attribut> attributTmp = new ArrayList<Attribut>();
		int i = 0;

		if (colonne==null) throw new Require("Erreur colonne"); 
		if(t.schema()==null && t.listeDonnee()==null) throw new Require("Erreur Table"); 
		if (colonne.size()==1 && colonne.get(0).equals("*")) return t;
		for (String c : colonne) if (t.schema().indiceAttributs(c)==-1) throw new Require(c + "N'existe pas ici");
		for(Attribut a : t.schema().attributs()) {
			for (String s : colonne) {				
				if (a.nom().toUpperCase().equals(s.toUpperCase())) {
					aGarder.add(i);
					attributTmp.add(t.schema().attributs().get(i));
				}
			}
			i++;	
		}
		i=0;
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		ArrayList<Donnee> tuple;
		ArrayList<Donnee> nouveauTuple = new ArrayList<Donnee>();
		for (ArrayList<Donnee> l : t.listeDonnee()) {
			for (Donnee d : l) {
				if(isIn(aGarder, i)) {
					nouveauTuple.add(d);
				}
				i++;			
			}
			i=0;
			nouvelleListe.add(nouveauTuple);
			nouveauTuple = new ArrayList<Donnee>();
		}
		Schema_tmp schema = new Schema_tmp(attributTmp);
		Table_tmp tmp = new Table_tmp(nouvelleListe, schema);
		return tmp;
	}

	/**
	 * Verifie si un entier est dans une ArrayList<Integer>
	 * @param l la liste d'entier
	 * @param i l'entier a rechercher
	 * @return true si l'entier a ete trouve dans la liste false sinon
	 */
	private static boolean isIn(ArrayList<Integer> l, int i) {
		for(Integer in : l) {
			if (i==in) return true;
		}
		return false;
	}
}
