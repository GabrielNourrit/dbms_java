package SGBD.Gestion;

import java.util.ArrayList;

import SGBD.BDD.Attribut;
import SGBD.BDD.Donnee;
import SGBD.BDD.Index;
import SGBD.BDD.Schema_tmp;
import SGBD.BDD.Table;
import SGBD.BDD.Table_tmp;
import SGBD.BDD.Tuple;
import SGBD.BDD.type.TypeInt;
import jus.util.assertion.Require;

public class Selection {

	/**
	 * Realise une selection sur une Table sur une valeur exemple colonne1=2 ou colonne1='Salut' 
	 * @param t la table ou effectuer la selection
	 * @param colonne nom de la colonne 
	 * @param operateur "=", "<", ">", "<=", ">=" "!=", si les chaines que "=" et "!="
	 * @param valeur la valeur a comparer (ex 2 ou 'Salut')
	 * @return la nouvelle table avec la selection effectuee
	 * @require t.schema()!=null && t.listeIndexTuple()!=null && t!=null && colonne!=null && colonne in t.schema() && operateur!=null && valeur!=null && (valeur=="x" || valeur="int")
	 * @ensure tmp.schema!=null && tmp.listeDonnee!=null && tmp la selection
	 */
	public static Table_tmp operationval(Table t, String colonne,String operateur, String valeur) {
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		ArrayList<Donnee> tuple;
		int i = 0, numColonne = 0, iValeur=0, iDonnee;
		boolean trouve = false; //Si on a trouve la colonne dans le schema
		boolean isInt = true; //Si notre valeur est un int
		//On regarde si notre colonne est dans le schema
		numColonne = t.schema().indiceAttributs(colonne);

		if (t.schema()==null || t.listeIndexTuple()==null || t==null) throw new Require("Erreur Table");
		if (colonne==null || numColonne==-1) throw new Require("Erreur Colonne");
		if(operateur==null) throw new Require("Erreur Operateur");
		if(valeur==null) throw new Require("Erreur Valeur");

		//On verifie si on veut comparer un int ou non et si c'est en accord avec le schema
		if (operateur.equals("<") || operateur.equals(">") || operateur.equals("<=") || operateur.equals(">=")) {
			if(!t.schema().attributs().get(numColonne).type().equals("TypeInt")) isInt = false;
			try {
				iValeur = Integer.parseInt(valeur);
			} catch (NumberFormatException e) {
				isInt = false;
			}
		}
		if (isInt==false) throw new Require("Erreur Valeur doit être un entier");

		for (Index index : t.listeIndexTuple()) {
			tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			if (operateur.equals("=")) {
				if (tuple.get(numColonne).toString().equals(valeur)) nouvelleListe.add(tuple);
			}
			else if (operateur.equals("<")) {
				iDonnee = Integer.parseInt(tuple.get(numColonne).toString());
				if (iDonnee<iValeur) nouvelleListe.add(tuple);
			}
			else if (operateur.equals(">")) {
				iDonnee = Integer.parseInt(tuple.get(numColonne).toString());
				if (iDonnee>iValeur) nouvelleListe.add(tuple);
			}
			else if (operateur.equals("<=")) {
				iDonnee = Integer.parseInt(tuple.get(numColonne).toString());
				if (iDonnee<=iValeur) nouvelleListe.add(tuple);
			}
			else if (operateur.equals(">=")) {
				iDonnee = Integer.parseInt(tuple.get(numColonne).toString());
				if (iDonnee>=iValeur) nouvelleListe.add(tuple);
			}
			else if (operateur.equals("!=")) {
				if (!tuple.get(numColonne).toString().equals(valeur)) nouvelleListe.add(tuple);
			}
			else throw new Require("Erreur opérateur");
		}
		//On cree notre table tmp
		ArrayList<Attribut> a = t.schema().attributs();
		Schema_tmp schema = new Schema_tmp(a);
		Table_tmp tmp = new Table_tmp(nouvelleListe, schema);
		return tmp;		
	}

