package SGBD.Gestion;

import SGBD.BDD.Table_tmp;
import jus.util.assertion.Require;

import java.util.ArrayList;

import SGBD.BDD.Donnee;

public class Union {

	/**
	 * Realise l'union de deux tables
	 * @param t1 la premiere table
	 * @param t2 la deuxieme table
	 * @return l'union des deux tables
	 * @require t1.schema()!=null && t2.schema()!=null && t1.listeDonne()!=null && t2.listeDonne()!=null && t1!=null && t2!=null
	 * @ensure tmp.schema()==t1.schema() && tmp.schema()==t2.schema() && tmp.listeDonne==(t1.listeDonnee U t2.listeDonnee)
	 */
	public static Table_tmp operation(Table_tmp t1, Table_tmp t2) {
		if(t1.schema()==null || t2.schema()==null || t1.listeDonnee()==null || t2.listeDonnee()==null || t1==null || t2==null) throw new Require("Erreur Mauvais table");
		if (t1.schema().equals(t2.schema())) {
			Table_tmp tmp = new Table_tmp(t1.schema());
			ArrayList<ArrayList<Donnee>> l = new ArrayList<ArrayList<Donnee>>();
			for (ArrayList<Donnee> l1 : t1.listeDonnee()) tmp.ajouterTuple(l1);
			for (ArrayList<Donnee> l2 : t2.listeDonnee()) tmp.ajouterTuple(l2);
			return tmp;
		}
		return null;
	}
}