	/**
	 * Methode qui effectue une Selection sur une Table sur deux colonnes ex:colonne1=colonne2
	 * @param t la table ou effectue la selection
	 * @param colonne1 la premiere colonne a regarder
	 * @param operateur "=", "<", ">", "<=", ">=" "!="
	 * @param colonne2 la deuxieme colonne a regarder
	 * @return la nouvelle table avec la selection effectuee
	 * @require t.schema()!=null && t.listeIndexTuple()!=null && t!=null && colonne1!=null && colonne1 in t.schema() && colonne2!=null && colonne2 in t.schema() && operateur!=null  && t.schema().colonne1.type()=="TypeInt" && t.schema().colonne2.type()=="TypeInt"
	 * @ensure tmp.schema!=null && tmp.listeDonnee!=null && tmp la selection
	 */
	public static Table_tmp operationCol(Table t, String colonne1,String operateur, String colonne2) {
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		ArrayList<Donnee> tuple;
		int i = 0, numColonne1, numColonne2, iDonnee1, iDonnee2;
		boolean trouve = false;
		boolean isInt = true;

		numColonne1 = t.schema().indiceAttributs(colonne1);
		numColonne2 = t.schema().indiceAttributs(colonne2);

		if (t.schema()==null || t.listeIndexTuple()==null || t==null) throw new Require("Erreur Table");
		if (colonne1==null || numColonne1==-1 || colonne2==null || numColonne2==-1) throw new Require("Erreur Colonne");
		if(operateur==null) throw new Require("Erreur Operateur");

		if (operateur.equals("<") || operateur.equals(">") || operateur.equals("<=") || operateur.equals(">=")) {
			if(!t.schema().attributs().get(numColonne1).type().equals("TypeInt") ||
					!t.schema().attributs().get(numColonne2).type().equals("TypeInt")) isInt = false;
		}

		if (!isInt) throw new Require("Erreur pour cet opérateur les colonnes doivent etre de type int");

		for (Index index : t.listeIndexTuple()) {
			tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			if (operateur.equals("=")) {
				if (tuple.get(numColonne1).toString().equals(tuple.get(numColonne2).toString())) nouvelleListe.add(tuple);
			}
			else if (operateur.equals("<")) {
				iDonnee1 = Integer.parseInt(tuple.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple.get(numColonne2).toString());
				if (iDonnee1<iDonnee2) nouvelleListe.add(tuple);
			}
			else if (operateur.equals(">")) {
				iDonnee1 = Integer.parseInt(tuple.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple.get(numColonne2).toString());
				if (iDonnee1>iDonnee2) nouvelleListe.add(tuple);
			}
			else if (operateur.equals("<=")) {
				iDonnee1 = Integer.parseInt(tuple.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple.get(numColonne2).toString());
				if (iDonnee1<=iDonnee2) nouvelleListe.add(tuple);
			}
			else if (operateur.equals(">=")) {
				iDonnee1 = Integer.parseInt(tuple.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple.get(numColonne2).toString());
				if (iDonnee1>=iDonnee2) nouvelleListe.add(tuple);
			}
			else if (operateur.equals("!=")) {
				if (!tuple.get(numColonne1).toString().equals(tuple.get(numColonne2).toString())) nouvelleListe.add(tuple);
			}
			else throw new Require("Erreur operateur");
		}
		ArrayList<Attribut> a = t.schema().attributs();
		Schema_tmp schema = new Schema_tmp(a);
		Table_tmp tmp = new Table_tmp(nouvelleListe, schema);
		return tmp;

	}

	/**
	 * Realise une selection sur une Table_tmp sur une valeur exemple colonne1=2 ou colonne1='Salut' 
	 * @param t la table ou effectuer la selection
	 * @param colonne nom de la colonne 
	 * @param operateur "=", "<", ">", "<=", ">=" "!=", si les chaines que "=" et "!="
	 * @param valeur la valeur a comparer (ex 2 ou 'Salut')
	 * @return la nouvelle table avec la selection effectuee
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null && colonne!=null && colonne in t.schema() && operateur!=null && valeur!=null && (valeur=="x" || valeur="int")
	 * @ensure tmp.schema!=null && tmp.listeDonnee!=null && tmp la selection
	 */
	public static Table_tmp operationTmpVal(Table_tmp t, String colonne,String operateur, String valeur) {
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		int numColonne = 0, iValeur = 0, iDonnee;
		boolean isInt = true;
		numColonne = t.schema().indiceAttributs(colonne);

		if (t.schema()==null || t.listeDonnee()==null || t==null) throw new Require("Erreur Table");
		if (colonne==null || numColonne==-1) throw new Require("Erreur Colonne");
		if(operateur==null) throw new Require("Erreur Operateur");
		if(valeur==null) throw new Require("Erreur Valeur");


		if (operateur.equals("<") || operateur.equals(">") || operateur.equals("<=") || operateur.equals(">=")) {
			if(!t.schema().attributs().get(numColonne).type().equals("TypeInt")) isInt = false;
			try {
				iValeur = Integer.parseInt(valeur);
			} catch (NumberFormatException e) {
				isInt = false;
			}
		}

		if (!isInt) throw new Require("Erreur valeur");

		for (ArrayList<Donnee> tuple1 : t.listeDonnee()) {
			//tuple = new Tuple(index,t.schema()).recupererTuple("src/"+t.nom()+".txt");
			if (operateur.equals("=")) {
				if (tuple1.get(numColonne).toString().equals(valeur)) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals("<")) {
				iDonnee = Integer.parseInt(tuple1.get(numColonne).toString());
				if (iDonnee<iValeur) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals(">")) {
				iDonnee = Integer.parseInt(tuple1.get(numColonne).toString());
				if (iDonnee>iValeur) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals("<=")) {
				iDonnee = Integer.parseInt(tuple1.get(numColonne).toString());
				if (iDonnee<=iValeur) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals(">=")) {
				iDonnee = Integer.parseInt(tuple1.get(numColonne).toString());
				if (iDonnee>=iValeur) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals("!=")) {
				if (!tuple1.get(numColonne).toString().equals(valeur)) nouvelleListe.add(tuple1);
			}
			else throw new Require("Erreur operateur");
		}
		ArrayList<Attribut> a = t.schema().attributs();
		Schema_tmp schema = new Schema_tmp(a);
		Table_tmp tmp = new Table_tmp(nouvelleListe, schema);
		return tmp;

	}

	/**
	 * Methode qui effectue une Selection sur une Table_tmp sur deux colonnes ex:colonne1=colonne2
	 * @param t la table ou effectue la selection
	 * @param colonne1 la premiere colonne a regarder
	 * @param operateur "=", "<", ">", "<=", ">=" "!="
	 * @param colonne2 la deuxieme colonne a regarder
	 * @return la nouvelle table avec la selection effectuee
	 * @require t.schema()!=null && t.listeDonnee()!=null && t!=null && colonne1!=null && colonne1 in t.schema() && colonne2!=null && colonne2 in t.schema() && operateur!=null && t.schema().colonne1.type()=="TypeInt" && t.schema().colonne2.type()=="TypeInt"
	 * @ensure tmp.schema!=null && tmp.listeDonnee!=null && tmp la selection
	 */
	public static Table_tmp operationTmpCol(Table_tmp t, String colonne1,String operateur, String colonne2) {
		ArrayList<ArrayList<Donnee>> nouvelleListe = new ArrayList<ArrayList<Donnee>>();
		int numColonne1, numColonne2, iDonnee1, iDonnee2;
		boolean isInt = true;

		numColonne1 = t.schema().indiceAttributs(colonne1);
		numColonne2 = t.schema().indiceAttributs(colonne2);

		if (t.schema()==null || t.listeDonnee()==null || t==null) throw new Require("Erreur Table");
		if (colonne1==null || numColonne1==-1 || colonne2==null || numColonne2==-1) throw new Require("Erreur Colonne");
		if(operateur==null) throw new Require("Erreur Operateur");

		if (operateur.equals("<") || operateur.equals(">") || operateur.equals("<=") || operateur.equals(">=")) {
			if(!t.schema().attributs().get(numColonne1).type().equals("TypeInt") ||
					!t.schema().attributs().get(numColonne2).type().equals("TypeInt")) isInt = false;
		}

		if(!isInt) throw new Require("Erreur les colonnes doivent être de type int");

		for (ArrayList<Donnee> tuple1 : t.listeDonnee()) {
			if (operateur.equals("=")) {
				if (tuple1.get(numColonne1).toString().equals(tuple1.get(numColonne2).toString())) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals("<")) {
				iDonnee1 = Integer.parseInt(tuple1.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple1.get(numColonne2).toString());
				if (iDonnee1<iDonnee2) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals(">")) {
				iDonnee1 = Integer.parseInt(tuple1.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple1.get(numColonne2).toString());
				if (iDonnee1>iDonnee2) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals("<=")) {
				iDonnee1 = Integer.parseInt(tuple1.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple1.get(numColonne2).toString());
				if (iDonnee1<=iDonnee2) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals(">=")) {
				iDonnee1 = Integer.parseInt(tuple1.get(numColonne1).toString());
				iDonnee2 = Integer.parseInt(tuple1.get(numColonne2).toString());
				if (iDonnee1>=iDonnee2) nouvelleListe.add(tuple1);
			}
			else if (operateur.equals("!=")) {
				if (!tuple1.get(numColonne1).toString().equals(tuple1.get(numColonne2).toString())) nouvelleListe.add(tuple1);
			}
			else throw new Require("Erreur operateur");
		}
		ArrayList<Attribut> a = t.schema().attributs();
		Schema_tmp schema = new Schema_tmp(a);
		Table_tmp tmp = new Table_tmp(nouvelleListe, schema);
		return tmp;		
	}

}
